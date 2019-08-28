package org.humancellatlas.ingest.stagingjobs;

import org.humancellatlas.ingest.stagingjob.StagingJob;
import org.humancellatlas.ingest.stagingjob.StagingJobRepository;
import org.humancellatlas.ingest.stagingjob.StagingJobService;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DuplicateKeyException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StagingJobServiceTest {

    StagingJobRepository stagingJobRepository = mock(StagingJobRepository.class);
    StagingJobService stagingJobService = new StagingJobService(stagingJobRepository);

    @BeforeEach
    public void mockStagingJobRepositorySave() {
        when(stagingJobRepository.save(any(StagingJob.class)))
                .thenAnswer(new Answer<StagingJob>() {
                    private Set<StagingJob> savedJobs = new HashSet<>();
                    @Override
                    public StagingJob answer(InvocationOnMock invocation){
                        StagingJob stagingJob = invocation.getArgument(0);
                        if(savedJobs.contains(stagingJob)) {
                            throw new DuplicateKeyException("");
                        } else {
                            savedJobs.add(stagingJob);
                            return stagingJob;
                        }
                    }
                });
    }

    @Test
    public void testIllegalStateExceptionOnConflictingJobs() {
        UUID testStagingAreaUuid = UUID.randomUUID();
        String testFileName = "test.fastq.gz";


        stagingJobService.registerNewJob(testStagingAreaUuid, testFileName);
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> stagingJobService.registerNewJob(testStagingAreaUuid, testFileName));
    }

    @Test
    public void testRegisteringJobs() {
        UUID testStagingAreaUuid_1 = UUID.randomUUID();
        String testFileName_1 = "test_1.fastq.gz";

        UUID testStagingAreaUuid_2 = UUID.randomUUID();
        String testFileName_2 = "test_2.fastq.gz";

        stagingJobService.registerNewJob(testStagingAreaUuid_1, testFileName_1);
        stagingJobService.registerNewJob(testStagingAreaUuid_1, testFileName_2);

        stagingJobService.registerNewJob(testStagingAreaUuid_2, testFileName_1);
        stagingJobService.registerNewJob(testStagingAreaUuid_2, testFileName_2);
    }
}
