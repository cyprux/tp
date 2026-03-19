package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.task.MaintenanceTaskList;

/**
 * Represents a storage layer for {@link MaintenanceTaskList}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the task list data file.
     */
    Path getTaskListFilePath();

    /**
     * Reads the task list from the default file path.
     * Returns {@code Optional.empty()} if the file is not found.
     *
     * @throws DataLoadingException if the file exists but could not be loaded.
     */
    Optional<MaintenanceTaskList> readTaskList() throws DataLoadingException;

    /**
     * Reads the task list from the specified file path.
     * Returns {@code Optional.empty()} if the file is not found.
     *
     * @param filePath The path to read from. Must not be null.
     * @throws DataLoadingException if the file exists but could not be loaded.
     */
    Optional<MaintenanceTaskList> readTaskList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@code MaintenanceTaskList} to the default file path.
     *
     * @param taskList The task list to save. Must not be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(MaintenanceTaskList taskList) throws IOException;

    /**
     * Saves the given {@code MaintenanceTaskList} to the specified file path.
     *
     * @param taskList The task list to save. Must not be null.
     * @param filePath The path to write to. Must not be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(MaintenanceTaskList taskList, Path filePath) throws IOException;
}
