package com.example.demo.generic;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemWriter;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import static org.springframework.batch.core.ExitStatus.COMPLETED;

@CommonsLog
public class SpringBatchUtils {

    public static ExecutionContext executionContext(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
    }

    public static <T> JobParametersBuilder jobParameter(String key, T object) {
        return new JobParametersBuilder().addString(key, object.toString());
    }

    public static <T> ItemWriter<T> contextWriter(Class<T> clazz) {
        return new SpringBatchUtils.StepContextListItemWriter(clazz.getName());
    }

    public static boolean isJobExecutionSuccessful(JobExecution jobExecution) {
        return jobExecution.getExitStatus().getExitCode().equals(COMPLETED.getExitCode());
    }

    public static class StepContextListItemWriter extends ListItemWriter {
        private String entityName;

        public StepContextListItemWriter(String entityName) {
            this.entityName = entityName;
        }

        @AfterStep
        public ExitStatus setItemsInStepContext(StepExecution stepExecution) {
            stepExecution.getExecutionContext().put(entityName, this.getWrittenItems());
            return ExitStatus.COMPLETED;
        }
    }

    public static class LambdaItemReader<T> implements ItemReader<T> {

        private final Supplier<List<T>> lambdaInvocation;
        private Iterator<T> itemIterator;
        private Throwable throwable;

        public LambdaItemReader(Supplier<List<T>> lambdaInvocation) {
            this.lambdaInvocation = lambdaInvocation;
            this.throwable = null;
        }

        @BeforeStep
        void beforeStep(StepExecution stepExecution) {
            try {
                itemIterator = lambdaInvocation.get().iterator();
            } catch (Exception e) {
                log.info(e);
                this.throwable = e;
            }
        }

        @Override
        public T read() throws JobInterruptedException {

            if (this.throwable != null) {
                throw new JobInterruptedException(throwable.getMessage());
            }

            return itemIterator.hasNext() ? itemIterator.next() : null;
        }
    }
}
