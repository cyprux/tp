package seedu.address.model.task;

import java.time.LocalDate;

/**
 * Represents a maintenance task in the task list.
 * Guarantees: facility and date are non-null and validated at construction.
 */
public class MaintenanceTask {

    public static final int MAX_FACILITY_LENGTH = 50;

    private final String facility;
    private final LocalDate date;
    private final int contractorIndex;

    /**
     * Constructs a {@code MaintenanceTask}.
     *
     * @param facility        The name of the facility (1–50 chars).
     * @param date            The scheduled date (must not be in the past).
     * @param contractorIndex The 1-based index of the assigned contractor.
     */
    public MaintenanceTask(String facility, LocalDate date, int contractorIndex) {
        this.facility = facility;
        this.date = date;
        this.contractorIndex = contractorIndex;
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

    @Override
    public String toString() {
        return facility + " on " + date + " (Contractor #" + contractorIndex + ")";
    }
}