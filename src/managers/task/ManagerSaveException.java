package managers.task;

public class ManagerSaveException extends RuntimeException {
    String text;

    public ManagerSaveException(String text) {
        this.text = text;
    }
}
