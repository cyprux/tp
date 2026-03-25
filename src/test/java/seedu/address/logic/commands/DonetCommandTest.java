package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains unit tests for DonetCommand.
 */
public class DonetCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // Add a task to work with
        new AddtCommand("Sports Hall",
                LocalDate.of(2026, 12, 1),
                Index.fromOneBased(1)).execute(model);
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        DonetCommand command = new DonetCommand(Index.fromOneBased(1));
        command.execute(model);
        assertTrue(model.getMaintenanceTaskList().getTasks().get(0).isCompleted());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        DonetCommand command = new DonetCommand(Index.fromOneBased(999));
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_alreadyCompleted_throwsCommandException() throws Exception {
        DonetCommand command = new DonetCommand(Index.fromOneBased(1));
        command.execute(model);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_completedTaskShowsInReport() throws Exception {
        new AddtCommand("Swimming Pool",
                LocalDate.of(2026, 12, 5),
                Index.fromOneBased(2)).execute(model);

        // Complete only first task
        new DonetCommand(Index.fromOneBased(1)).execute(model);

        assertTrue(model.getMaintenanceTaskList().getTasks().get(0).isCompleted());
        assertTrue(!model.getMaintenanceTaskList().getTasks().get(1).isCompleted());
    }

    @Test
    public void equals_sameIndex_returnsTrue() {
        DonetCommand command1 = new DonetCommand(Index.fromOneBased(1));
        DonetCommand command2 = new DonetCommand(Index.fromOneBased(1));
        assertEquals(command1, command2);
    }
}
