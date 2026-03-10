package seedu.address.model.task;

public class MaintenanceTask {
    private final String description;

    public MaintenanceTask(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
