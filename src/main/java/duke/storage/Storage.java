package duke.storage;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import java.io.IOException;
import java.io.FileWriter;

public class Storage {

    File tempFile;
    ArrayList<String> storingStrings = new ArrayList<>();

    /**
     * Object that deals with saving data to the files and reading from them.
     * @param filePath file to be saved to
     */
    public Storage(String filePath) {
        try {
            this.tempFile = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(tempFile));
            String line = br.readLine();
            while (line != null) {
                storingStrings.add(line);
                line = br.readLine();
            }
            //Stream<String> stream = Files.lines(Paths.get(tempFile.getAbsolutePath()));
            //stream.forEach(storingStrings::add);
        } catch (FileNotFoundException ex) {
            //nested try catch to prevent propagation up the hierarchy
            try {
                createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * creates file if file is not found.
     * @throws IOException thrown when file cannot be created
     */
    private void createNewFile() throws IOException {
        if (!this.tempFile.exists()) {
            this.tempFile.getParentFile().mkdir();
            this.tempFile.createNewFile();
        }
    }


    /**
     * Saves given arrayList of Tasks to file.
     * @param list ArrayList of Task
     */
    public void saveTaskToFile(ArrayList<Task> list) {
        try {
            assert this.tempFile != null : "File cannot be null here";
            FileWriter fileWriter = new FileWriter(this.tempFile);
            for (int i = 0; i < list.size(); i++) {
                Task currentTask = list.get(i);
                fileWriter.write(currentTask.createTaskInFileFormat() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Creates an arrayList of Taks from file.
     * @return ArrayList of Task
     */
    public ArrayList<Task> createTasksFromFile() {
        assert this.storingStrings != null : "array cannot be null here";
        ArrayList<Task> store = new ArrayList<>();
        for (int i = 0; i < this.storingStrings.size(); i++) {
            String wholeLine = this.storingStrings.get(i);
            String[] argumentArray = wholeLine.split(" ");
            boolean isCompleted = false;
            if (argumentArray[1].equals("1"))  {
                isCompleted = true;
            }
            if (argumentArray[0].equals("T")) {
                String toDoTaskString = "";
                for (int j = 2; j < argumentArray.length; j++) {
                    toDoTaskString += argumentArray[j];
                    toDoTaskString += " ";
                }
                //.trim() to remove trailing space
                Task toDoTask = new ToDo(toDoTaskString.trim());
                if (isCompleted) {
                    toDoTask.markAsDone();
                }
                store.add(toDoTask);
            } else if (argumentArray[0].equals("D")) {
                String deadlineTaskDescriptionString = "";
                String deadlineTaskDateAndTimeString = "";
                boolean createDesc = true;
                for (int j = 2; j < argumentArray.length; j++) {
                    if (argumentArray[j].equals("/by")) {
                        createDesc = false;
                    } else if (createDesc) {
                        deadlineTaskDescriptionString += argumentArray[j];
                        deadlineTaskDescriptionString += " ";
                    } else {
                        deadlineTaskDateAndTimeString += argumentArray[j];
                        deadlineTaskDateAndTimeString += " ";
                    }
                }
                //use .trim() method to eliminate trailing white space
                Task deadlineTask = new Deadline(deadlineTaskDescriptionString.trim(),
                        deadlineTaskDateAndTimeString.trim());
                if (isCompleted) {
                    deadlineTask.markAsDone();
                }
                store.add(deadlineTask);
            } else if (argumentArray[0].equals("E")) {
                String eventTaskDescriptionString = "";
                String eventTaskDateAndTimeString = "";
                boolean createDesc = true;
                for (int j = 2; j < argumentArray.length; j++) {
                    if (argumentArray[j].equals("/at")) {
                        createDesc = false;
                    } else if (createDesc) {
                        eventTaskDescriptionString += argumentArray[j];
                        eventTaskDescriptionString += " ";
                    } else {
                        eventTaskDateAndTimeString += argumentArray[j];
                        eventTaskDateAndTimeString += " ";
                    }
                }
                //use of .trim() to avoid trailing whitespace
                Task eventTask = new Event(eventTaskDescriptionString.trim(), eventTaskDateAndTimeString.trim());
                if (isCompleted) {
                    eventTask.markAsDone();
                }
                store.add(eventTask);
            }
        }
        return store;
    }

}
