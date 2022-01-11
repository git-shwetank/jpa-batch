package com.example.demo.generic;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

public class JavaUtils {

    public static <T> boolean nullableComparable(Comparable<T> comparable, T otherComparable) {
        if (comparable == null && otherComparable == null)
            return true;
        else if (comparable == null ^ otherComparable == null) {
            return false;
        } else {
            return comparable.compareTo(otherComparable) == 0;
        }
    }

    public static <S, T extends Comparable<? super T>> Comparator<S> makeNullLastComparator(Function<S, T> compareOn) {
        return (ca1, ca2) -> {
            T value1 = compareOn.apply(ca1);
            T value2 = compareOn.apply(ca2);
            if (isNull(value1) && isNull(value2))
                return 0;
            else if (isNull(value1))
                return 1;
            else if (isNull(value2))
                return -1;
            else return value1.compareTo(value2);
        };
    }

    public static String _toUpperCase(String string) {
        if (Objects.nonNull(string))
            return string.toUpperCase();
        else
            return null;
    }

    public static <T> List<T> removeEmptyOptsAndUnpackInList(Stream<Optional<T>> optionals) {
        return optionals
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public static <T> Stream<T> iterableToStream(Iterable<T> items) {
        return StreamSupport.stream(items.spliterator(), false);
    }

    /*public static TaskInvocationResult mergeTaskInvocationResults(TaskInvocationResult result1, TaskInvocationResult result2) {
        return combineTaskInvocationResults(Stream.of(result1, result2));
    }

    public static TaskInvocationResult combineTaskInvocationResults(Stream<TaskInvocationResult> taskInvocationResultStream) {
        TaskInvocationResult.TaskInvocationResultBuilder resultBuilder = TaskInvocationResult.builder()
                .success(true);
        String combinedErrorMessage = taskInvocationResultStream.filter(result -> !result.getSuccess())
                .peek(x -> resultBuilder.success(false))
                .filter(x -> x.getErrorMessage() != null)
                .map(TaskInvocationResult::getErrorMessage)
                .collect(Collectors.joining("\n"));
        return resultBuilder.errorMessage(combinedErrorMessage)
                .build();
    }

    public static TaskInvocationResult combineTaskInvocationResults(List<TaskInvocationResult> taskInvocationResultListOfList) {
        Stream<TaskInvocationResult> taskInvocationResultStream = taskInvocationResultListOfList.stream();
        TaskInvocationResult.TaskInvocationResultBuilder resultBuilder = TaskInvocationResult.builder()
                .success(true);
        String combinedErrorMessage = taskInvocationResultStream.filter(result -> !result.getSuccess())
                .peek(x -> resultBuilder.success(false))
                .filter(x -> x.getErrorMessage() != null)
                .map(TaskInvocationResult::getErrorMessage)
                .collect(Collectors.joining("\n"));
        return resultBuilder.errorMessage(combinedErrorMessage)
                .build();
    }*/

    public static InputStream getResourceFileAsStream(String resourceFileName) {
        return JavaUtils.class.getClassLoader().getResourceAsStream(resourceFileName);
    }

    public static File getFileFromInputStream(InputStream inputStream, String ext) {
        File file = null;
        try {
            file = File.createTempFile("temp_file", ext);
            file.deleteOnExit();
            OutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File getFileFromInputStream(InputStream inputStream) {
        return getFileFromInputStream(inputStream, ".xlsx");
    }

    public static <T> List<List<T>> partition(List<T> list, int partitionSize) {
        List<List<T>> result = new ArrayList<>();
        int nextIndex;
        for (int i = 0; i < list.size(); i = nextIndex) {
            nextIndex = Math.min(i + partitionSize, list.size());
            result.add(list.subList(i, nextIndex));
        }
        return result;
    }

    /*
     * Stricter matching without using findAny()
     */
    public static boolean compareMapData(Map<String, Object> data, Map<String, Object> data1) {
        if (data.isEmpty() && data1.isEmpty())
            return true;

        if (data.size() != data1.size())
            return false;

        return data.entrySet().stream()
                .filter(value -> data1.entrySet().stream().anyMatch(value1 -> (value1.getKey().equals(value.getKey()) && value1.getValue().equals(value.getValue()))))
                .count()
                == data.entrySet().size();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static String checkEmptyConvertToNull(String value) {
        return (value == null || value.isEmpty()) ? null : value;
    }

    public static String checkNullConvertToEmpty(String value) {
        return (value == null || value.isEmpty() || value.equalsIgnoreCase("null")) ? "" : value;
    }

    /*public static JSONObject convertStringToJson(String jsonString) throws ParseException {
        JSONParser parser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        return (JSONObject) parser.parse(jsonString);
    }*/

    public static Long getResourceFileSize(String resourceFileName) throws URISyntaxException, IOException {
        return Files.size(Paths.get(JavaUtils.class.getClassLoader().getResource(resourceFileName).toURI()));
    }
}
