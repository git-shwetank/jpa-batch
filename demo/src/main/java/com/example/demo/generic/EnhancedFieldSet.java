package com.example.demo.generic;

import org.joda.time.LocalDate;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FieldSetFactory;

public class EnhancedFieldSet extends DefaultFieldSet {

    public EnhancedFieldSet(String[] tokens) {
        super(tokens);
    }

    public EnhancedFieldSet(String[] tokens, String[] names) {
        super(tokens, names);
    }

    public LocalDate readJodaDate(String name) {
        return new LocalDate(readDate(name, DateTimeUtils.BASIC_DATE_PATTERN));
    }

    public static class EnhancedFieldSetFactory implements FieldSetFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public FieldSet create(String[] values, String[] names) {
            return new EnhancedFieldSet(values, names);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FieldSet create(String[] values) {
            return new EnhancedFieldSet(values);
        }
    }

}
