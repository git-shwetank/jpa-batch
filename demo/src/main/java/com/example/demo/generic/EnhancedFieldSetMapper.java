package com.example.demo.generic;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * About the casting of FieldSet:
 * This is not ideal but to receive a EnhancedFieldSet, we need to:
 * - declare a public interface with method T mapJodaFieldSet(JodaFieldSet jodaFieldSet) throws BindException;
 * - extend it here
 * - Implement a LineMapper<T> that uses an EnhancedFieldSetMapper
 *
 */

public interface EnhancedFieldSetMapper<T> extends FieldSetMapper<T> {
    String[] getFieldNames();

    default T mapFieldSet(FieldSet fieldSet) throws BindException {
        if (fieldSet instanceof EnhancedFieldSet)
            return mapEnhancedFieldSet((EnhancedFieldSet) fieldSet);
        else throw new RuntimeException("Interface method not implemented: mapFieldSet");
    }

    // the default implementation is here as a stopgap until all implementations have switched to this method
    default T mapEnhancedFieldSet(EnhancedFieldSet enhancedFieldSet) throws BindException {
        return mapFieldSet(enhancedFieldSet);
    }
}
