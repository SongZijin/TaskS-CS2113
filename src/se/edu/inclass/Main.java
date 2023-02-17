package se.edu.inclass;

import se.edu.inclass.data.DataManager;
import se.edu.inclass.task.Deadline;
import se.edu.inclass.task.Task;
import se.edu.inclass.task.TaskNameComparator;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

public class Main {

    private TaskNameComparator taskNameComparator;

    public static void main(String[] args) {
        System.out.println("Welcome to Task (stream) manager\n");
        DataManager dm = new DataManager("./data/data.txt");
        ArrayList<Task> tasksData = dm.loadData();

        System.out.println("Printing deadlines before sorting");
//        printData(tasksData);
//        printDataUsingStreams(tasksData);
//        System.out.println();

        System.out.println("Printing deadlines");
        printDeadlines(tasksData);
        System.out.println("Total number of deadlines counted using streams: "
                + countDeadlinesUsingStream(tasksData));

        System.out.println("Printing deadlines after sorting");
        printDeadlinesUsingStream(tasksData);

        ArrayList<Task>  filteredList = filterTaskListUsingStreams(tasksData, "11");
        System.out.println("Filtered list of tasks:");
        printData(filteredList);
    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlinesUsingStream(ArrayList<Task> tasks) {
        int count = (int) tasks.stream()
                .filter(t -> t instanceof Deadline)
                .count(); //return the number of data in the list
                // remember to cast it again, since count will return long
        return count;
    }
    public static void printData(ArrayList<Task> tasksData) {
        System.out.println("Printing data using iteration.");
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDataUsingStreams(ArrayList<Task> tasks){
        System.out.println("Printing data using streams");
        tasks.stream()  //first convert it into a stream
                //to improve performance for large data, you can use .parallelStream
                .forEach(System.out::println);//using sout here will resolve to a C++ way of referring to a method
                // this forEach will print all elements in the list (appling the print method for all)
    }
    public static void printDeadlines(ArrayList<Task> tasksData) {
        System.out.println("Printing Deadlines using iterations");
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }
    
    public static void printDeadlinesUsingStream(ArrayList<Task> tasks) {
        System.out.println("Printing Deadlines using streams");
        tasks.stream()
                .filter(t -> t instanceof Deadline)
                // The filter takes a predicate to filter through the data
                // Predicate: t instanceof Deadline, which is a method
                .sorted( (a, b) -> a.getDescription().compareToIgnoreCase(b.getDescription()))
                // we can add a comparator to specify how the task is compared
                // comparator need 2 descriptors
                .forEach(System.out::println);
    }

    public static ArrayList<Task> filterTaskListUsingStreams (ArrayList<Task> taskData, String filterString) {
        ArrayList<Task> filteredList = (ArrayList<Task>) taskData.stream()
                .filter(t -> t.getDescription().contains(filterString))
                // pass a parameter through the lambda function
                .collect(toList());
                // then collect the result to a new list, which we can cast to arrayList
        return filteredList;
    }

}
