package tasks;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String about, Epic epic) {
        super(name, about);
        this.epic = epic;
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
