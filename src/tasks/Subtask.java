package tasks;

import managers.task.TaskType;

public class Subtask extends Task {
    private Epic epic;
    private TaskType type;

    public Subtask(String name, String about, Epic epic) {
        super(name, about);
        this.epic = epic;
        this.type = TaskType.SUBTASK;
    }

    @Override
    public TaskType getType() {
        return type;
    }

    @Override
    public void setType(TaskType type) {
        this.type = type;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "Tasks.Subtask{" +
                "name='" + super.getName() + '\'' +
                ", about='" + super.getAbout() + '\'' +
                ", id=" + super.getId() +
                ", status='" + super.getStatus() + '\'' +
                ", epic='" + epic.getName() + '\'' +
                '}';
    }
}
