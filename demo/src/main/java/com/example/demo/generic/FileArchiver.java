package com.example.demo.generic;

import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;

public interface FileArchiver {

    String archiveWithTime(String inputPath, String archivePath, Instant instant) throws Exception;

    String archiveWithDate(String inputPath, String archivePath, LocalDate archiveDateTime) throws Exception;

    void archive(String inputPath, String archivePath) throws Exception;

    boolean fileExistsForDate(String fileUrl, LocalDate date);

    default void delete(String inputPath) {
    }

    default void archiveWithFilePath(String reportFile, String archivePath) {
    }

}

abstract class AbstractFileArchiver implements FileArchiver {

    String formattedArchiveWithTimePath(String archivePathPattern, ReadableInstant instant) {
        return archivePathPattern.replace("%{timestamp}", DateTimeUtils.dateTimePathFormatter.print(instant));
    }

    String formattedArchivePath(String archivePathPattern, LocalDate date) {
        return archivePathPattern.replace("%{timestamp}", DateTimeUtils.printBasicDate(date));
    }
}
