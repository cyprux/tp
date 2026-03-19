package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.task.MaintenanceTaskList;

/**
 * A class to access {@link MaintenanceTaskList} data stored as a JSON file on disk.
 */
public class JsonTaskListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTaskListStorage.class);

    private final Path filePath;

    /**
     * Creates a {@code JsonTaskListStorage} using the given file path.
     *
     * @param filePath The path to the JSON file. Must not be null.
     */
    public JsonTaskListStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTaskListFilePath() {
        return filePath;
    }

    @Override
    public Optional<MaintenanceTaskList> readTaskList() throws DataLoadingException {
        return readTaskList(filePath);
    }

    @Override
    public Optional<MaintenanceTaskList> readTaskList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTaskList> jsonTaskList =
                JsonUtil.readJsonFile(filePath, JsonSerializableTaskList.class);

        if (!jsonTaskList.isPresent()) {
            return Optional.empty();
        }

        return parseTaskList(jsonTaskList.get(), filePath);
    }

    /**
     * Converts a {@code JsonSerializableTaskList} into a {@code MaintenanceTaskList}.
     *
     * @param jsonTaskList The deserialized JSON object.
     * @param filePath     The source file path, used for logging.
     * @throws DataLoadingException if the JSON data contains invalid values.
     */
    private Optional<MaintenanceTaskList> parseTaskList(
            JsonSerializableTaskList jsonTaskList, Path filePath) throws DataLoadingException {
        try {
            return Optional.of(jsonTaskList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTaskList(MaintenanceTaskList taskList) throws IOException {
        saveTaskList(taskList, filePath);
    }

    @Override
    public void saveTaskList(MaintenanceTaskList taskList, Path filePath) throws IOException {
        requireNonNull(taskList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskList(taskList), filePath);
    }
}
