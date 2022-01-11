package com.example.demo;

import com.example.demo.generic.JavaUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.assertj.core.api.IterableAssert;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsIterableContaining;
import org.joda.time.DateTime;
import org.junit.jupiter.api.function.Executable;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.util.StreamUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.core.AllOf.allOf;
import static org.joda.time.DateTimeUtils.setCurrentMillisFixed;
import static org.joda.time.DateTimeUtils.setCurrentMillisSystem;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@CommonsLog
public class TestUtils {

    public static final BigDecimal LARGE_BIG_DECIMAL = new BigDecimal("78483297489324346.432432432455");

    public static byte[] getResourceFileAsBytes(String resourceFileName) throws IOException {
        return StreamUtils.copyToByteArray(JavaUtils.getResourceFileAsStream(resourceFileName));
    }

    public static String getResourceFileAsString(String resourceFileName) throws IOException {
        return new String(getResourceFileAsBytes(resourceFileName), "UTF-8");
    }

    public static String compileStubTemplate(String templateResourcePath, HashMap<String, String> params) throws IOException {
        String compiledTemplate = getResourceFileAsString(templateResourcePath);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            compiledTemplate = compiledTemplate.replace(format("{{%s}}", entry.getKey()), entry.getValue());
        }
        return compiledTemplate;
    }

    public static void copyResourceToFile(String resourceName, File targetFile) throws IOException {
        Files.copy(JavaUtils.getResourceFileAsStream(resourceName), targetFile.toPath());
    }

    public static IterableAssert<String> assertThatCollectionStartingWith(Collection<String> collection, String startingWith) {
        return assertThat(collection).filteredOn(filename -> filename.startsWith(startingWith));
    }

    public static JobExecution mockJobExecution(ExitStatus status) {
        JobExecution jobExecutionMock = mock(JobExecution.class);
        when(jobExecutionMock.getExitStatus()).thenReturn(status);
        return jobExecutionMock;
    }

    public static Matcher<Iterable<BigDecimal>> hasBigDecimalItems(String... items) {
        BigDecimal[] bigDecimals = Arrays.stream(items)
                .map(BigDecimal::new)
                .toArray(BigDecimal[]::new);
        return hasComparableItems(bigDecimals);
    }

    /*@Factory*/
    private static <T extends java.lang.Comparable<T>> Matcher<Iterable<T>> hasComparableItems(T... items) {
        List<Matcher<? super Iterable<T>>> all = new ArrayList<>(items.length);
        for (T element : items) {
            all.add(new IsIterableContaining<T>(comparesEqualTo(element)));
        }
        return allOf(all);
    }

    public static void runAtFixedTime(String time, Runnable func) {
        runAtFixedTime(DateTime.parse(time), func);
    }

    public static void runAtFixedTime(DateTime time, Runnable func) {
        try {
            setCurrentMillisFixed(time.getMillis());
            func.run();
        } finally {
            setCurrentMillisSystem();
        }
    }

    public static void runThrowableAtFixedTime(DateTime time, ThrowableRunnable func) throws Exception {
        try {
            setCurrentMillisFixed(time.getMillis());
            func.run();
        } finally {
            setCurrentMillisSystem();
        }
    }

    public static <U> U runThrowableSupplierAtFixedTime(DateTime time, ThrowableSupplier<U> func) throws Exception {
        try {
            setCurrentMillisFixed(time.getMillis());
            return func.run();
        } finally {
            setCurrentMillisSystem();
        }
    }

    public static void warnAboutSkippedAssertions(Exception e) {
        System.out.println("Your assertions might not have been run if you see this message!! " + e.getMessage());
    }

    public static void testForeignKeyConstraint(EntityManager entityManager) {
        PersistenceException persistenceException = assertThrows(PersistenceException.class, entityManager::flush);
        assertThat(persistenceException.getMessage()).containsIgnoringCase("ConstraintViolationException");
    }

    public static PersistenceException assertDatabaseConstraint(Executable executable, String constraintNameContains) {
        PersistenceException persistenceException = assertThrows(PersistenceException.class, executable);
        if (Objects.nonNull(constraintNameContains))
            assertThat(getHibernateConstraintName(persistenceException))
                    .containsIgnoringCase(constraintNameContains);
        return persistenceException;
    }

    public static String getHibernateConstraintName(PersistenceException persistenceException) {
        return ((org.hibernate.exception.ConstraintViolationException) persistenceException.getCause()).getConstraintName();
    }

    public interface ThrowableRunnable {
        void run() throws Exception;
    }

    public interface ThrowableSupplier<U> {
        U run() throws Exception;
    }

    public interface ThrowsFileNotFoundSupplier<U> {
        U run() throws FileNotFoundException;
    }

    public static <U> U retryFileNotFound(int backoff, TimeUnit unit, int maxRetries, ThrowsFileNotFoundSupplier<U> func) throws InterruptedException, FileNotFoundException {
        int failures = 0;
        U result;
        while (true) {
            try {
                result = func.run();
                break;
            } catch (FileNotFoundException error) {
                failures++;
                if (failures <= maxRetries) {
                    log.warn(format("failed %d times, retrying", failures));
                    Thread.sleep(TimeUnit.MILLISECONDS.convert(backoff, unit));
                } else {
                    log.warn(format("exceeded max of %d retries", maxRetries));
                    throw error;
                }
            }
        }

        if (failures > 0)
            log.debug(format("retried %d times before success", failures));

        return result;
    }

    public static Optional<String> getAbsoluteResourcePath(String resourcePath) {
        return Optional.ofNullable(resourcePath)
                .map(TestUtils.class.getClassLoader()::getResource)
                .map(URL::getFile)
                .map(File::new)
                .map(File::getAbsolutePath);
    }

    public static Optional<File> getFileObjectForResource(String resourcePath) {
        return getAbsoluteResourcePath(resourcePath)
                .map(File::new);
    }
}

