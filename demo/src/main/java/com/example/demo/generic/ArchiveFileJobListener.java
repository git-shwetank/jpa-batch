package com.example.demo.generic;

import lombok.extern.apachecommons.CommonsLog;
import org.joda.time.Instant;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.ExecutionContext;

import static com.example.demo.generic.FileUtils.addSuffixBeforeExtensionToFilename;
import static com.example.demo.generic.SpringBatchUtils.isJobExecutionSuccessful;
import static com.example.demo.generic.StaticProperties.ARCHIVED_FILE_LOCATION;
import static java.lang.String.format;

@CommonsLog
public class ArchiveFileJobListener implements JobExecutionListener {

    static final String FAILURE_FILE_SUFFIX = "-fail";

    private final FileArchiver fileArchiver;
    private final String inputFilePath;
    private final String archiveFilePath;
    private final boolean skipOnFailure;

    public ArchiveFileJobListener(FileArchiver fileArchiver, String inputFilePath, String archiveFilePath, boolean skipOnFailure) {
        this.fileArchiver = fileArchiver;
        this.inputFilePath = inputFilePath;
        this.archiveFilePath = archiveFilePath;
        this.skipOnFailure = skipOnFailure;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        String archiveFileWithStatusPath = archiveFilePath;

        if (!isJobExecutionSuccessful(jobExecution)) {
            if (skipOnFailure)
                return;
            archiveFileWithStatusPath = addSuffixBeforeExtensionToFilename(archiveFileWithStatusPath, FAILURE_FILE_SUFFIX);
        }

        try {
            String archivedFilePath = fileArchiver.archiveWithTime(inputFilePath, archiveFileWithStatusPath, Instant.now());
            ExecutionContext executionContext = jobExecution.getExecutionContext();
            executionContext.put(ARCHIVED_FILE_LOCATION, archivedFilePath);
        } catch (Exception e) {
            log.error(format("Could not archive file '%s' to '%s", inputFilePath, archiveFileWithStatusPath), e);
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }
}
