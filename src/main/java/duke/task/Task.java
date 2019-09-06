package duke.task;

public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Parent class of Tasks.
     * ToDo, Event , Deadline inherits from this class.
     * contains a description and a boolean flag (to mark done status).
     * @param description title of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task() {
    }

    /**
     * Setter for isDone field.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        if (this.isDone) {
            return "[\u2713] " + this.description; //shows tick
        } else {
            return "[\u2718] " + this.description; //shows cross
        }
    }

    /**
     * Utility method used when specific task is saved to the file.
     * @return created tasks in string
     */
    public String createTaskInFileFormat() {
        String temp = "";
        if (this.isDone) {
            temp += "1 ";
        } else {
            temp += "0 ";
        }
        temp += this.description;
        return temp;
    }
}
