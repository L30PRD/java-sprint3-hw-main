package managers.task;

import managers.history.HistoryManager;
import managers.Managers;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    int id = 1;
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subTaskMap = new HashMap<>();
    private final HashMap <Integer, Task> taskMap = new HashMap<>();
    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    private int generateId(){
        return id++;
    }

    @Override
    public Status calculateStatus(Epic epic) {
        int sumSubtask = 0;
        int newSubtask = 0;
        int doneSubtask = 0;

        HashMap<Integer, Subtask> tempMap = new HashMap<>();
        for(Integer taskId : epic.getSubTaskId()){
            tempMap.put(taskId, subTaskMap.get(taskId));
            sumSubtask++;
        }
        for(Subtask getSubtask : tempMap.values()){
            if (getSubtask.getStatus().equals(Status.NEW)){
                newSubtask++;
            } else if (getSubtask.getStatus().equals(Status.DONE)){
                doneSubtask++;
            }
        }

        if (epic.getSubTaskId().isEmpty() || sumSubtask == newSubtask){
            return Status.NEW;
        } else if (sumSubtask == doneSubtask){
            return Status.DONE;
        }
        return Status.IN_PROGRESS;
    }

    @Override
    public void makeEpic(Epic epic){
        epic.setId(generateId());
        epicMap.put(epic.getId(), epic);
    }

    @Override
    public void makeSubtask(Subtask subtask){
        subtask.setId(generateId());
        subTaskMap.put(subtask.getId(), subtask);
        subtask.getEpic().updateSubTaskId(subtask.getId());
    }

    @Override
    public void makeTask(Task task){
        task.setId(generateId());
        taskMap.put(task.getId(), task);
    }

    @Override
    public Task getTaskById(int id){
        if (taskMap.containsKey(id)) {
            inMemoryHistoryManager.add(taskMap.get(id));
            return taskMap.get(id);
        } else if (epicMap.containsKey(id)){
            inMemoryHistoryManager.add(epicMap.get(id));
            return  epicMap.get(id);
        } else if (subTaskMap.containsKey(id)){
            inMemoryHistoryManager.add(subTaskMap.get(id));
            return  subTaskMap.get(id);
        }
        return null;
    }

    @Override
    public void removeTaskById(int id){
        if (taskMap.containsKey(id)){
            taskMap.remove(id);
        } else if (epicMap.containsKey(id)){
            epicMap.remove(id);
        } else if (subTaskMap.containsKey(id)){
            subTaskMap.remove(id);
        }
    }

    @Override
    public HashMap <Integer, Subtask> getEpicSubtasks(Epic epic){
        HashMap <Integer, Subtask> temporaryMap = new HashMap<>();
        for(Integer taskId : epic.getSubTaskId()){
            temporaryMap.put(taskId, subTaskMap.get(taskId));
        }
        return temporaryMap;
    }

    @Override
    public void clearAllTask() {
        epicMap.clear();
        subTaskMap.clear();
        taskMap.clear();
    }

    @Override
    public void clearTask(){
        taskMap.clear();
    }

    @Override
    public void clearEpic(){
        epicMap.clear();
        subTaskMap.clear();
    }

    @Override
    public void clearSubtask(){
        subTaskMap.clear();
    }

    @Override
    public void updateEpic(Epic epic, int id){
        epic.setId(id);
        epicMap.put(id, epic);
    }

    @Override
    public void updateTask(Task task, int id){
        task.setId(id);
        taskMap.put(id, task);
    }

    @Override
    public void updateSubtask(Subtask subtask, int id, Status status){
        subtask.setStatus(status);
        subtask.setId(id);
        subTaskMap.put(id, subtask);
        subtask.getEpic().setStatus(calculateStatus(subtask.getEpic()));
    }

    @Override
    public String toString() {
        return "Managers.Managers.InMemoryTaskManager{" +
                "epicMap=" + epicMap +
                ", subtaskMap=" + subTaskMap +
                ", taskMap=" + taskMap +
                '}';
    }

    @Override
    public List<Task> history(){
        return inMemoryHistoryManager.getHistory();
    }
}
