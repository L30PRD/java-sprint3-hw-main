package managers;

import managers.history.HistoryManager;
import managers.history.InMemoryHistoryManager;
import managers.task.FileBackedTasksManager;
import managers.task.InMemoryTaskManager;
import managers.task.TaskManager;

import java.io.File;
import java.io.IOException;

public abstract class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getDefault(File file) {
        return new FileBackedTasksManager(file);
    }

    public  static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
