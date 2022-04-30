import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import googletask.converter.FileTools;
import models.GoogleTasks;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import static googletask.converter.Main.convertGoogleTasksJsonToTodoistCsv;
import static org.junit.Assert.assertTrue;

public class ExtractionTest {
    @Rule
    public TemporaryFolder folder = TemporaryFolder.builder()
            .parentFolder(new File(System.getProperty("user.dir") + "/src/test/"))
            .assureDeletion()
            .build();

    @Test
    public void extractTasks() throws IOException {
        folder.create();
        final Gson gson = new GsonBuilder().create();
        String outputCatalog = String.valueOf(folder.getRoot());

        String json = FileTools.readFileToString("./src/test/resources/Tasks.json");
        GoogleTasks googleTasks = gson.fromJson(json, GoogleTasks.class);

        LinkedHashMap<String, LinkedHashSet<String>> extractedCsvFiles = convertGoogleTasksJsonToTodoistCsv(googleTasks);
        extractedCsvFiles.forEach((fileName, tasks) -> {
            FileTools.saveTasksToFile(outputCatalog, fileName, tasks);
        });

        extractedCsvFiles.forEach((fileName, tasks) -> {
            File file = new File(outputCatalog, fileName + ".txt");
            assertTrue(file.exists());
        });

        extractedCsvFiles.forEach((fileName, tasks) -> {
            final File expected = new File(System.getProperty("user.dir") + "/src/test/resources", fileName + ".txt");
            final File output = new File(outputCatalog, fileName + ".txt");
            List<String> expectedOutput;
            List<String> actualOutput;
            try {
                expectedOutput = FileUtils.readLines(expected, StandardCharsets.UTF_8);
                actualOutput = FileUtils.readLines(output, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Assert.assertEquals(expectedOutput, actualOutput);
        });
    }
}