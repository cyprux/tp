package seedu.address.logic.commands;

import java.util.List;
import seedu.address.model.Model;
import seedu.address.model.task.MaintenanceTask;
import seedu.address.model.task.MaintenanceTaskList;

public class ListtCommand extends Command {

    public static final String COMMAND_WORD = "listt";
    public static final String MESSAGE_NO_TASKS = "No maintenance tasks found.";
    public static final String MESSAGE_SUCCESS = "Listed all maintenance tasks:";

    @Override
    public CommandResult execute(Model model) {
        MaintenanceTaskList taskList = model.getMaintenanceTaskList();
        if (taskList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_TASKS);
        }
        StringBuilder sb = new StringBuilder(MESSAGE_SUCCESS + "\n");
        List<MaintenanceTask> tasks = taskList.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return new CommandResult(sb.toString());
    }
}
