package com.example.demo;

import com.example.demo.generic.SpringBatchUtils;
import org.assertj.core.api.AbstractAssert;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

public class JobExecutionAssert extends AbstractAssert<JobExecutionAssert, JobExecution> {

    public JobExecutionAssert(JobExecution jobExecution) {
        super(jobExecution, JobExecutionAssert.class);
    }

    public JobExecutionAssert isSuccessful() {
        if (!SpringBatchUtils.isJobExecutionSuccessful(actual))
            failWithMessage("Expected job to be successful but wasn't");
        return this;
    }
    public JobExecutionAssert isNotSuccessful() {
        if (SpringBatchUtils.isJobExecutionSuccessful(actual))
            failWithMessage("Expected job to fail but it was successful");
        return this;
    }
    public JobExecutionAssert hasExitStatusCodeAs(ExitStatus exitStatus) {
        if (!actual.getExitStatus().getExitCode().equals(exitStatus.getExitCode()))
            failWithMessage("ExitStatusCode should be %s but is %s", exitStatus.getExitCode(), actual.getExitStatus().getExitCode());
        return this;
    }
    public JobExecutionAssert hasExitDescriptionContainingIgnoringCase(String contained) {
        if (!actual.getExitStatus().getExitDescription().equalsIgnoreCase(contained))
            failWithMessage("Exit description does not include string %s", contained);
        return this;
    }
}
