import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<Task>();
        String line = "    ______________________________________________";
        System.out.println(line + "\n    Hello, I'm your task manager :)\n    What can I do for you?\n" + line);
        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            System.out.println(line);
            if (command.equals("list")) {
                System.out.println("    Here are the tasks in your list:");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(String.format("    %d.%s",
                            i+1, list.get(i).toString()));
                }
            } else if (command.startsWith("mark ")) {
                try {
                    int toMark = Integer.parseInt(command.substring(5));
                    Task task = list.get(toMark - 1);
                    task.markAsDone();
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println(String.format("      %s", task.toString()));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("    Task does not exist.");
                }
            } else if (command.startsWith("unmark ")) {
                try {
                    int toMark = Integer.parseInt(command.substring(7));
                    Task task = list.get(toMark - 1);
                    task.markAsUndone();
                    System.out.println("    OK, I've marked this task as not done yet:");
                    System.out.println(String.format("      %s", task.toString()));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("    Task does not exist.");
                }
            } else if (command.startsWith("deadline ")) {
                int byIndex = command.indexOf("/by");
                String desc = command.substring(9, byIndex).trim();
                String deadline = command.substring(byIndex + 3).trim();
                Deadline d = new Deadline(desc, deadline);
                list.add(d);
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + d.toString());
                System.out.println("    Number of tasks: " + list.size());
            } else if (command.startsWith("event ")) {
                int fromIndex = command.indexOf("/from");
                int toIndex = command.indexOf("/to");
                String desc = command.substring(6, fromIndex).trim();
                String from = command.substring(fromIndex + 5, toIndex).trim();
                String to = command.substring(toIndex + 3).trim();
                Event e = new Event(desc, from, to);
                list.add(e);
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + e.toString());
                System.out.println("    Number of tasks: " + list.size());
            } else if (command.startsWith("todo ")) {
                String desc = command.substring(5).trim();
                ToDo t = new ToDo(desc);
                list.add(t);
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + t.toString());
                System.out.println("    Number of tasks: " + list.size());
            }
            System.out.println(line);
            command = scanner.nextLine();
        }
        System.out.println(line + "\n    Bye. Hope to see you again soon!\n" + line);
    }
}
