package com.example.demo;

import org.assertj.core.api.Assertions;
import org.springframework.batch.core.JobExecution;

public class BatchJobAssertions extends Assertions {

    public static JobExecutionAssert assertThat(JobExecution actual) {
        return new JobExecutionAssert(actual);
    }
}
