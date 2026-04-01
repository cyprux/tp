package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.MaintenanceTask;
import seedu.address.model.task.MaintenanceTaskList;

/**
 * Delete maintenance task
 */
public class DeltCommand extends Command {

    public static final String COMMAND_WORD = "delt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the maintenance task identified by the "
            + "index number used in the displayed maintenance tasklist.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Maintenance Task: %1$s";

    private final Index targetIndex;

    public DeltCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        MaintenanceTaskList taskList = model.getMaintenanceTaskList();
        List<MaintenanceTask> lastShownList = taskList.getTasks();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_INDEX);
        }

        MaintenanceTask taskToDelete = lastShownList.get(targetIndex.getZeroBased());
        taskList.removeTask(targetIndex.getZeroBased());

        int contractorIdx = taskToDelete.getContractorIndex() - 1;
        List<Person> personList = model.getFilteredPersonList();

        String contractorName;
        String service;
        String tagsString = taskToDelete.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(java.util.stream.Collectors.joining(", "));

        if (contractorIdx < personList.size()) {
            Person contractor = personList.get(contractorIdx);
            contractorName = contractor.getName().fullName;
            service = contractor.getService().toString();
        } else {
            contractorName = "Unknown (deleted)";
            service = "Unknown";
        }

        String taskDisplay = taskToDelete.getFacility() + " on " + taskToDelete.getDate()
                + " (Contractor: " + contractorName
                + " | Service: " + service
                + " | Tags: [" + tagsString + "])";
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskDisplay));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeltCommand)) {
            return false;
        }

        DeltCommand otherDeltCommand = (DeltCommand) other;
        return targetIndex.equals(otherDeltCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
