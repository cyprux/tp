package seedu.address.model.task;

import seedu.address.Main;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceTaskList {
    private final ArrayList<MaintenanceTask> tasks = new ArrayList<>();

    public void addTask(MaintenanceTask task) {
        tasks.add(task);
    }

    public void removeTask(int index) {
        tasks.remove(index);
    }

    public List<MaintenanceTask> getTasks() {
        return tasks;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }
}
