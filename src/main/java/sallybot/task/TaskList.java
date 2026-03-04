package sallybot.task;

import sallybot.exception.SallyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TaskList is responsible for owning and managing the list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> loaded) {
        this.tasks = new ArrayList<>(loaded == null ? Collections.emptyList() : loaded);
    }

    public List<Task> asUnmodifiableList() {
        return Collections.unmodifiableList(tasks);
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int indexOneBased) {
        validateIndex(indexOneBased);
        return tasks.get(indexOneBased - 1);
    }

    public Task add(Task t) {
        tasks.add(t);
        return t;
    }

    public Task delete(int indexOneBased) {
        validateIndex(indexOneBased);
        return tasks.remove(indexOneBased - 1);
    }

    public Task mark(int indexOneBased) {
        Task t = get(indexOneBased);
        t.markAsDone();
        return t;
    }

    public Task unmark(int indexOneBased) {
        Task t = get(indexOneBased);
        t.markAsNotDone();
        return t;
    }

    private void validateIndex(int indexOneBased) {
        if (tasks.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You have no tasks!");
        }
        if (indexOneBased < 1 || indexOneBased > tasks.size()) {
            throw new SallyException("\t すみません🙇‍♀️ This index is invalid!");
        }
    }

    public List<Task> find(String arg) {
        List<Task> foundTasks = new ArrayList<>();
        for (Task t : tasks) {
            if (t.toString().contains(arg)) {
                foundTasks.add(t);
            }
        }
        if (foundTasks.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ No tasks found with this keyword!");
        }

        return foundTasks;
    }
}
