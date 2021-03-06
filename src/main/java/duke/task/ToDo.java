package duke.task;

public class ToDo extends Task {

    /**
     * ToDo task.
     * @param description user input of title of the task
     */

    public ToDo(String description) {
        super(description);
    }

    public ToDo() {
    }

    @Override
    public String toString() {
        if (this.isDone) {
            return "[T][\u2713] " + this.description; //shows tick
        } else {
            return "[T][\u2718] " + this.description; //shows cross
        }
    }

    @Override
    public String createTaskInFileFormat() {
        String temp = "T ";
        temp += super.createTaskInFileFormat();
        return temp;
    }

    @Override
    public void editDateAndTime(String newDateAndTime) {
    }
}
