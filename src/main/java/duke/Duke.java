package duke;

import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;

import java.util.ArrayList;

public class Duke  {

    private Storage storage;
    private Parser parser;
    ArrayList<Task> tempStore = new ArrayList<Task>();

    /**
     * Constructor for Duke instance.
     * @param filepath file path giving the location of the file
     */
    public Duke(String filepath) {
        assert !filepath.equals("") : "file path should not be empty!";
        storage = new Storage(filepath);
        //initialise an arraylist to store Tasks
        TaskList store = new TaskList(storage.createTasksFromFile(), storage);
        this.parser = new Parser(store);
    }


    /**
     * Generate a response to user input.
     * Calls Parser object to read current input from GUI
     * ALL outputs from the program will be passed through here to the GUI
     */
    private String getResponse(String input) {
        assert !input.equals("") :  "string from GUI parser should not be empty ";
        return parser.readUserInput(input);
    }


    /**
     * accessor to getResponse() method.
     * @param input user input
     * @return return response of program according to input
     */
    public String getPublicResponse(String input) {
        return this.getResponse(input);
    }


}
