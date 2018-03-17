package org.humancellatlas.ingest.process;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Proc;
import org.humancellatlas.ingest.biomaterial.Biomaterial;
import org.humancellatlas.ingest.biomaterial.BiomaterialRepository;
import org.humancellatlas.ingest.file.File;
import org.humancellatlas.ingest.file.FileRepository;
import org.humancellatlas.ingest.submission.SubmissionEnvelope;
import org.humancellatlas.ingest.submission.SubmissionEnvelopeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by rolando on 19/02/2018.
 */
@Service
@RequiredArgsConstructor
@Getter
public class ProcessService {
    private final @NonNull
    SubmissionEnvelopeRepository submissionEnvelopeRepository;
    private final @NonNull
    ProcessRepository processRepository;
    private final @NonNull
    FileRepository fileRepository;
    private final @NonNull
    BiomaterialRepository biomaterialRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    protected Logger getLog() {
        return log;
    }

    public Page<Biomaterial> findInputBiomaterialsForProcess(Process process, Pageable pageable) {
        return biomaterialRepository.findByInputToProcessesContaining(process, pageable);
    }

    public Page<File> findInputFilesForProcess(Process process, Pageable pageable) {
        return fileRepository.findByInputToProcessesContaining(process, pageable);
    }

    public Page<Biomaterial> findOutputBiomaterialsForProcess(Process process, Pageable pageable) {
        return biomaterialRepository.findByDerivedByProcessesContaining(process, pageable);
    }

    public Page<File> findOutputFilesForProcess(Process process, Pageable pageable) {
        return fileRepository.findByDerivedByProcessesContaining(process, pageable);
    }

    public Process addProcessToSubmissionEnvelope(SubmissionEnvelope submissionEnvelope,
                                                  Process process) {
        process.addToSubmissionEnvelope(submissionEnvelope);
        return getProcessRepository().save(process);
    }

    public Page<Process> retrieveAssaysFrom(SubmissionEnvelope submissionEnvelope,
                                            Pageable pageable) {
        return findAssays(submissionEnvelope, pageable);
    }

    public Page<Process> retrieveAnalysesFrom(SubmissionEnvelope submissionEnvelope,
                                              Pageable pageable) {
        return findAnalyses(submissionEnvelope, pageable);
    }

    private Page<Process> findAssays(SubmissionEnvelope submissionEnvelope, Pageable pageable) {
        Set<Process> results = new LinkedHashSet<>();
        List<File> derivedFiles =
                fileRepository.findBySubmissionEnvelopesContains(submissionEnvelope);
        for (File derivedFile : derivedFiles) {
            for (Process derivedByProcess : derivedFile.getDerivedByProcesses()) {
                if (!biomaterialRepository.findByInputToProcessesContains(derivedByProcess).isEmpty()) {
                    results.add(derivedByProcess);
                }
            }
        }
        return makePage(results, pageable);
    }

    private Page<Process> findAnalyses(SubmissionEnvelope submissionEnvelope, Pageable pageable) {
        Set<Process> results = new LinkedHashSet<>();
        List<File> derivedFiles =
                fileRepository.findBySubmissionEnvelopesContains(submissionEnvelope);
        for (File derivedFile : derivedFiles) {
            for (Process derivedByProcess : derivedFile.getDerivedByProcesses()) {
                if (!fileRepository.findByInputToProcessesContains(derivedByProcess).isEmpty()) {
                    results.add(derivedByProcess);
                }
            }
        }
        return makePage(results, pageable);
    }

    private Page<Process> makePage(Set<Process> processes, Pageable pageable) {
        List<Process> processesList = new ArrayList<>();
        processesList.addAll(processes);
        int from = pageable.getOffset();
        int to = pageable.getOffset() + pageable.getPageSize();
        if (processesList.size() < to) {
            to = processesList.size();
        }
        Page<Process> page = new PageImpl<>(
                processesList.subList(from, to),
                pageable,
                processesList.size());
        return page;
    }
}
