package googletask.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.GoogleTasks;
import models.Task;
import models.TaskItems;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class Main {
    private static final boolean SKIP_COMPLETED = true;
    private static final boolean SKIP_EMPTY = true;
    private static final boolean SKIP_HIDDEN = true;
    private static final boolean SKIP_DELETED = true;
    private static final Gson gson = new GsonBuilder().create();

    public static void main(String[] args) {
        String outputCatalog = System.getProperty("user.dir");
        String json = FileTools.readFileToString(System.getProperty("user.dir") + "/Tasks.json");
        GoogleTasks googleTasks = gson.fromJson(json, GoogleTasks.class);

        LinkedHashMap<String, LinkedHashSet<String>> extractedCsvFiles = convertGoogleTasksJsonToTodoistCsv(googleTasks);
        extractedCsvFiles.forEach((fileName, tasks) -> {
            FileTools.saveTasksToFile(outputCatalog, fileName, tasks, true);
        });
    }

    public static LinkedHashMap<String, LinkedHashSet<String>> convertGoogleTasksJsonToTodoistCsv(GoogleTasks googleTasks) {
        LinkedHashMap<String, LinkedHashSet<String>> uncompletedTasksListMap = new LinkedHashMap<>();
        List<TaskItems> taskItems = googleTasks.getTasks();

        for (TaskItems taskItem : taskItems) {
            List<Task> tasks = taskItem.getItems();
            new LinkedHashSet<>();

            if (tasks != null) {
                LinkedHashSet<Task> uncompletedTasks = extractUncompletedTasks(tasks);
                LinkedHashSet<String> uncompletedTasksCsv = uncompletedTasksToString(uncompletedTasks);
                uncompletedTasksListMap.put(taskItem.getTitle(), uncompletedTasksCsv);
            }
        }
        return uncompletedTasksListMap;
    }

    private static LinkedHashSet<String> uncompletedTasksToString(LinkedHashSet<Task> uncompletedTasks) {
        LinkedHashSet<String> uncompletedTasksCsv = new LinkedHashSet<>();
        uncompletedTasks.forEach(task ->
                uncompletedTasksCsv.add(taskToString(task).trim())
        );
        return uncompletedTasksCsv;
    }

    private static LinkedHashSet<Task> extractUncompletedTasks(List<Task> tasks) {
        LinkedHashSet<Task> uncompletedTasks = new LinkedHashSet<>();
        for (Task task : tasks) {
            boolean deleted = task.getDeleted() && SKIP_DELETED;
            boolean hidden = task.getHidden() && SKIP_HIDDEN;
            boolean completed = task.getStatus().equals("completed") && SKIP_COMPLETED;
            boolean empty = task.getTitle().trim().equals("") && SKIP_EMPTY;

            if (!deleted && !hidden && !completed && !empty) {
                if (!task.getTitle().trim().equals("")) {
                    uncompletedTasks.add(task);
                }
            }
        }
        return uncompletedTasks;
    }

    static String taskToString(Task task) {
        // TYPE,CONTENT,PRIORITY,INDENT,AUTHOR,RESPONSIBLE,DATE,DATE_LANG,TIMEZONE
        StringBuilder stringBuilder = new StringBuilder();
        // TYPE
        stringBuilder.append("task,");
        // Content
        stringBuilder.append(task.getTitle().trim()).append(",");
        // PRIORITY
        stringBuilder.append("4,");
        // INDENT
        stringBuilder.append("1,");
        // AUTHOR
        stringBuilder.append(",");
        // RESPONSIBLE
        stringBuilder.append(",");
        // DATE
        if (task.getDue() != null) {
            stringBuilder.append(task.getDue()).append(",");
        }
        // DATE_LANG
        stringBuilder.append(",");
        // TIMEZONE
        stringBuilder.append(",");
        return stringBuilder.toString();
    }
}
