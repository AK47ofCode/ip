package sallybot.storage;

import sallybot.exception.SallyException;
import sallybot.task.Deadline;
import sallybot.task.Event;
import sallybot.task.Task;
import sallybot.task.ToDo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

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

    private Task tryParseLine(String line) {
        if (line == null) {
            return null;
        }

        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        // Expected formats:
        // T | 1 | desc
        // D | 0 | desc | by
        // E | 0 | desc | from | to
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
                    Task t = new Deadline(desc, parts[3].trim());
                    if (isDone) {
                        t.markAsDone();
                    }
                    return t;
                }
                case "E": {
                    if (parts.length < 5) {
                        return null;
                    }
                    Task t = new Event(desc, parts[3].trim(), parts[4].trim());
                    if (isDone) {
                        t.markAsDone();
                    }
                    return t;
                }
                default:
                    return null;
            }
        } catch (Exception ignored) {
            // Any unexpected constructor/parsing issue = treat as corrupted line and skip
            return null;
        }
    }

    private String encode(Task task) {
        String done = task.getIsDone() ? "1" : "0";

        if (task instanceof ToDo) {
            return "T | " + done + " | " + task.getDescription();
        }
        if (task instanceof Deadline d) {
            return "D | " + done + " | " + d.getDescription() + " | " + d.getBy();
        }
        if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + done + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        }

        // Fallback (should not happen)
        return "T | " + done + " | " + task.getDescription();
    }
}

