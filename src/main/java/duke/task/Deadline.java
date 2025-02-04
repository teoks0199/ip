package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Encapsulates a Deadline task that can be added to the task manager.
 *
 * @author Teo Kai Sheng
 */
public class Deadline extends Task {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

    /**
     * Deadline of the task.
     */
    private LocalDate by;

    /**
     * Constructor to create a Deadline.
     *
     * @param description Description of the deadline.
     * @param by Deadline of the task.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the String representation of the deadline.
     *
     * @return A String representing the deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatter.format(by) + ")";
    }

    /**
     * Returns the String representation of the deadline to be saved in the hard disk.
     *
     * @return A String representing the deadline.
     */
    @Override
    public String taskToString() {
        return "D | " + super.taskToString() + " | " + by;
    }

    /**
     * Compares this to another object to check if they are the same task.
     *
     * @param o Object to compare with.
     * @return True if they are the same task and false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Deadline)) {
            return false;
        }
        Deadline c = (Deadline) o;
        return this.toString().equals(c.toString());
    }

    /**
     * Updates the deadline of the task.
     *
     * @param newDate The new deadline.
     */
    public void updateByDate(LocalDate newDate) {
        by = newDate;
    }
}
