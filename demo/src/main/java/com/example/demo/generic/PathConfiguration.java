package com.example.demo.generic;

public interface PathConfiguration {

    String getProperty(String key);

    default String getImportFileUrlIndexesValues() {
        return blobPathForProperty("import.input.index.values.url");
    }

    default String getArchiveFileUrlIndexesValues() {
        return blobPathForProperty("import.archive.index.values.url");
    }

    default String blobPathForProperty(String key) {
        //TODO do modifications such as a base url for blob file configuration
        return getProperty(key);
    }

}
