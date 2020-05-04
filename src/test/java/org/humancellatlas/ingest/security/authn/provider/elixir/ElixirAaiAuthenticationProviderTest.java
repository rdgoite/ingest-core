package org.humancellatlas.ingest.security.authn.provider.elixir;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.spring.security.api.authentication.PreAuthenticatedAuthenticationJsonWebToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.humancellatlas.ingest.security.Account;
import org.humancellatlas.ingest.security.AccountRepository;
import org.humancellatlas.ingest.security.JwtGenerator;
import org.humancellatlas.ingest.security.common.jwk.JwtVerifierResolver;
import org.humancellatlas.ingest.security.exception.InvalidUserEmail;
import org.humancellatlas.ingest.security.exception.JwtVerificationFailed;
import org.junit.jupiter.api.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ElixirAaiAuthenticationProviderTest {

    private static MockWebServer mockBackEnd;

    @BeforeAll
    public static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Nested
    @DisplayName("Authenticate")
    class AuthenticationTests {
        private WebTestClient webTestClient;
        private JWTVerifier jwtVerifier;
        private JwtVerifierResolver jwtVerifierResolver;
        private AccountRepository accountRepository;

        @BeforeEach
        public void setUp() {
            jwtVerifier = mock(JWTVerifier.class);
            jwtVerifierResolver = mock(JwtVerifierResolver.class);
            doReturn(jwtVerifier).when(jwtVerifierResolver).resolve(anyString());
            String baseUrl = String.format("http://localhost:%s",
                    mockBackEnd.getPort());
            doReturn(baseUrl).when(jwtVerifierResolver).getIssuer();
            accountRepository = mock(AccountRepository.class);
        }

        @Test
        @DisplayName("success")
        public void testAuthenticate() throws JsonProcessingException {
            //given
            AuthenticationProvider authenticationProvider = new ElixirAaiAuthenticationProvider(jwtVerifierResolver,
                    accountRepository);

            //given: JWT
            String keyId = "MDc2OTM3ODI4ODY2NUU5REVGRDVEM0MyOEYwQTkzNDZDRDlEQzNBRQ";
            String subject = "johndoe@elixirdomain.tld";

            JwtGenerator jwtGenerator = new JwtGenerator("elixir");
            String jwt = jwtGenerator.generate(keyId, subject, null);

            //and: given a JWT Authentication
            PreAuthenticatedAuthenticationJsonWebToken jwtAuthentication = PreAuthenticatedAuthenticationJsonWebToken.usingToken(jwt);
            assumeThat(jwtAuthentication).isNotNull();

            //and: given JWT Verifier will verify token successfully
            DecodedJWT token = mock(DecodedJWT.class);
            doReturn(jwt).when(token).getToken();
            doReturn(token).when(jwtVerifier).verify(jwtAuthentication.getToken());

            //and: given account with same provider reference will be found
            Account account = mock(Account.class);
            doReturn(subject).when(account).getProviderReference();
            doReturn(account).when(accountRepository).findByProviderReference(subject);

            //and: Elixir user info will be returned
            ElixirUserInfo mockUserInfo = new ElixirUserInfo("sub", "name", "pref", "giv", "fam", "email@ebi.ac.uk");
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(new ObjectMapper().writeValueAsString(mockUserInfo))
                    .addHeader("Content-Type", "application/json"));

            //when:
            Authentication authentication = authenticationProvider.authenticate(jwtAuthentication);

            //then:
            assertThat(authentication).isNotNull();
            assertThat(authentication.isAuthenticated()).isTrue();
            assertThat(authentication.getPrincipal()).isNotNull();
            assertThat(authentication.getPrincipal()).isEqualTo(account);
        }

        @Test
        @DisplayName("no account")
        public void testForNoAccount() throws JsonProcessingException {
            //given
            var authenticationProvider = new ElixirAaiAuthenticationProvider(jwtVerifierResolver, accountRepository);

            //given: JWT
            String keyId = "MDc2OTM3ODI4ODY2NUU5REVGRDVEM0MyOEYwQTkzNDZDRDlEQzNBRQ";
            String subject = "johndoe@elixirdomain.tld";

            JwtGenerator jwtGenerator = new JwtGenerator("elixir");
            String jwt = jwtGenerator.generate(keyId, subject, null);

            //and: given a JWT Authentication
            var jwtAuthentication = PreAuthenticatedAuthenticationJsonWebToken.usingToken(jwt);
            assumeThat(jwtAuthentication).isNotNull();

            //and: given JWT Verifier will verify token successfully
            DecodedJWT token = mock(DecodedJWT.class);
            doReturn(jwt).when(token).getToken();
            doReturn(token).when(jwtVerifier).verify(jwtAuthentication.getToken());

            //and: Elixir user info will be returned
            ElixirUserInfo mockUserInfo = new ElixirUserInfo("sub", "name", "pref", "giv", "fam", "email@ebi.ac.uk");
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(new ObjectMapper().writeValueAsString(mockUserInfo))
                    .addHeader("Content-Type", "application/json"));

            //and: no matching records in the database
            doReturn(null).when(accountRepository).findByProviderReference(anyString());

            //when:
            Authentication authentication = authenticationProvider.authenticate(jwtAuthentication);

            //then:
            assertThat(authentication).isNotNull();
            assertThat(authentication.getPrincipal()).isEqualTo(Account.GUEST);
        }

        @Test
        @DisplayName("invalid user email")
        public void testForInvalidUserEmail() throws JsonProcessingException {
            //given:
            AuthenticationProvider authenticationProvider = new ElixirAaiAuthenticationProvider(jwtVerifierResolver, accountRepository);

            //and:
            ElixirUserInfo mockUserInfo = new ElixirUserInfo("sub", "name", "pref", "giv", "fam", "email@embl.ac.uk");
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(new ObjectMapper().writeValueAsString(mockUserInfo))
                    .addHeader("Content-Type", "application/json"));

            //and:
            JwtGenerator jwtGenerator = new JwtGenerator("issuer@elixir");
            String jwt = jwtGenerator.generate();
            Authentication jwtAuthentication = PreAuthenticatedAuthenticationJsonWebToken.usingToken(jwt);

            //expect:
            assertThatThrownBy(() -> {
                authenticationProvider.authenticate(jwtAuthentication);
            }).isInstanceOf(InvalidUserEmail.class).hasMessageContaining("email@embl.ac.uk");
        }

        @Test
        @DisplayName("valid user email")
        public void testForValidUserEmail() throws JsonProcessingException {
            //given:
            AuthenticationProvider authenticationProvider = new ElixirAaiAuthenticationProvider(jwtVerifierResolver, accountRepository);

            //and:
            ElixirUserInfo mockUserInfo = new ElixirUserInfo("sub", "name", "pref", "giv", "fam", "email@ebi.ac.uk");
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(new ObjectMapper().writeValueAsString(mockUserInfo))
                    .addHeader("Content-Type", "application/json"));

            //and:
            JwtGenerator jwtGenerator = new JwtGenerator("elixir");
            String jwt = jwtGenerator.generate();
            PreAuthenticatedAuthenticationJsonWebToken jwtAuthentication = PreAuthenticatedAuthenticationJsonWebToken.usingToken(jwt);

            DecodedJWT token = mock(DecodedJWT.class);
            doReturn(jwt).when(token).getToken();
            doReturn(token).when(jwtVerifier).verify(jwtAuthentication.getToken());
            Account account = mock(Account.class);
            doReturn(account).when(accountRepository).findByProviderReference("sub");

            //when:
            Authentication auth = authenticationProvider.authenticate(jwtAuthentication);

            //then:
            assertThat(auth).isNotNull();
        }

        @Test
        @DisplayName("verification failed")
        public void testForFailedVerification() throws JsonProcessingException {
            //given:
            AuthenticationProvider authenticationProvider = new ElixirAaiAuthenticationProvider(jwtVerifierResolver, accountRepository);

            //and: Elixir user info will be returned
            ElixirUserInfo mockUserInfo = new ElixirUserInfo("sub", "name", "pref", "giv", "fam", "email@ebi.ac.uk");
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(new ObjectMapper().writeValueAsString(mockUserInfo))
                    .addHeader("Content-Type", "application/json"));

            //and: given a JWT Authentication
            JwtGenerator jwtGenerator = new JwtGenerator("sample@elixir.tld");
            String jwt = jwtGenerator.generateWithSubject("sub");

            PreAuthenticatedAuthenticationJsonWebToken jwtAuthentication = PreAuthenticatedAuthenticationJsonWebToken.usingToken(jwt);

            //and: JWT verifier will fail
            Exception verificationFailed = new JWTVerificationException("verification failed");
            doThrow(verificationFailed).when(jwtVerifier).verify(jwtAuthentication.getToken());


            //expect:
            assertThatThrownBy(() -> {
                authenticationProvider.authenticate(jwtAuthentication);
            }).isInstanceOf(JwtVerificationFailed.class);
        }

    }


}
