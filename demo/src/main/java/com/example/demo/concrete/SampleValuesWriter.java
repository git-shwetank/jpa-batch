package com.example.demo.concrete;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@CommonsLog
public class SampleValuesWriter implements ItemWriter<SampleValueInput> {

    private final EntityManager entityManager;

    public SampleValuesWriter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void write(List<? extends SampleValueInput> items) {

        log.info("Parsed Object items.");
        log.info(items);

        //TODO write to db
        entityManager.flush();

    }
}
