package googletask.converter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashSet;

public class FileTools {
    public static String readFileToString(String path) {
        String content = null;
        try {
            content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Tasks.json file not found.");
        }
        return content;
    }

    public static void saveTasksToFile(String outputCatalog, String fileName, LinkedHashSet<String> tasks, boolean headers) {
        String outputFileName = fileName.trim().replace('/', '-') + ".txt";
        Path filePath = Paths.get(outputCatalog, outputFileName);

        StringBuilder stringBuilder = new StringBuilder();
        if (headers) {
            stringBuilder.append("TYPE,CONTENT,PRIORITY,INDENT,AUTHOR,RESPONSIBLE,DATE,DATE_LANG,TIMEZONE").append('\n');
        }
        tasks.forEach(s -> stringBuilder.append(s).append('\n'));
        String taskList = stringBuilder.toString().trim();

        try {
            Files.writeString(filePath, taskList, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
