import managers.Managers;

import managers.task.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import managers.task.Status;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File f = new File("");
        TaskManager taskManager = Managers.getDefault(f);

        Task task = new Task("Работа", "Работать");
        taskManager.makeTask(task);

        Epic epic = new Epic("Переезд", "Ура!");
        taskManager.makeEpic(epic);

        Subtask subtask = new Subtask("Собрать вещи","Все-все!", epic);
        taskManager.makeSubtask(subtask);
        taskManager.updateSubtask(subtask, subtask.getId(), Status.IN_PROGRESS);

        Subtask subtask2 = new Subtask("Упаковать коробки", "Плотно-плотно!", epic);
        taskManager.makeSubtask(subtask2);
        taskManager.updateSubtask(subtask2, subtask2.getId(), Status.DONE);

        Epic epic2 = new Epic("Уборка", "Опять:(");
        taskManager.makeEpic(epic2);

        Subtask subtask21 = new Subtask("Помыть окна", "Без разводов!", epic2);
        taskManager.makeSubtask(subtask21);
        taskManager.updateSubtask(subtask21, subtask21.getId(), Status.IN_PROGRESS);

        Subtask subtask22 = new Subtask("Убрать пыль", "Всю!", epic2);
        taskManager.makeSubtask(subtask22);
        taskManager.updateSubtask(subtask22, subtask22.getId(), Status.DONE);

        System.out.println("Визуализация методов");
        System.out.println("=========================================================");
        System.out.println("Вывод всех данных");
        System.out.println(taskManager);
        System.out.println("=========================================================");
        System.out.println("Получить задачу по ID");
        System.out.println(taskManager.getTaskById(epic2.getId()));
        System.out.println(taskManager.getTaskById(subtask21.getId()));
        System.out.println(taskManager.getTaskById(task.getId()));
        System.out.println("=========================================================");
        System.out.println("Обновление данных Tasks.Subtask");
        System.out.println(taskManager.getTaskById(subtask.getId()));
        Subtask subtask3 = new Subtask("Разобрать вещи", "Так надо", epic);
        taskManager.updateSubtask(subtask3, subtask.getId(), Status.DONE);
        System.out.println(taskManager.getTaskById(subtask.getId()));
        System.out.println("=========================================================");
        System.out.println("Обновление данных Tasks.Task");
        System.out.println(taskManager.getTaskById(task.getId()));
        Task task2 = new Task("Учиться", ":)");
        taskManager.updateTask(task2, task.getId());
        System.out.println(taskManager.getTaskById(task.getId()));
        System.out.println("=========================================================");
        System.out.println("Получение подзадач Tasks.Epic");
        System.out.println(taskManager.getEpicSubtasks(epic));
        System.out.println("=========================================================");
        System.out.println("Удалить все задачи");
        System.out.println(taskManager);
        //taskManager.clearAllTask();
        System.out.println(taskManager);
        System.out.println("=========================================================");
        System.out.println("Вывод истории просмотра");
        taskManager.getTaskById(3);
        taskManager.getTaskById(1);
        taskManager.getTaskById(7);
        taskManager.getTaskById(6);
        taskManager.getTaskById(4);
        taskManager.getTaskById(7);
        taskManager.history().forEach(System.out::println);
        System.out.println("=========================================================");
    }
}
