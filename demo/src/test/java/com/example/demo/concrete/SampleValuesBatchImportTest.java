package com.example.demo.concrete;

import com.example.demo.BatchImportAcceptanceTestBase;
import com.example.demo.generic.EntityImportStepBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.demo.BatchJobAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SampleValuesBatchImportTest extends BatchImportAcceptanceTestBase {

    @Autowired
    private SampleValuesBatchImport sampleValuesBatchImport;

    @Test
    void runJob() throws Exception {
        //clean file staging area before testing
        /*s3TestUtils.cleanS3ResourceDirectories(
                pathConfiguration.getImportFileUrlIndexesValues(),
                pathConfiguration.getArchiveFileUrlIndexesValues());
*/


        //TODO upload sample file to input file location
        //s3Utils.uploadInputStreamToS3(JavaUtils.getResourceFileAsStream("static/samples/sample_values.csv"), pathConfiguration.getImportFileUrlIndexesValues());

        JobExecution jobExecution = sampleValuesBatchImport.runJob();

        assertThat(jobExecution).isSuccessful();

        //traverse all file at archive location to verify the batch import archival listener is successfully complete.
        //List<String> fileListMap = s3TestUtils.archivedFilenames(pathConfiguration.getArchiveFileUrlIndexesValues());

        // only verify there is *a* file which has some kind of pattern replaced (2 from 2017)
        //assertThatCollectionStartingWith(fileListMap, "index_values_20").hasSize(1);
    }

    @Test
    void invalidData() {
        EntityImportStepBuilder<SampleValueInput> builder = new EntityImportStepBuilder<>();
        builder.fieldSetMapper(new SampleValuesBatchImport.IndexesValuesFieldsetMapper());

        String invalidDateLine = "invalid_data,X2C,342.123456789012,EQUITY\n";
        assertThrows(IllegalArgumentException.class, () -> builder.lineMapper().mapLine(invalidDateLine, 0), "Date should be invalid");

        String invalidDecimalLine = "20171212,X2C,invalid_decimal,EQUITY\n";
        assertThrows(IllegalArgumentException.class, () -> builder.lineMapper().mapLine(invalidDecimalLine, 0), "Value should be invalid");

        String invalidType = "20171212,SGXTEX,1209.123456000000,XXXXXX\n";
        assertThrows(IllegalArgumentException.class, () -> builder.lineMapper().mapLine(invalidType, 0), "Value should be invalid");

        String invalidDateLine1 = "invalid_data,SGXTEX,342.123456789012,FUTURE\n";
        assertThrows(IllegalArgumentException.class, () -> builder.lineMapper().mapLine(invalidDateLine1, 0), "Date should be invalid");

        String invalidDecimalLine1 = "20171212,SGXTEX,invalid_decimal,FUTURE\n";
        assertThrows(IllegalArgumentException.class, () -> builder.lineMapper().mapLine(invalidDecimalLine1, 0), "Value should be invalid");
    }
}
