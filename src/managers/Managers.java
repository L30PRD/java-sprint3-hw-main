package managers;

import managers.history.HistoryManager;
import managers.history.InMemoryHistoryManager;
import managers.task.FileBackedTasksManager;
import managers.task.TaskManager;

import java.io.File;

public abstract class Managers {

    public static TaskManager getDefault(File file) {
        return new FileBackedTasksManager(file);
    }

    public  static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
