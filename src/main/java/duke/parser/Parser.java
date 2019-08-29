package duke.parser;

import duke.exception.DukeException;
import duke.tasklist.TaskList;

import java.util.Scanner;

public class Parser  {
    TaskList storeTaskList;


    /**
     * Parser class responsible for parsing user input.
     * This includes asking for user input, decoding user input accordingly.
     * Only responsible for making sense of the users command e.g String manipulation
     * @param list TaskList object at any instance, usually the TaskList read from the hard drive
     */
    public Parser(TaskList list) {
        this.storeTaskList = list;

    }

    /**
     * main driver method for Parser class
     * calls and links to other objects to execute the entire program
     */

    public void readUserInput() {
        Scanner myScanner = new Scanner(System.in);
        
        String argument = myScanner.nextLine();
        while (!argument.equals("bye") && !argument.equals("Bye")) {
            if (argument.equals("list")) {
                storeTaskList.listTask();
                argument = myScanner.nextLine();
            } else {
                String[] argumentArray = argument.split(" ");
                //start of toDo,Event,Deadline
                if (argumentArray[0].equals("done")) {
                    try {
                        if (argumentArray.length == 1) {
                            throw new DukeException("☹ OOPS!!! The description of a done command cannot be empty.");
                        }
                        int index = Integer.valueOf(argumentArray[1]) - 1;
                        storeTaskList.doneTask(index);
                    } catch(IndexOutOfBoundsException e) {
                        System.out.println("invalid number not in current list");
                    }catch (DukeException e) {
                        System.out.println(e);
                    } finally {
                        argument = myScanner.nextLine();
                    }
                } else if (argumentArray[0].equals("todo")) {
                    try {
                        //catch empty desc error
                        if (argumentArray.length == 1) {
                            throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
                        }
                        //form back string
                        String toDoTaskString = "";
                        for (int i = 1; i < argumentArray.length; i++) {
                            toDoTaskString += argumentArray[i];
                            toDoTaskString += " ";

                        }
                        //.trim() to remove trailing space

                        storeTaskList.addToDoTask(toDoTaskString.trim());

                    } catch (DukeException e) {
                        System.out.println(e);
                    } finally {
                        argument = myScanner.nextLine();
                    }
                } else if (argumentArray[0].equals("deadline")) {
                    try {
                        //catch empty desc error
                        if (argumentArray.length == 1) {
                            throw new DukeException("☹ OOPS!!! The description of a deadline cannot be empty.");
                        }


                        //form back string , description stops at /by
                        //date time starts from /by
                        String deadlineTaskDescriptionString = "";
                        String deadlineTaskDateAndTimeString = "";
                        boolean createDesc = true;
                        //catch error of no specific date time after /by
                        if (argumentArray[argumentArray.length -1].matches("/by")) {
                            throw new DukeException("Oops, no specific date/time supplied");
                        }

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
                        //System.out.println(deadlineTaskDateAndTimeString);
                        deadlineTaskDateAndTimeString = convertStringToDate(deadlineTaskDateAndTimeString);
                        storeTaskList.addDeadlineTask(deadlineTaskDescriptionString.trim(), deadlineTaskDateAndTimeString.trim());
                    } catch (DukeException e) {
                        System.out.println(e);
                    } finally {
                        argument = myScanner.nextLine();
                    }
                } else if (argumentArray[0].equals("event")) {
                    try {
                        //catch empty desc error
                        if (argumentArray.length == 1) {
                            throw new DukeException("☹ OOPS!!! The description of a event cannot be empty.");
                        }
                        //form back string , description stops at /at
                        //date time starts from /at
                        String eventTaskDescriptionString = "";
                        String eventTaskDateAndTimeString = "";
                        boolean createDesc = true;
                        //catch error of no specific date time after /at
                        if (argumentArray[argumentArray.length -1].matches("/at")) {
                            throw new DukeException("Oops, no specific duration supplied");
                        }

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

                        eventTaskDateAndTimeString = convertStringToDateEvent(eventTaskDateAndTimeString);
                        storeTaskList.addEventTask(eventTaskDescriptionString.trim(), eventTaskDateAndTimeString.trim());
                        //argument = myScanner.nextLine();
                    } catch (DukeException e) {
                        System.out.println(e);
                    } finally {
                        argument = myScanner.nextLine();
                    }
                }else if (argumentArray[0].equals("delete")) {
                    try{
                        if (argumentArray.length == 1) {
                            throw new DukeException("☹ OOPS!!! The description for delete command cannot be empty.");
                        }
                        int index = Integer.valueOf(argumentArray[1]) - 1;
                        storeTaskList.deleteTask(index);

                        //catch task number not in list error
                    }catch(IndexOutOfBoundsException e) {
                        System.out.println("invalid number not in current list");

                    }catch (DukeException e) {
                        System.out.println(e);
                    } finally {
                        argument = myScanner.nextLine();
                    }


                } else if (argumentArray[0].equals("find")) {
                    storeTaskList.findTask(argumentArray[1]);
                    argument = myScanner.nextLine();
                } else {
                    try {
                        //handles error for not recognized command
                        throw new DukeException("☹  OOPS!!! I'm sorry, but I don't know what that means :-(");
                    } catch (DukeException e) {
                        System.out.println(e);
                    } finally {
                        argument = myScanner.nextLine();
                    }
                }
            }
        }
        //test
        //exiting program
        System.out.println("Bye. Hope to see you again soon!");
        myScanner.close();
    }

