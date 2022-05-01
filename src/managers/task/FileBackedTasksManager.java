package managers.task;

import managers.Managers;
import managers.history.HistoryManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    public static File file;
    private static final String home = System.getProperty("./");
    public static void main(String[] args) {
        File data = new File(home, "testFile.csv");

        TaskManager manager = loadFromFile(data);

    }

    public FileBackedTasksManager(File file) {
        FileBackedTasksManager.file = file;
    }

    public void save(){
        try(FileWriter fileWriter = new FileWriter(file)){
            StringBuilder sb = new StringBuilder("id,type,name,status,description,epic\n");

            for(Integer id : taskMap.keySet()){
                Task task = taskMap.get(id);
                sb.append(id).append(",");
                sb.append(TaskType.TASK).append(",");
                sb.append(task.getName()).append(",");
                sb.append(task.getStatus()).append(",");
                sb.append(task.getAbout()).append("\n");
            }

            for(Integer id : epicMap.keySet()){
                Epic epic = epicMap.get(id);
                sb.append(id).append(",");
                sb.append(TaskType.EPIC).append(",");
                sb.append(epic.getName()).append(",");
                sb.append(epic.getStatus()).append(",");
                sb.append(epic.getAbout()).append("\n");
            }

            for(Integer id : subTaskMap.keySet()){
                Subtask subtask = subTaskMap.get(id);
                sb.append(id).append(",");
                sb.append(TaskType.SUBTASK).append(",");
                sb.append(subtask.getName()).append(",");
                sb.append(subtask.getStatus()).append(",");
                sb.append(subtask.getAbout()).append(",");
                sb.append(subtask.getEpic().getId()).append("\n");
            }

            sb.append(System.lineSeparator());

            for(Task task : history()){
                sb.append(task.getId()).append(",");
            }

            fileWriter.write(sb.toString());


            File newFile = new File(home, "testLoader.csv");
            Writer fileWriter2 = new FileWriter(newFile);
            fileWriter2.write(sb.toString());
            fileWriter2.close();



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

    public Task fromStringTask(String value) {
        String[] data = value.split(",");
        if ("TASK".equals(data[1])) {
            Task task = new Task(data[2], data[4]);
            task.setId(Integer.parseInt(data[0]));
            task.setStatus(Status.valueOf(data[3]));
            task.setType(TaskType.TASK);
            return task;
        }
        return null;
    }

    public Epic fromStringEpic(String value){
        String[] data = value.split(",");
        if ("EPIC".equals(data[1])) {
            Epic epic = new Epic(data[2], data[4]);
            epic.setId(Integer.parseInt(data[0]));
            epic.setStatus(Status.valueOf(data[3]));
            epic.setType(TaskType.EPIC);
            return epic;
        }
        return null;
    }

    public Subtask fromStringSubtask(String value){
        String[] data = value.split(",");
        if ("SUBTASK".equals(data[1])) {
            Subtask subtask = new Subtask(data[2], data[4], epicMap.get(Integer.parseInt(data[5])));
            subtask.setId(Integer.parseInt(data[0]));
            subtask.setStatus(Status.valueOf(data[3]));
            subtask.setType(TaskType.SUBTASK);
            return subtask;
        }
        return null;
    }

    public static List<Integer>  fromStringList(String value){
        List<Integer> idList = new ArrayList<>();
        String idString[] = value.split(System.lineSeparator());
        for (int i = 1; i < idString.length; i++){
            if (idString[i].isBlank()){
                String t[] = idString[i+1].split(",");
                for(String b : t){
                    idList.add(Integer.parseInt(b));
                }
            }
        }
        return idList;
    }

    public static String toString(FileBackedTasksManager manager) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(manager.getFile()));
            String line;
            while ((line = br.readLine()) != null) sb.append(line + "\n");
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        try {
            FileBackedTasksManager loader = new FileBackedTasksManager(file);
            HistoryManager history = Managers.getDefaultHistory();
            Reader fileReader = new FileReader(loader.getFile());
            BufferedReader br = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();

            while (br.ready()) {
                String line = br.readLine();
                sb.append(line + System.lineSeparator());
            }
            br.close();
            fileReader.close();

            String listTask[] = sb.toString().split(System.lineSeparator());
            for(int i = 1; i < listTask.length; i++) {
                if(!listTask[i].isEmpty()) {
                    String[] data = listTask[i].split(",");
                    if ("TASK".equals(data[1])) {
                        Task task = new Task(data[2], data[4]);
                        task.setId(Integer.parseInt(data[0]));
                        task.setStatus(Status.valueOf(data[3]));
                        task.setType(TaskType.TASK);
                        taskMap.put(task.getId(), task);

                    } else if ("EPIC".equals(data[1])) {
                        Epic epic = new Epic(data[2], data[4]);
                        epic.setId(Integer.parseInt(data[0]));
                        epic.setStatus(Status.valueOf(data[3]));
                        epic.setType(TaskType.EPIC);
                        epicMap.put(epic.getId(), epic);

                    } else if ("SUBTASK".equals(data[1])) {
                        Subtask subtask = new Subtask(data[2], data[4], epicMap.get(Integer.parseInt(data[5])));
                        subtask.setId(Integer.parseInt(data[0]));
                        subtask.setStatus(Status.valueOf(data[3]));
                        subtask.setType(TaskType.SUBTASK);
                        subTaskMap.put(subtask.getId(), subtask);

                    }
                }
            }

            List<Integer> idList = fromStringList(sb.toString());
            for(Integer id : idList) {
                history.add(loader.getTaskById(id));
            }
            loader.save();
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void makeTask(Task task){
        taskMap.put(task.getId(), task);
        save();
    }

    @Override
    public void makeEpic(Epic epic){
        epicMap.put(epic.getId(), epic);
        save();
    }

    @Override
    public void makeSubtask(Subtask subtask){
        subTaskMap.put(subtask.getId(), subtask);
        //subtask.getEpic().updateSubTaskId(subtask.getId());
        save();
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
        save();
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
        save();
    }

    @Override
    public void updateEpic(Epic epic, int id){
        epic.setId(id);
        epicMap.put(id, epic);
        save();
    }

    @Override
    public void updateTask(Task task, int id){
        task.setId(id);
        taskMap.put(id, task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask, int id, Status status){
        subtask.setStatus(status);
        subtask.setId(id);
        subTaskMap.put(id, subtask);
        subtask.getEpic().setStatus(calculateStatus(subtask.getEpic()));
        save();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        FileBackedTasksManager.file = file;
    }
}
