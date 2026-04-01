package seedu.address.logic.commands;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.MaintenanceTask;
import seedu.address.model.task.MaintenanceTaskList;

/**
 * Lists all maintenance tasks in the task list to the user.
 */
public class ListtCommand extends Command {

    public static final String COMMAND_WORD = "listt";
    public static final String MESSAGE_NO_TASKS = "No maintenance tasks found.";
    public static final String MESSAGE_SUCCESS = "Listed all maintenance tasks:";

    @Override
    public CommandResult execute(Model model) {
        assert model != null : "Model should not be null";
        MaintenanceTaskList taskList = model.getMaintenanceTaskList();

        List<MaintenanceTask> tasks = taskList.getTasks();
        List<Person> personList = model.getFilteredPersonList();

        long pendingCount = tasks.stream()
                .filter(task -> !task.isCompleted())
                .count();

        if (pendingCount == 0) {
            return new CommandResult(MESSAGE_NO_TASKS);
        }

        StringBuilder sb = new StringBuilder(MESSAGE_SUCCESS + "\n");
        int displayIndex = 1;

        for (int i = 0; i < tasks.size(); i++) {
            MaintenanceTask task = tasks.get(i);
            if (task.isCompleted()) {
                continue;
            }

            int contractorIdx = task.getContractorIndex() - 1;
            String contractorName;
            String tagsString;
            String service;

            if (contractorIdx < personList.size()) {
                Person contractor = personList.get(contractorIdx);
                contractorName = contractor.getName().fullName;
                service = contractor.getService().toString();
                tagsString = task.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(java.util.stream.Collectors.joining(", "));
            } else {
                contractorName = "Unknown (deleted)";
                service = "Unknown";
                tagsString = "";
            }

            sb.append(displayIndex).append(". ")
                    .append("[PENDING] ")
                    .append(task.getFacility())
                    .append(" on ").append(task.getDate())
                    .append(" (Contractor: ").append(contractorName)
                    .append(" | Service: ").append(service)
                    .append(" | Tags: [").append(tagsString).append("])\n");
            displayIndex++;
        }
        return new CommandResult(sb.toString());
    }
}

