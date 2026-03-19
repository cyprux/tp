package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMaintenanceTasks.SPORTS_HALL;
import static seedu.address.testutil.TypicalMaintenanceTasks.SWIMMING_POOL;
import static seedu.address.testutil.TypicalMaintenanceTasks.getTypicalTaskList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.task.MaintenanceTaskList;

public class JsonTaskListStorageTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonTaskListStorageTest");

    @TempDir
    public Path testFolder;

    // ================ readTaskList tests ==============================

    @Test
    public void readTaskList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTaskList(null));
    }

    @Test
    public void readTaskList_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskList("NonExistentFile.json").isPresent());
    }

    @Test
    public void readTaskList_notJsonFormat_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTaskList("notJsonFormatTaskList.json"));
    }

    @Test
    public void readTaskList_invalidTaskList_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTaskList("invalidTaskList.json"));
    }

    // ================ saveTaskList / read round-trip tests ==============================

    @Test
    public void readAndSaveTaskList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTaskList.json");
        MaintenanceTaskList original = getTypicalTaskList();
        JsonTaskListStorage storage = new JsonTaskListStorage(filePath);

        // Save and read back
        storage.saveTaskList(original, filePath);
        MaintenanceTaskList readBack = storage.readTaskList(filePath).get();
        assertTaskListEquals(original, readBack);

        // Modify, overwrite, and read back
        original.addTask(SPORTS_HALL); // duplicate tolerated at storage layer
        storage.saveTaskList(original, filePath);
        readBack = storage.readTaskList(filePath).get();
        assertTaskListEquals(original, readBack);

        // Save and read without specifying file path
        original.addTask(SWIMMING_POOL);
        storage.saveTaskList(original);
        readBack = storage.readTaskList().get();
        assertTaskListEquals(original, readBack);
    }

    @Test
    public void saveTaskList_nullTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskList(null, "SomeFile.json"));
    }

    @Test
    public void saveTaskList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskList(new MaintenanceTaskList(), null));
    }

    // ================ helpers ==============================

    /**
     * Reads a task list from a file in the test data folder.
     */
    private java.util.Optional<MaintenanceTaskList> readTaskList(String filePath) throws Exception {
        Path fullPath = addToTestDataPathIfNotNull(filePath);
        return new JsonTaskListStorage(fullPath).readTaskList(fullPath);
    }

    /**
     * Saves {@code taskList} at the specified {@code filePath} within the test data folder.
     */
    private void saveTaskList(MaintenanceTaskList taskList, String filePath) {
        try {
            new JsonTaskListStorage(Paths.get(filePath))
                    .saveTaskList(taskList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    private Path addToTestDataPathIfNotNull(String fileName) {
        return fileName != null ? TEST_DATA_FOLDER.resolve(fileName) : null;
    }

    /**
     * Asserts that two {@code MaintenanceTaskList} objects contain equal tasks in the same order.
     */
    private void assertTaskListEquals(MaintenanceTaskList expected, MaintenanceTaskList actual) {
        assertEquals(expected.getTasks(), actual.getTasks());
    }
}
