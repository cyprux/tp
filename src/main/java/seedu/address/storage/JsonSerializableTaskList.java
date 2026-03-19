package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.MaintenanceTaskList;

/**
 * An immutable {@link MaintenanceTaskList} that is serializable to JSON format.
 */
@JsonRootName(value = "tasklist")
class JsonSerializableTaskList {

    private final List<JsonAdaptedMaintenanceTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTaskList} with the given tasks.
     */
    @JsonCreator
    public JsonSerializableTaskList(@JsonProperty("tasks") List<JsonAdaptedMaintenanceTask> tasks) {
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    /**
     * Converts a given {@code MaintenanceTaskList} into this class for Jackson use.
     *
     * @param source The task list to convert. Future changes to it will not affect this object.
     */
    public JsonSerializableTaskList(MaintenanceTaskList source) {
        tasks.addAll(source.getTasks().stream()
                .map(JsonAdaptedMaintenanceTask::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this serializable task list into the model's {@code MaintenanceTaskList}.
     *
     * @throws IllegalValueException if any task contains invalid data.
     */
    public MaintenanceTaskList toModelType() throws IllegalValueException {
        MaintenanceTaskList taskList = new MaintenanceTaskList();
        for (JsonAdaptedMaintenanceTask adapted : tasks) {
            taskList.addTask(adapted.toModelType());
        }
        return taskList;
    }
}
