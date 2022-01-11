package com.example.demo.concrete;

import com.example.demo.generic.BatchImport;
import com.example.demo.generic.EnhancedFieldSetMapper;
import com.example.demo.generic.EntityImportStepBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import static com.example.demo.generic.DateTimeUtils.ISO_BASIC_DATE_FORMATTER;

@Component
public class SampleValuesBatchImport extends BatchImport {

    private final EntityImportStepBuilder<SampleValueInput> indexValueEntityImportStepBuilder;

    private SampleValuesWriter sampleValuesWriter;

    public SampleValuesBatchImport(EntityImportStepBuilder<SampleValueInput> indexValueEntityImportStepBuilder, SampleValuesWriter sampleValuesWriter) {
        this.indexValueEntityImportStepBuilder = indexValueEntityImportStepBuilder;
        this.sampleValuesWriter = sampleValuesWriter;
    }

    @Override
    protected Job getJob() {
        return getJobBuilder("importCSVFile")
                .start(importIndexValuesStep())
                .listener(configureArchiveFileJobListener(
                        pathConfiguration.getImportFileUrlIndexesValues(),
                        pathConfiguration.getArchiveFileUrlIndexesValues()))
                .build();
    }

    private Step importIndexValuesStep() {
        return indexValueEntityImportStepBuilder
                .inputFilePath(pathConfiguration.getImportFileUrlIndexesValues())
                .entityType(SampleValueInput.class)
                .stepBuilderFactory(stepBuilderFactory)
                .fieldSetMapper(new IndexesValuesFieldsetMapper())
                .defaultStepBuilder()
                .chunk(Integer.valueOf(20000))
                .writer(sampleValuesWriter)
                .build();
    }

    static public class IndexesValuesFieldsetMapper implements EnhancedFieldSetMapper<SampleValueInput> {

        private static final String DATE = "Date";
        private static final String INDEX_CODE = "Index Code";
        private static final String INDEX_VALUE = "Index Value";
        private static final String INDEX_TYPE = "Index Type";

        @Override
        public SampleValueInput mapFieldSet(FieldSet fieldSet) {
            return SampleValueInput.builder()
                    .date(ISO_BASIC_DATE_FORMATTER.parseLocalDate(fieldSet.readString(DATE)))
                    .indexCode(fieldSet.readString(INDEX_CODE))
                    .indexValue(fieldSet.readBigDecimal(INDEX_VALUE))
                    .indexType(IndexValueType.valueOf(fieldSet.readString(INDEX_TYPE)).getName())
                    .build();
        }

        @Override
        public String[] getFieldNames() {
            return new String[]{
                    DATE,
                    INDEX_CODE,
                    INDEX_VALUE,
                    INDEX_TYPE
            };
        }
    }
}
