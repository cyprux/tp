package seedu.address.model.task;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a maintenance task in the task list.
 * Guarantees: facility and date are non-null and validated at construction.
 */
public class MaintenanceTask {

    public static final int MAX_FACILITY_LENGTH = 50;

    private final String facility;
    private final LocalDate date;
    private final int contractorIndex;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructs a {@code MaintenanceTask}.
     *
     * @param facility        The name of the facility (1–50 chars).
     * @param date            The scheduled date (must not be in the past).
     * @param contractorIndex The 1-based index of the assigned contractor.
     * @param tags            The set of tags associated with the task.
     */
    public MaintenanceTask(String facility, LocalDate date, int contractorIndex, Set<Tag> tags) {
        this.facility = facility;
        this.date = date;
        this.contractorIndex = contractorIndex;
        this.tags.addAll(tags);
    }

    /** Returns the facility name. */
    public String getFacility() {
        return facility;
    }

    /** Returns the scheduled date. */
    public LocalDate getDate() {
        return date;
    }

    /** Returns the 1-based contractor index. */
    public int getContractorIndex() {
        return contractorIndex;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public String toString() {
        String tagsString = tags.stream()
                .map(tag -> tag.tagName)
                .collect(java.util.stream.Collectors.joining(", "));

        return facility + " on " + date
                + " (Contractor #" + contractorIndex + " [" + tagsString + "])";
    }
}
