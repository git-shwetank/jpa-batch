package com.example.demo.generic;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.AbstractStep;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

import static java.lang.String.format;

public abstract class BatchImport {

    //@fixme: should be private/protected
    @Autowired
    public FileArchiver fileArchiver;
    @Autowired
    protected StepBuilderFactory stepBuilderFactory;
    @Autowired
    protected PathConfiguration pathConfiguration;
    @Autowired
    protected JobRepository jobRepository;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * Prepares the job to be run
     *
     * @return the job
     * @throws Exception
     */
    abstract protected Job getJob() throws Exception;

    /***
     * to be override to use
     *
     * @param userName
     * @return
     * @throws Exception
     */
    protected Job getJob(String userName) throws Exception {
        return null;
    }

    /**
     * Pass the jobParameters to job level in order to get the dynamic value
     * @param jobParameters
     * @return
     * @throws Exception
     */
    protected Job getJob(JobParameters jobParameters) throws Exception {
        return null;
    }

    public JobExecution runJob() throws Exception {
        return runJob(new JobParameters());
    }

  /*  public JobExecution runJob(String userName) throws Exception {
        return runJob(new JobParameters(), userName);
    }*/

    public JobExecution runJob(JobParameters jobParameters) throws Exception {
        return getJobLauncher().run(getJob(), jobParameters);
    }

    public JobExecution runJob(JobParameters jobParameters, String userName) throws Exception {
        return getJobLauncher().run(getJob(userName), jobParameters);
    }

    public JobExecution runJob(JobParameters jobParameters, boolean isPassJobParameter) throws Exception {
        if(isPassJobParameter){
            return getJobLauncher().run(getJob(jobParameters), jobParameters);
        } else {
            return getJobLauncher().run(getJob(), jobParameters);
        }
    }

    protected JobBuilder getJobBuilder(String name) {
        return jobBuilderFactory.get(name);
    }

    protected ArchiveFileJobListener configureArchiveFileJobListener(String inputFilePath, String archiveFilePath) {
        return new ArchiveFileJobListener(fileArchiver, inputFilePath, archiveFilePath, false);
    }

    protected Step taskletStep(String stepName, Tasklet tasklet) {
        return stepBuilderFactory.get(stepName + "_step")
                .tasklet(tasklet)
                .allowStartIfComplete(true)
                .build();
    }

    protected Step echo(String stepName) {
        return new AbstractStep() {
            {
                setName(stepName);
                setJobRepository(jobRepository);
                setAllowStartIfComplete(true);
            }

            @Override
            protected void doExecute(StepExecution stepExecution) {
                stepExecution.upgradeStatus(BatchStatus.COMPLETED);
                stepExecution.setExitStatus(ExitStatus.COMPLETED);
                jobRepository.update(stepExecution);
            }
        };
    }

    protected SimpleJobLauncher getJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    /***
     * to get error message from {@link JobExecution}
     *
     * @param jobExecution
     * @return
     */
    public String getErrorMsg(JobExecution jobExecution) {

        String errorMsg = jobExecution.getAllFailureExceptions()
                .stream()
                .map(item -> {
                    String msg = "";
                    if (item instanceof FlatFileParseException) {
                        msg = format("Line number=%s Cause=%s", ((FlatFileParseException) item).getLineNumber(), item.getCause().getMessage());
                    } else if (item instanceof ConstraintViolationException) {
                        msg = format("Constraint Violation=%s",
                                ((ConstraintViolationException) item).getConstraintViolations()
                                        .stream()
                                        .map(constraintViolation -> format("%s -> %s", constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
                                        .collect(Collectors.joining(";")));
                    } else {
                        msg = format("%s Cause(%s)", item.getMessage(), item.getCause() != null ? item.getCause().getMessage() : "null");
                    }
                    return msg;
                }).collect(Collectors.joining(System.lineSeparator()));

        return errorMsg;
    }
}
