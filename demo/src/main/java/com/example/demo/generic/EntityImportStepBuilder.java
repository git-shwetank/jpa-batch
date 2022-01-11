package com.example.demo.generic;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManagerFactory;

@Component
@Scope("prototype")
public class EntityImportStepBuilder<T> {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private StepBuilderFactory stepBuilderFactory;
    private EnhancedFieldSetMapper<T> fieldSetMapper;
    private Class<? extends T> entityType;
    private int maxItemCount = Integer.MAX_VALUE;
    private String inputFilePath;
    private int linesToSkip = 1;
    private int chunkSize = 50;

    public EntityImportStepBuilder<T> stepBuilderFactory(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        return this;
    }

    public EntityImportStepBuilder<T> fieldSetMapper(EnhancedFieldSetMapper<T> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
        return this;
    }

    public EntityImportStepBuilder<T> entityType(Class<? extends T> entityType) {
        this.entityType = entityType;
        return this;
    }

    public EntityImportStepBuilder<T> linesToSkip(int linesToSkip) {
        this.linesToSkip = linesToSkip;
        return this;
    }

    public EntityImportStepBuilder<T> maxItemCount(int maxItemCount) {
        this.maxItemCount = maxItemCount;
        return this;
    }

    public EntityImportStepBuilder<T> inputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
        return this;
    }

    public EntityImportStepBuilder<T> chunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
        return this;
    }

    public SimpleStepBuilder<T, T> defaultStepBuilder() {
        return stepBuilderFactory.get("import_" + ClassUtils.getShortName(entityType) + "_step")
                .allowStartIfComplete(true)
                .<T, T>chunk(this.chunkSize)
                .reader(fileReader())
                .writer(entityWriter());
    }

    public Step buildForBulkInsert(String entityName) {
        ExecutionContextPromotionListener entitiesPromoter = new ExecutionContextPromotionListener();
        entitiesPromoter.setKeys(new String[]{entityName});

        return defaultStepBuilder()
                .writer(contextWriter(entityName))
                .listener(entitiesPromoter)
                .build();
    }

    public LineMapper<T> lineMapper() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setFieldSetFactory(new EnhancedFieldSet.EnhancedFieldSetFactory());
        tokenizer.setNames(fieldSetMapper.getFieldNames());

        DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    private ItemWriter<T> entityWriter() {
        JpaItemWriter<T> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    private ItemReader<T> fileReader() {
        return new FlatFileItemReaderBuilder<T>()
                .resource(resourceLoader.getResource(this.inputFilePath))
                .name(entityType.getName() + "_reader")
                .linesToSkip(linesToSkip)
                .strict(false)
                .maxItemCount(maxItemCount)
                .lineMapper(lineMapper())
                .targetType(entityType)
                .recordSeparatorPolicy(new BlankLineRecordSeparatorPolicy())
                .build();
    }

    //@todo this should be using the helper method where you can provide a class (see SpringBatchUtils)
    private ItemWriter<T> contextWriter(String entityName) {
        return new SpringBatchUtils.StepContextListItemWriter(entityName);
    }
}
