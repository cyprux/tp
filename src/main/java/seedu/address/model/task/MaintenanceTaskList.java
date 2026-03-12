package seedu.address.model.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of maintenance tasks.
 */
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

    /**
     * Returns true if a task with the same facility and date already exists in the list.
     *
     * @param facility The facility name to check.
     * @param date     The date to check.
     * @return {@code true} if a duplicate exists, {@code false} otherwise.
     */
    public boolean hasDuplicate(String facility, LocalDate date) {
        return tasks.stream()
                .anyMatch(t -> t.getFacility().equalsIgnoreCase(facility)
                        && t.getDate().equals(date));
    }
}
