package sallybot.storage;

import sallybot.exception.SallyException;
import sallybot.parser.DateTimeUtil;
import sallybot.task.Deadline;
import sallybot.task.Event;
import sallybot.task.Task;
import sallybot.task.ToDo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Storage is responsible for loading and saving the tasks to a file. <br>
 * It uses a simple text-based format to store the tasks, which is human-readable and easy to parse. <br>
 * The format is as follows: <br>
 * T | 1 | desc <br>
 * D | 0 | desc | by <br>
 * E | 0 | desc | from | to <br>
 * where the first part is the type of task (T for ToDo, D for Deadline, E for Event),
 * the second part is the status of the task (1 for done, 0 for not done),
 * the third part is the description of the task,
 * and the remaining parts are the time-related information (if applicable). <br>
 * The Storage class also ensures that the parent directory of the file exists before attempting to read or write,
 * and it handles any IO exceptions that may occur during these operations
 * by throwing a SallyException with an appropriate message.
 */
public class Storage {
    private final Path filePath;

    public Storage(String relativePathFromProjectRoot) {
        this.filePath = Paths.get(relativePathFromProjectRoot);
    }

    public ArrayList<Task> load() throws SallyException {
        ensureParentDirExists();

        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new SallyException("\t Unable to read saved tasks.");
        }

        ArrayList<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            Task parsed = tryParseLine(line);
            if (parsed != null) {
                tasks.add(parsed);
            }
            // Corrupted/unknown lines are skipped (stretch goal)
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to the file.
     *
     * @param tasks the list of tasks to save, which will be encoded into the specified text format
     *              and written to the file.
     * @throws SallyException if there is an error while writing to the file, such as an IOException,
     *                        or if there is an error while ensuring the parent directory exists.
     */
    public void save(List<Task> tasks) throws SallyException {
        ensureParentDirExists();

        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(encode(t));
        }

        try {
            Files.write(filePath, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new SallyException("\t Unable to save tasks.");
        }
    }

    /**
     * Ensures that the parent directory of the file exists.
     * If it does not exist, it attempts to create it.
     *
     * @throws SallyException if there is an error while creating the parent directory.
     */
    private void ensureParentDirExists() throws SallyException {
        Path parent = filePath.getParent();
        if (parent == null) {
            return;
        }
        try {
            Files.createDirectories(parent);
        } catch (IOException e) {
            throw new SallyException("\t Unable to create data folder.");
        }
    }

    /**
     * Tries to parse a line from the file into a Task object.
     * If the line is corrupted or does not match the expected format, it returns null.
     *
     * @param line the line to parse, which is expected to be in the format of
     *             "type | done | description | [time info]"
     * @return a Task object if parsing is successful, or null if the line is corrupted/invalid.
     */
    private Task tryParseLine(String line) {
        if (line == null) {
            return null;
        }

        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        String[] parts = trimmed.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].trim();
        String doneStr = parts[1].trim();
        String desc = parts[2].trim();

        boolean isDone;
        if ("1".equals(doneStr)) {
            isDone = true;
        } else if ("0".equals(doneStr)) {
            isDone = false;
        } else {
            return null;
        }

        try {
            switch (type) {
            case "T": {
                Task t = new ToDo(desc);
                if (isDone) {
                    t.markAsDone();
                }
                return t;
            }
            case "D": {
                if (parts.length < 4) {
                    return null;
                }
                // Expect ISO-8601 datetime in storage file, e.g. 2019-12-02T18:00
                Task t = new Deadline(desc, DateTimeUtil.parseStorageDateTime(parts[3].trim()));
                if (isDone) {
                    t.markAsDone();
                }
                return t;
            }
            case "E": {
                if (parts.length < 5) {
                    return null;
                }
                Task t = new Event(desc,
                        DateTimeUtil.parseStorageDateTime(parts[3].trim()),
                        DateTimeUtil.parseStorageDateTime(parts[4].trim()));
                if (isDone) {
                    t.markAsDone();
                }
                return t;
            }
            default:
                return null;
            }
        } catch (DateTimeParseException ignored) {
            // Corrupted date/time in storage -> skip line (stretch goal)
            return null;
        } catch (Exception ignored) {
            // Any unexpected constructor/parsing issue = treat as corrupted line and skip
            return null;
        }
    }

    /**
     * Encodes a Task object into a string format suitable for saving to the file.
     *
     * @param task the Task object to encode, which can be an instance of ToDo, Deadline or Event. The method will
     *             determine the type of task and format the string accordingly, including the status and
     *             time-related information if applicable.
     * @return a string representation of the task in the format of "type | done | description | [time info]", where
     * "type" is "T" for ToDo, "D" for Deadline, and "E" for Event, "done" is "1" if the task is done
     * and "0" if it is not done, "description" is the description of the task,
     * and "time info" includes the deadline for Deadline tasks or the start and end times for Event tasks.
     */
    private String encode(Task task) {
        String done = task.getIsDone() ? "1" : "0";

        if (task instanceof ToDo) {
            return "T | " + done + " | " + task.getDescription();
        }
        if (task instanceof Deadline d) {
            return "D | " + done + " | " + d.getDescription() + " | " + d.getBy();
        }
        if (task instanceof Event e) {
            return "E | " + done + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        }

        // Fallback (should not happen)
        return "T | " + done + " | " + task.getDescription();
    }
}
