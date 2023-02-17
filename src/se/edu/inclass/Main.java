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
        printDeadlines(tasksData);

        System.out.println("Total number of deadlines: " + countDeadlines(tasksData));

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

    public static void printData(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesUsingStream(ArrayList<Task> tasks) {
        tasks.stream()
                .filter(t -> t instanceof Deadline)
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
