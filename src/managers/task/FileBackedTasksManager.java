package managers.task;

import managers.Managers;
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
        File file = new File(home, "testFile.csv");

        TaskManager taskManager5 = Managers.getDefault(file);

        Task task = new Task("Работа", "Работать");
        taskManager5.makeTask(task);

        Epic epic = new Epic("Переезд", "Ура!");
        taskManager5.makeEpic(epic);

        Subtask subtask = new Subtask("Собрать вещи", "Все-все!", epic);
        taskManager5.makeSubtask(subtask);
        taskManager5.updateSubtask(subtask, subtask.getId(), Status.IN_PROGRESS);

        Subtask subtask2 = new Subtask("Упаковать коробки", "Плотно-плотно!", epic);
        taskManager5.makeSubtask(subtask2);
        taskManager5.updateSubtask(subtask2, subtask2.getId(), Status.DONE);

        Epic epic2 = new Epic("Уборка", "Опять:(");
        taskManager5.makeEpic(epic2);

        Subtask subtask21 = new Subtask("Помыть окна", "Без разводов!", epic2);
        taskManager5.makeSubtask(subtask21);
        taskManager5.updateSubtask(subtask21, subtask21.getId(), Status.IN_PROGRESS);

        Subtask subtask22 = new Subtask("Убрать пыль", "Всю!", epic2);
        taskManager5.makeSubtask(subtask22);
        taskManager5.updateSubtask(subtask22, subtask22.getId(), Status.DONE);

        System.out.println("Визуализация методов");
        System.out.println("=========================================================");
        System.out.println("Вывод всех данных");
        System.out.println(taskManager5);
        System.out.println("=========================================================");
        System.out.println("Получить задачу по ID");
        System.out.println(taskManager5.getTaskById(epic2.getId()));
        System.out.println(taskManager5.getTaskById(subtask21.getId()));
        System.out.println(taskManager5.getTaskById(task.getId()));
        System.out.println("=========================================================");
        System.out.println("Обновление данных Tasks.Subtask");
        System.out.println(taskManager5.getTaskById(subtask.getId()));
        Subtask subtask3 = new Subtask("Разобрать вещи", "Так надо", epic);
        taskManager5.updateSubtask(subtask3, subtask.getId(), Status.DONE);
        System.out.println(taskManager5.getTaskById(subtask.getId()));
        System.out.println("=========================================================");
        System.out.println("Обновление данных Tasks.Task");
        System.out.println(taskManager5.getTaskById(task.getId()));
        Task task2 = new Task("Учиться", ":)");
        taskManager5.updateTask(task2, task.getId());
        System.out.println(taskManager5.getTaskById(task.getId()));
        System.out.println("=========================================================");
        System.out.println("Получение подзадач Tasks.Epic");
        System.out.println(taskManager5.getEpicSubtasks(epic));
        System.out.println("=========================================================");
        System.out.println("Удалить все задачи");
        System.out.println(taskManager5);
        //taskManager.clearAllTask();
        System.out.println(taskManager5);
        System.out.println("=========================================================");
        System.out.println("Вывод истории просмотра");
        taskManager5.getTaskById(3);
        taskManager5.getTaskById(1);
        taskManager5.getTaskById(7);
        taskManager5.getTaskById(6);
        taskManager5.getTaskById(4);
        taskManager5.getTaskById(7);
        taskManager5.history().forEach(System.out::println);
        System.out.println("=========================================================");
        System.out.println("Display manager5");
        System.out.println(toString(taskManager5));
        System.out.println("=========================================================");
        System.out.println("Display manager from file");
        TaskManager manager = loadFromFile(file);
        System.out.println(toString(manager));
        System.out.println("=========================================================");

    }

    public FileBackedTasksManager(File file) {
        FileBackedTasksManager.file = file;
    }

    public void save(){
        try(FileWriter fileWriter = new FileWriter(String.valueOf(file))){
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

    public Task fromStringTask(String value){
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

    public static List<Integer>  fromStringList(String value){
        List<Integer> idList = new ArrayList<>();
        String idString[] = value.split(System.lineSeparator());
        for (int i = 0; i < idString.length; i++){
            if (idString[i].isBlank()){
                String t[] = idString[i+1].split(",");
                for(String b : t){
                    idList.add(Integer.parseInt(b));
                }
            }
        }
        return idList;
    }

    public static String toString(TaskManager manager) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(getFile()));
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
            Reader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                String line = br.readLine();
                sb.append(line + System.lineSeparator());
            }
            br.close();

            File newFile = new File(home, "testLoader.csv");
            Writer fileWriter = new FileWriter(newFile);
            fileWriter.write(sb.toString());
            fileWriter.close();
            return new FileBackedTasksManager(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public Task getTaskById(int id){
        if (taskMap.containsKey(id)) {
            inMemoryHistoryManager.add(taskMap.get(id));
            save();
            return taskMap.get(id);
        } else if (epicMap.containsKey(id)){
            inMemoryHistoryManager.add(epicMap.get(id));
            save();
            return  epicMap.get(id);
        } else if (subTaskMap.containsKey(id)){
            inMemoryHistoryManager.add(subTaskMap.get(id));
            save();
            return  subTaskMap.get(id);
        }
        return null;
    }

    @Override
    public void removeTaskById(int id){
        if (taskMap.containsKey(id)){
            taskMap.remove(id);
            save();
        } else if (epicMap.containsKey(id)){
            epicMap.remove(id);
            save();
        } else if (subTaskMap.containsKey(id)){
            subTaskMap.remove(id);
            save();
        }
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

    public static File getFile() {
        return file;
    }
}
