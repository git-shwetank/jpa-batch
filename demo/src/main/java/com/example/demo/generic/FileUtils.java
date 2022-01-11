package com.example.demo.generic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

    public static void deleteFolderRecursively(File folder) throws IOException {
        Files.walk(folder.toPath())
                .sequential()
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    private static Pattern FILE_EXTENSION_PATTERN = Pattern.compile("\\.\\w{3}$");

    public static String addSuffixBeforeExtensionToFilename(String filename, String failureFileSuffix) {
        Matcher matcher = FILE_EXTENSION_PATTERN.matcher(filename);
        if (matcher.find()) {
            String extension = filename.substring(matcher.start());
            return filename.substring(0, matcher.start()) + failureFileSuffix + extension;
        } else
            return filename + failureFileSuffix;
    }

    /*public static File writeStringToFileAs(String document, String prefix , String suffix) throws IOException {
        File tempFile = File.createTempFile(prefix, suffix);
        tempFile.deleteOnExit();

        if(suffix.equalsIgnoreCase(".xlsx")){
            XSSFWorkbook contentForFile = convertToXlsx(document, prefix);
            contentForFile.write(new FileOutputStream(tempFile));
        }else{
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            bw.write(document);
            bw.close();
        }
        return tempFile;
    }*/

    /*public static File compileTemplateToFile(String templatePath) throws IOException {
        HashMap<String, String> templateValues = new HashMap<String, String>() {{
            put("reportDate", previousWeekDay(LocalDate.now())
                    .toDateTimeAtCurrentTime(UTC)
                    .toString("yyyyMMdd"));
        }};
        InputStream templateStream = JavaUtils.getResourceFileAsStream(templatePath);
        return new MustacheFileCompiler().compileTemplate(templateStream, templateValues);
    }*/
}
