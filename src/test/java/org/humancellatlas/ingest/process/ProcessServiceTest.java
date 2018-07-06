package org.humancellatlas.ingest.process;

import org.humancellatlas.ingest.biomaterial.BiomaterialRepository;
import org.humancellatlas.ingest.bundle.BundleManifest;
import org.humancellatlas.ingest.bundle.BundleManifestRepository;
import org.humancellatlas.ingest.core.service.ResourceLinker;
import org.humancellatlas.ingest.file.File;
import org.humancellatlas.ingest.file.FileRepository;
import org.humancellatlas.ingest.submission.SubmissionEnvelope;
import org.humancellatlas.ingest.submission.SubmissionEnvelopeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProcessServiceTest {

    @Autowired
    private ProcessService service;

    @MockBean
    private SubmissionEnvelopeRepository submissionEnvelopeRepository;
    @MockBean
    private ProcessRepository processRepository;
    @MockBean
    private FileRepository fileRepository;
    @MockBean
    private BiomaterialRepository biomaterialRepository;
    @MockBean
    private BundleManifestRepository bundleManifestRepository;

    @MockBean
    private ResourceLinker resourceLinker;

    @Test
    public void testAddFileToAnalysisProcess() {
        //given:
        Process analysis = new Process();
        SubmissionEnvelope submissionEnvelope = new SubmissionEnvelope();
        analysis.addToSubmissionEnvelope(submissionEnvelope);

        //and:
        File file = new File();
        file.setFileName("ERR1630013.fastq.gz");
        file = spy(file);

        //and:
        doReturn(Collections.emptyList()).when(fileRepository)
                .findBySubmissionEnvelopesInAndFileName(any(SubmissionEnvelope.class), anyString());

        //when:
        Process result = service.addFileToAnalysisProcess(analysis, file);

        //then:
        assertThat(result).isEqualTo(analysis);
        verify(file).addToAnalysis(analysis);
        verify(fileRepository).save(file);
    }

    @Test
    public void testAddFileToAnalysisProcessWhenFileAlreadyExists() {
        //given:
        Process analysis = new Process();
        SubmissionEnvelope submissionEnvelope = new SubmissionEnvelope();
        analysis.addToSubmissionEnvelope(submissionEnvelope);

        //and:
        File file = new File();
        String fileName = "ERR1630013.fastq.gz";
        file.setFileName(fileName);

        //and:
        File persistentFile = spy(new File());
        List<File> persistentFiles = asList(persistentFile);
        doReturn(persistentFiles).when(fileRepository)
                .findBySubmissionEnvelopesInAndFileName(submissionEnvelope, fileName);

        //when:
        Process result = service.addFileToAnalysisProcess(analysis, file);

        //then:
        assertThat(result).isEqualTo(analysis);

        //and:
        verify(persistentFile).addToAnalysis(analysis);
        verify(fileRepository).save(persistentFile);
    }

    @Test
    public void testResolveBundleReferencesForProcess() {
        //given:
        Process analysis = spy(new Process());
        String bundleUuid = "7df005b";
        BundleReference bundleReference = new BundleReference(asList(bundleUuid));

        //and:
        BundleManifest bundleManifest = new BundleManifest("dee00a1", "cd00aa12", "4600991");
        doReturn(bundleManifest).when(bundleManifestRepository).findByBundleUuid(bundleUuid);

        //and:
        Process persistentProcess = mock(Process.class);
        doReturn(persistentProcess).when(processRepository).save(any(Process.class));

        //when:
        Process result = service.resolveBundleReferencesForProcess(analysis, bundleReference);

        //then:
        assertThat(result).isEqualTo(persistentProcess);

        //and:
        InOrder order = inOrder(analysis, processRepository);
        order.verify(analysis).addInputBundleManifest(bundleManifest);
        order.verify(processRepository).save(analysis);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        ProcessService processService() {
            return new ProcessService();
        }

    }

}
