package managers.task;

import managers.history.HistoryManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    Path data;


    public static void main(String[] args){
        try {
            Path path = Files.createFile(Paths.get("/Users/leonid/IdeaProjects/test", "testFile.csv"));
            FileBackedTasksManager taskManager2 = new FileBackedTasksManager(path);

            Task task = new Task("Работа", "Работать");
            taskManager2.makeTask(task);

            Epic epic = new Epic("Переезд", "Ура!");
            taskManager2.makeEpic(epic);

            Subtask subtask = new Subtask("Собрать вещи","Все-все!", epic);
            taskManager2.makeSubtask(subtask);
            taskManager2.updateSubtask(subtask, subtask.getId(), Status.IN_PROGRESS);

            Subtask subtask2 = new Subtask("Упаковать коробки", "Плотно-плотно!", epic);
            taskManager2.makeSubtask(subtask2);
            taskManager2.updateSubtask(subtask2, subtask2.getId(), Status.DONE);

            taskManager2.getTaskById(3);
            taskManager2.getTaskById(1);
            taskManager2.getTaskById(4);
            taskManager2.getTaskById(2);

            Files.readString(Path.of(String.valueOf(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FileBackedTasksManager(Path data) throws IOException {
        this.data = data;
    }

    public void save(){
        try(FileWriter fileWriter = new FileWriter(String.valueOf(data))){
            StringBuilder sb = new StringBuilder("id,type,name,status,description,epic\n");

            for(Integer id : taskMap.keySet()){
                Task task = taskMap.get(id);
                sb.append(id).append(",");
                sb.append(TaskType.TASK).append(",");
                sb.append(task.getName()).append(",");
                sb.append(task.getStatus()).append(",");
                sb.append(task.getAbout()).append(",\n");
            }

            for(Integer id : epicMap.keySet()){
                Epic epic = epicMap.get(id);
                sb.append(id).append(",");
                sb.append(TaskType.EPIC).append(",");
                sb.append(epic.getName()).append(",");
                sb.append(epic.getStatus()).append(",");
                sb.append(epic.getAbout()).append(",\n");
            }

            for(Integer id : subTaskMap.keySet()){
                Subtask subtask = subTaskMap.get(id);
                sb.append(id).append(",");
                sb.append(TaskType.SUBTASK).append(",");
                sb.append(subtask.getName()).append(",");
                sb.append(subtask.getStatus()).append(",");
                sb.append(subtask.getAbout()).append(",");
                sb.append(subtask.getEpic().getId()).append(",\n");

            }
            sb.append(" \n");
            for(Integer task : taskMap.keySet()){
                sb.append(task).append(",");
            }

            for(Integer task : epicMap.keySet()){
                sb.append(task).append(",");
            }

            for(Integer task : subTaskMap.keySet()){
                sb.append(task).append(",");
            }

            fileWriter.write(String.valueOf(sb));

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения.");
        }
    }

    public String toString(Task task){
        if(taskMap.containsKey(task.getId())) {
            String sb = task.getId() + "," +
                    TaskType.TASK + "," +
                    task.getName() + "," +
                    task.getStatus() + "," +
                    task.getAbout();
            return sb;

        }
        return null;
    }

    public String toString(Epic epic){
        if(epicMap.containsKey(epic.getId())) {
            String sb = epic.getId() + "," +
                    TaskType.EPIC + "," +
                    epic.getName() + "," +
                    epic.getStatus() + "," +
                    epic.getAbout();
            return sb;
        }
        return null;
    }

    public String toString(Subtask subtask){
        if(subTaskMap.containsKey(subtask.getId())) {
            String sb = subtask.getId() + "," +
                    TaskType.SUBTASK + "," +
                    subtask.getName() + "," +
                    subtask.getStatus() + "," +
                    subtask.getAbout() + "," +
                    subtask.getEpic().getId();
            return sb;
        }
        return null;
    }

    public Task fromString(String value){
        String[] data = value.split(",");
        switch (data[1]) {
            case "TASK":
                Task task = new Task(data[2], data[4]);
                task.setId(Integer.parseInt(data[0]));
                task.setStatus(Status.valueOf(data[3]));
                return task;
            case "EPIC":
                Task epic = new Epic(data[2], data[4]);
                epic.setId(Integer.parseInt(data[0]));
                epic.setStatus(Status.valueOf(data[3]));
                return epic;
            case "SUBTASK":
                Task subtask = new Subtask(data[2], data[4], epicMap.get(Integer.parseInt(data[5])));
                subtask.setId(Integer.parseInt(data[0]));
                subtask.setStatus(Status.valueOf(data[3]));
                return subtask;
        }
        return null;
    }

    public static List<Integer> ffromString(String value){
        List<Integer> idList = new ArrayList<>();
        String[] data = value.split(",");
        for (String id : data){
            if(Integer.parseInt(id) > 0){
                idList.add(Integer.parseInt(id));
            }
        }
        return idList;
    }

    public static String toString(HistoryManager manager) {
        StringBuilder sb = new StringBuilder(manager.toString());
        return sb.toString();
    }

    public static FileBackedTasksManager loadFromFile(Path file) throws IOException {
        return new FileBackedTasksManager(file);
    }

    public Path getData() {
        return data;
    }

    @Override
    public void makeTask(Task task){
        super.makeTask(task);
        save();
    }

    @Override
    public void makeEpic(Epic epic){
        super.makeEpic(epic);
        save();
    }

    @Override
    public void makeSubtask(Subtask subtask){
        super.makeSubtask(subtask);
        save();
    }
}
