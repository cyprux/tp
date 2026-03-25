package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.YearMonth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains unit tests for ReportCommand.
 */
public class ReportCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        new AddtCommand("Sports Hall",
                LocalDate.of(2026, 12, 1),
                Index.fromOneBased(1)).execute(model);
        new AddtCommand("Swimming Pool",
                LocalDate.of(2026, 12, 5),
                Index.fromOneBased(2)).execute(model);
    }

    @Test
    public void execute_noCompletedTasks_showsNoTasksMessage() {
        ReportCommand command = new ReportCommand(YearMonth.of(2026, 12));
        CommandResult result = command.execute(model);
        assertEquals(String.format(ReportCommand.MESSAGE_NO_TASKS,
                YearMonth.of(2026, 12)), result.getFeedbackToUser());
    }

    @Test
    public void execute_withCompletedTasks_showsReport() throws Exception {
        new DonetCommand(Index.fromOneBased(1)).execute(model);
        ReportCommand command = new ReportCommand(YearMonth.of(2026, 12));
        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("Sports Hall"));
    }

    @Test
    public void execute_wrongMonth_showsNoTasksMessage() throws Exception {
        new DonetCommand(Index.fromOneBased(1)).execute(model);
        ReportCommand command = new ReportCommand(YearMonth.of(2025, 1));
        CommandResult result = command.execute(model);
        assertEquals(String.format(ReportCommand.MESSAGE_NO_TASKS,
                YearMonth.of(2025, 1)), result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleCompletedTasks_showsAllInReport() throws Exception {
        new DonetCommand(Index.fromOneBased(1)).execute(model);
        new DonetCommand(Index.fromOneBased(2)).execute(model);
        ReportCommand command = new ReportCommand(YearMonth.of(2026, 12));
        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("Sports Hall"));
        assertTrue(result.getFeedbackToUser().contains("Swimming Pool"));
    }

    @Test
    public void equals_sameYearMonth_returnsTrue() {
        ReportCommand command1 = new ReportCommand(YearMonth.of(2026, 12));
        ReportCommand command2 = new ReportCommand(YearMonth.of(2026, 12));
        assertEquals(command1, command2);
    }
}
