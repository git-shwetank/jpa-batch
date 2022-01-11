package com.example.demo.concrete;

public enum IndexValueType {
    EQUITY("EQUITY"),
    FUTURE("FUTURE"),
    INDEX_OF_INDEX("INDEX_OF_INDEX");

    String indexType;

    IndexValueType(String indexType) {
        this.indexType = indexType;
    }

    public String getName() {
        return indexType;
    }
}
