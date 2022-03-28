package managers.task;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    public Status calculateStatus(Epic epic);

    public void makeEpic(Epic epic);

    public void makeSubtask(Subtask subtask);

    public void makeTask(Task task);

    public Task getTaskById(int id);

    public void removeTaskById(int id);

    public HashMap <Integer, Subtask> getEpicSubtasks(Epic epic);

    public void clearAllTask();

    public void clearTask();

    public void clearEpic();

    public void clearSubtask();

    public void updateEpic(Epic epic, int id);

    public void updateTask(Task task, int id);

    public void updateSubtask(Subtask subtask, int id, Status status);

    public List<Task> history();
}
