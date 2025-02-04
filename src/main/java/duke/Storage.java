package duke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

/**
 * Used to handle loading and saving of task list.
 *
 * @author Teo Kai Sheng
 */
public class Storage {
    private Path filePath;
    private ArrayList<Task> list;

    /**
     * Constructor to create a Storage object.
     *
     * @param filePath The file path of the saved task list.
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
        list = new ArrayList<>();
    }

    /**
     * Loads a saved task list if it exists.
     *
     * @return An ArrayList that was saved.
     */
    public ArrayList<Task> loadTaskList() {
        try {
            // Create the directory if it doesn't exist
            Path parentDirectory = filePath.getParent();
            if (!Files.exists(parentDirectory)) {
                Files.createDirectories(parentDirectory);
            }

            // Create the file if it doesn't exist
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            // Read the existing content from the file
            BufferedReader reader = Files.newBufferedReader(filePath);
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                Task toAdd = stringToTask(nextLine);
                if (!toAdd.equals(null)) {
                    list.add(toAdd);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Data file corrupted.");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        } finally {
            return list;
        }
    }

    /**
     * Updates the hard drive with the current task list.
     */
    public void updateTaskList() {
        try {
            // Create the directory if it doesn't exist
            Path parentDirectory = filePath.getParent();
            if (!Files.exists(parentDirectory)) {
                Files.createDirectories(parentDirectory);
            }
            // Create the file if it doesn't exist
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            // Write new content to the file
            StringBuilder toWrite = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                toWrite.append(list.get(i).taskToString());
                toWrite.append("\n");
            }
            BufferedWriter writer = Files.newBufferedWriter(filePath);
            writer.write(toWrite.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Converts a string representation of a task to a Task object.
     *
     * @param s String representation of the task.
     * @return The task object.
     * @throws DukeException Throws DukeException if data file is corrupted.
     */
    public Task stringToTask(String s) throws DukeException {
        String[] details = s.split("[|]", 0);
        String type = details[0].strip();
        Task t;
        try {
            if (type.equals("T")) {
                t = new ToDo(details[2].strip());
            } else if (type.equals("D")) {
                t = new Deadline(details[2].strip(), LocalDate.parse(details[3].strip()));
            } else if (type.equals("E")) {
                t = new Event(details[2].strip(),
                        LocalDate.parse(details[3].strip()), LocalDate.parse(details[4].strip()));
            } else {
                throw new DukeException("Data file corrupted.");
            }
            if (details[1].strip().equals("Y")) {
                t.markAsDone();
            }
            return t;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Data file corrupted.");
        }
    }
}
