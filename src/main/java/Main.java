import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.GoogleTasks;
import models.Task;
import models.TaskItems;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class Main {
    private static Gson gson = new GsonBuilder().create();

    public static void main(String[] args) {
        String json = FileTools.readFileToString("Tasks.json");
        GoogleTasks googleTasks = gson.fromJson(json, GoogleTasks.class);

        LinkedHashMap<String, LinkedHashSet<String>> uncompletedTasksListMap = new LinkedHashMap<>();
        List<TaskItems> taskItems = googleTasks.getTasks();

        for (int taskListIndex = 0; taskListIndex < taskItems.size(); ++taskListIndex) {
            List<Task> tasks = taskItems.get(taskListIndex).getItems();
            LinkedHashSet<String> uncompletedTasks = new LinkedHashSet<>();

            for (Task task : tasks) {
                if (!task.getStatus().equals("completed") && !task.getDeleted() && !task.getHidden())
                    if (!task.getTitle().trim().equals(""))
                        uncompletedTasks.add(task.getTitle().trim());
            }
            uncompletedTasksListMap.put(taskItems.get(taskListIndex).getTitle(), uncompletedTasks);
        }

        uncompletedTasksListMap.forEach(FileTools::saveTasksToFile);
    }
}
