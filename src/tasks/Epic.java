package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList <Integer> listSubtaskId;

    public Epic(String name, String about) {
        super(name, about);
        this.listSubtaskId = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTaskId() {
        return listSubtaskId;
    }

    public void updateSubTaskId(int getSubId) {
        listSubtaskId.add(getSubId);
    }

    @Override
    public String toString() {
        return "Tasks.Epic{" +
                "name='" + super.getName() + '\'' +
                ", about='" + super.getAbout() + '\'' +
                ", id=" + super.getId() +
                ", status='" + super.getStatus() + '\'' +
                ", listSubtaskId='" + listSubtaskId + '\'' +
                '}';
    }
}