    /**
     * String manipulation method to return a formatted
     * from 2/12/2019 1800 to 2nd of December 2019 6pm etc
     * @param dateAndTimeString given string in the format of d/mm/yyyy HHmm
     * @return formatted date and time
     */
    private static String convertStringToDate(String dateAndTimeString) {
        try {
            String[] arrayOfDateAndTime = dateAndTimeString.split(" ");
            String date = arrayOfDateAndTime[0];
            String time = arrayOfDateAndTime[1];
            // d/mm/yyyy

            date = formatString(date);
            Integer timeInInt = Integer.valueOf(time);
            time = convertTime(timeInInt);
            dateAndTimeString = date + ", " + time;
            return dateAndTimeString;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("invalid date and time supplied: try d/mm/yyyy 0000 format");
        } catch (DukeException e ) {
            System.out.println(e);
        }

        return " ";
    }

    /**
     * converts time to string
     * @param convertedTime given input e.g 1630
     * @return formatted string e.g 1630 becomes 430pm
     * @throws DukeException exception when the input is not four digits / out of bounds e.g 2500
     */
    private static String convertTime(Integer convertedTime) throws  DukeException{
        String time = "";
        if (convertedTime == 0) {
            //midnight
            convertedTime = 12;
        } else if ( convertedTime > 0 &&  convertedTime < 1200) {
            int mins = convertedTime % 100;
            if (mins == 0 ) {
                convertedTime = convertedTime % 100;
            }
            time = convertedTime.toString() + "am";
        } else if (convertedTime < 2359 && convertedTime >= 1300) {
            //1300 / 100 = 13 % 12
            int hrs = (convertedTime / 100) % 12;
            int mins = convertedTime % 100;
            if (mins == 0) {
                time = hrs + "pm";
            } else {
                time = String.valueOf(hrs) + mins + "pm";
            }

        } else if (convertedTime < 1300 && convertedTime >= 1200 ) {
            int mins = convertedTime % 100;
            if (mins == 0) {
                time = "12pm";
            } else {
                time = "12" + mins + "pm";
            }

            time = "12" + mins + "pm";
        } else {
            throw new DukeException("invalid time entered");
        }

        return time;
    }

    /**
     *
     * helper function to convert d/mm/yyyy to the correct format
     * @param date inputdate etc 2/12/2019
     * @return formatted date
     */

    private static String formatString(String date) {
        //[ 2 , 12, 2019]
        String[] newArr = date.split("/");

        String result = "";
        String num = newArr[0];
        num = getOrdinal(num);
        String month = newArr[1];
        month = getMonth(month);
        return num + " of " + month + " " + newArr[2];
    }

    /**
     * method to return the month in full spelling
     * @param month given month number as input
     * @return name of the month
     */

    private static String getMonth(String month) {
        String[] arrMonths = {" bye", "January", "February", "March", "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"};
        int temp = Integer.valueOf(month);

        return arrMonths[temp];

    }

    /**
     * method to find the ordinal of each number e.g 1st, 2nd, 3rd
     * @param num input num
     * @return formatted number e.g 1 becomes 1st, 21 becomes 21st
     */
    private static String getOrdinal(String num) {
        int temp = Integer.valueOf(num);
        if (temp == 1 || temp == 21 || temp == 31) {
            return temp + "st";
        } else if (temp == 2 || temp == 22 ) {
            return temp + "nd";
        } else if (temp == 3 || temp == 23) {
            return temp + "rd";
        }else {
            return temp + "th";
        }
    }

    private static String convertStringToDateEvent(String dateAndTimeString) {
       try { // format of input 2/12/2019 1400-1500
           String[] arrayOfDateAndTime = dateAndTimeString.split(" ");
           String date = arrayOfDateAndTime[0];

           String temp = arrayOfDateAndTime[1];
           String[] arrayOfTime = temp.split("-");
           String timeStarting = arrayOfTime[0];
           String timeEnding = arrayOfTime[1];
           date = formatString(date);
           timeStarting = convertTime(Integer.valueOf(timeStarting));
           timeEnding = convertTime(Integer.valueOf(timeEnding));

           dateAndTimeString = date + ", " + timeStarting + "-" +timeEnding;
           return dateAndTimeString;
       } catch (IndexOutOfBoundsException e) {
           System.out.println("invalid date and time supplied: try d/mm/yyyy 0000 format");
       } catch (DukeException e ) {
           System.out.println(e);
       }

        return " ";
    }

    /**
     * method for testing private static methods
     * @param test dummy String
     * @return
     */
    public static String accessConvertStringToDate(String test) {
        return convertStringToDate(test);
    }

    /**
     * method for testing private static method
     * @param convertedTime dummy int
     * @return
     * @throws DukeException
     */
    public static String accessConvertTime(int convertedTime) throws DukeException {
        return convertTime(convertedTime);
    }
}