package tasks;

import  managers.task.Status;

public class Task {
    private String name;
    private String about;
    private int id;
    private Status status;

    public Task(String name, String about) {
        this.name = name;
        this.about = about;
        this.status = Status.NEW;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status newStatus){
        this.status=newStatus;
    }

    public void setName(String newName){
        this.name=newName;
    }

    public void setAbout(String newAbout) {
        this.about = newAbout;
    }

    @Override
    public String toString() {
        return "Tasks.Task{" +
                "name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
