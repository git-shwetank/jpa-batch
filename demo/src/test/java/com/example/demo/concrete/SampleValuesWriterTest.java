package com.example.demo.concrete;

import com.example.demo.TestUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SampleValuesWriterTest {

    SampleValueInput sampleValueInput1, sampleValueInput2, sampleValueInput3;

    private LocalDate date;

    List<SampleValueInput> sampleValueInputs = new ArrayList<>();

    BigDecimal indexValue1, indexValue2, indexValue3;

    private DateTime calculationTime;

    @BeforeEach
    void setUp() {
         date = LocalDate.now();
        calculationTime = DateTime.now();

        indexValue1 = new BigDecimal("123.45676");
        indexValue2 = new BigDecimal("321.89766");
        indexValue3 = new BigDecimal("234.89766");


        sampleValueInput1 = new SampleValueInput(date,"IOI",indexValue1,IndexValueType.INDEX_OF_INDEX.getName());
        sampleValueInput2 = new SampleValueInput(date,"EQU",indexValue2,IndexValueType.EQUITY.getName());
        sampleValueInput3 = new SampleValueInput(date,"FUT",indexValue3,IndexValueType.FUTURE.getName());


        sampleValueInputs.add(sampleValueInput1);
        sampleValueInputs.add(sampleValueInput2);
        sampleValueInputs.add(sampleValueInput3);

    }
    @Test
    void write() {
        TestUtils.runAtFixedTime( calculationTime ,()-> {

            EntityManager entityManager = mock(EntityManager.class);
            SampleValuesWriter sampleValuesWriter = new SampleValuesWriter(entityManager);
            sampleValuesWriter.write(sampleValueInputs);

            verify(entityManager).flush();
        });


    }
}