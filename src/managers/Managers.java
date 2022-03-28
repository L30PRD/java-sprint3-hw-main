package managers;

import managers.history.HistoryManager;
import managers.history.InMemoryHistoryManager;
import managers.task.InMemoryTaskManager;
import managers.task.TaskManager;

public abstract class Managers implements TaskManager {

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public  static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
