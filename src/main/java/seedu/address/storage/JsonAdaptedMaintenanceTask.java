package seedu.address.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.MaintenanceTask;

/**
 * Jackson-friendly version of {@link MaintenanceTask}.
 */
class JsonAdaptedMaintenanceTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "MaintenanceTask's %s field is missing!";

    private final String facility;
    private final String date;
    private final int contractorIndex;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMaintenanceTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedMaintenanceTask(
            @JsonProperty("facility") String facility,
            @JsonProperty("date") String date,
            @JsonProperty("contractorIndex") int contractorIndex,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.facility = facility;
        this.date = date;
        this.contractorIndex = contractorIndex;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code MaintenanceTask} into this class for Jackson use.
     *
     * @param source The task to convert. Must not be null.
     */
    public JsonAdaptedMaintenanceTask(MaintenanceTask source) {
        this.facility = source.getFacility();
        this.date = source.getDate().toString();
        this.contractorIndex = source.getContractorIndex();
        this.tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted task into the model's {@code MaintenanceTask}.
     *
     * @throws IllegalValueException if any field is missing or invalid.
     */
    public MaintenanceTask toModelType() throws IllegalValueException {
        final String modelFacility = parseFacility();
        final LocalDate modelDate = parseDate();
        final Set<Tag> modelTags = parseTags();
        return new MaintenanceTask(modelFacility, modelDate, contractorIndex, modelTags);
    }

    /**
     * Parses and validates the facility field.
     *
     * @throws IllegalValueException if the facility is null or blank.
     */
    private String parseFacility() throws IllegalValueException {
        if (facility == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "facility"));
        }
        return facility;
    }

    /**
     * Parses and validates the date field.
     *
     * @throws IllegalValueException if the date is null or not a valid ISO date.
     */
    private LocalDate parseDate() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid date format: " + date);
        }
    }

    /**
     * Converts each {@code JsonAdaptedTag} in the list into a model {@code Tag}.
     *
     * @throws IllegalValueException if any tag name is invalid.
     */
    private Set<Tag> parseTags() throws IllegalValueException {
        Set<Tag> modelTags = new HashSet<>();
        for (JsonAdaptedTag tag : tags) {
            modelTags.add(tag.toModelType());
        }
        return modelTags;
    }
}
