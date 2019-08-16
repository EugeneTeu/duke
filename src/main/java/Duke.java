import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        /*String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);*/
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");

        //intialize an arraylist to store strings
        List<Task> store = new ArrayList<>();
        Scanner myScanner = new Scanner(System.in);
        String argument = myScanner.nextLine();
        //when user input is not bye
        while (!argument.equals("bye") && !argument.equals("Bye")) {
            if (argument.equals("list")) {
                int num = 1;
                System.out.println("Here are the tasks in your list:");
                for (Task i : store) {
                    System.out.println(+ num + ". " + i);
                    num++;
                }
                argument = myScanner.nextLine();
            } else {
                String[] argumentArray = argument.split(" ");
                if (argumentArray[0].equals("done")) {
                    int index = Integer.valueOf(argumentArray[1]) - 1;
                    Task taskToModify = store.get(index);
                    taskToModify.markAsDone();
                    System.out.println("Nice! I've marked this task as done: ");
                    System.out.println(taskToModify);
                    argument = myScanner.nextLine();
                } else if (argumentArray[0].equals("todo")) {
                    //form back string
                    String toDoTaskString = "";
                    for (int i = 1; i < argumentArray.length; i++) {
                        toDoTaskString += argumentArray[i];
                        toDoTaskString += " ";

                    }
                    //.trim() to remove trailing space
                    Task toDoTask = new ToDo(toDoTaskString.trim());
                    store.add(toDoTask);
                    printGotIt();
                    System.out.println(" " + toDoTask.toString());
                    printNow(store.size());
                    argument = myScanner.nextLine();
                } else if (argumentArray[0].equals("deadline")) {
                    //form back string , description stops at /by
                    //date time starts from /by
                    String deadlineTaskDescriptionString = "";
                    String deadlineTaskDateAndTimeString = "";
                    boolean createDesc = true;
                    for (int i = 1; i < argumentArray.length; i++) {
                        if (argumentArray[i].equals("/by")) {
                            createDesc = false;
                        } else if (createDesc) {
                            deadlineTaskDescriptionString += argumentArray[i];
                            deadlineTaskDescriptionString += " ";
                        } else {
                            deadlineTaskDateAndTimeString += argumentArray[i];
                            deadlineTaskDateAndTimeString += " ";

                        }
                    }

                    //use .trim() method to eliminate trailing white space
                    Task deadlineTask = new Deadline(deadlineTaskDescriptionString.trim(), deadlineTaskDateAndTimeString.trim());
                    store.add(deadlineTask);
                    printGotIt();
                    System.out.println(" " + deadlineTask.toString());
                    printNow(store.size());
                    argument = myScanner.nextLine();
                } else if (argumentArray[0].equals("event")) {
                    //form back string , description stops at /at
                    //date time starts from /at
                    String eventTaskDescriptionString = "";
                    String eventTaskDateAndTimeString = "";
                    boolean createDesc = true;
                    for (int i = 1; i < argumentArray.length; i++) {
                        if (argumentArray[i].equals("/at")) {
                            createDesc = false;
                        } else if (createDesc) {
                            eventTaskDescriptionString += argumentArray[i];
                            eventTaskDescriptionString += " ";
                        } else {
                            eventTaskDateAndTimeString += argumentArray[i];
                            eventTaskDateAndTimeString += " ";

                        }
                    }
                    //use of .trim() to avoid trailing whitespace
                    Task eventTask = new Event(eventTaskDescriptionString.trim(), eventTaskDateAndTimeString.trim());
                    store.add(eventTask);
                    printGotIt();
                    System.out.println(" " + eventTask.toString());
                    printNow(store.size());
                    argument = myScanner.nextLine();
                }

                else {
                    Task incomingTask = new Task(argument);
                    store.add(incomingTask);
                    System.out.println("added: " + argument);
                    argument = myScanner.nextLine();
                }
            }
        }

        //exiting program
        System.out.println("Bye. Hope to see you again soon!");
        myScanner.close();
    }

    //print common methods to call
    static void printGotIt() {
        System.out.println("Got it. I've added this task:");
    }

    static void printNow(int length) {
        System.out.println("Now you have " + length + " tasks in the list.");
    }


}
