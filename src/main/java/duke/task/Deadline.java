package duke.task;


public class Deadline extends Task {
    String dateAndTime;

    /**
     * Deadline task
     * @param description user input of the title of the task
     * @param dateAndTime user input of date and time in the format d/mm/yyyy HHmm
     */
    public Deadline(String description, String dateAndTime) {
        super(description);
        this.dateAndTime = dateAndTime;
    }

    @Override
    public String toString() {
        if (this.isDone) {
            return "[D][\u2713] " + this.description + " (by: " + this.dateAndTime + ")";
        } else {
            return "[D][\u2718] " + this.description + " (by: " + this.dateAndTime + ")";
        }
    }

    @Override
    public String createTaskInFileFormat() {
        String temp = "D ";
        temp += super.createTaskInFileFormat();
        temp += " /by ";
        temp += this.dateAndTime;
        return temp;
    }
}