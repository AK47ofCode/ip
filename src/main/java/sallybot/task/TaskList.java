package sallybot.task;

import sallybot.exception.SallyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TaskList is responsible for owning and managing the list of tasks.
 * It provides methods to add, delete, mark, unmark, and find tasks, as well as to retrieve the list of tasks in an unmodifiable form.
 * It also handles index validation and throws appropriate exceptions when invalid operations are attempted.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * The default constructor initializes an empty TaskList with an empty ArrayList of tasks.
     * This constructor is used when there are no existing tasks to load, such as when the application is run for the first time or when loading fails.
     * The TaskList will start with an empty list of tasks, allowing the user to add new tasks from scratch.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * This constructor initializes the TaskList with a given list of tasks.
     * It creates a new ArrayList and copies the provided list into it.
     * If the provided list is null, it initializes the TaskList with an empty list instead.
     * This constructor is used when loading tasks from storage, allowing the TaskList to be initialized with the tasks that were previously saved.
     *
     * @param loaded A list of tasks to initialize the TaskList with.
     */
    public TaskList(List<Task> loaded) {
        this.tasks = new ArrayList<>(loaded == null ? Collections.emptyList() : loaded);
    }

    /**
     * Returns an unmodifiable view of the list of tasks.
     * This method allows external code to access the list of tasks without being able to modify it directly, ensuring encapsulation and data integrity.
     *
     * @return An unmodifiable view of the list of tasks.
     */
    public List<Task> asUnmodifiableList() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Returns the number of tasks in the TaskList.
     * This method provides a way to retrieve the current count of tasks, which can be useful for displaying information to the user
     * or for validating operations that require a certain number of tasks.
     *
     * @return The number of tasks in the TaskList.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the Task at the specified 1-based index in the TaskList.
     * This method takes a 1-based index as input and returns the corresponding Task from the TaskList.
     * It validates the index to ensure it is within the valid range (1 to the number of tasks) and throws a SallyException
     * if the index is invalid or if there are no tasks in the list.
     *
     * @param indexOneBased Takes a 1-based index and returns the corresponding Task from the TaskList.
     * @return The Task at the specified 1-based index in the TaskList.
     */
    public Task get(int indexOneBased) {
        validateIndex(indexOneBased);
        return tasks.get(indexOneBased - 1);
    }

    /**
     * Adds a Task to the TaskList and returns the added Task.
     * This method takes a Task as input, adds it to the TaskList, and returns the same Task.
     * This allows the caller to have a reference to the newly added task, which can be useful for displaying information to the user
     * or for further operations on the task.
     *
     * @param t Takes a Task and adds it to the TaskList.
     * @return The Task that was added to the TaskList.
     */
    public Task add(Task t) {
        tasks.add(t);
        return t;
    }

    /**
     * Deletes the Task at the specified 1-based index from the TaskList and returns the deleted Task.
     * This method takes a 1-based index as input, validates it to ensure it is within the valid range (1 to the number of tasks),
     * and removes the corresponding Task from the TaskList.
     *
     * @param indexOneBased Takes a 1-based index and deletes the corresponding Task from the TaskList.
     * @return The Task that was deleted from the TaskList.
     */
    public Task delete(int indexOneBased) {
        validateIndex(indexOneBased);
        return tasks.remove(indexOneBased - 1);
    }

    /**
     * Marks the Task at the specified 1-based index as done and returns the updated Task.
     * This method takes a 1-based index as input, validates it to ensure it is within the valid range (1 to the number of tasks),
     * and marks the corresponding Task in the TaskList as done.
     * This allows the caller to have a reference to the updated task, which can be useful for displaying information to the user
     * or for further operations on the task.
     *
     * @param indexOneBased Takes a 1-based index and marks the corresponding Task in the TaskList as done.
     * @return The Task that was marked as done in the TaskList.
     */
    public Task mark(int indexOneBased) {
        Task t = get(indexOneBased);
        t.markAsDone();
        return t;
    }

    /**
     * Marks the Task at the specified 1-based index as not done and returns the updated Task.
     * This method takes a 1-based index as input, validates it to ensure it is within the valid range (1 to the number of tasks),
     * and marks the corresponding Task in the Task List as not done.
     * This allows the caller to have a reference to the updated task, which can be useful for displaying information to the user.
     *
     * @param indexOneBased Takes a 1-based index and marks the corresponding Task in the Task List as not done.
     * @return The Task that was marked as not done in the TaskList.
     */
    public Task unmark(int indexOneBased) {
        Task t = get(indexOneBased);
        t.markAsNotDone();
        return t;
    }

    /**
     * Finds and returns a list of tasks that contain the given keyword in their string representation.
     * This method takes a keyword as input and iterates through the list of tasks, checking if each task's string representation contains the keyword.
     * If a task contains the keyword, it is added to the list of found tasks.
     * If no tasks are found after iterating through the entire list, a SallyException is thrown with an appropriate message.
     *
     * @param arg Takes a keyword as input and searches for tasks that contain this keyword in their string representation.
     * @return A list of tasks that contain the given keyword in their string representation.
     */
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

    /**
     * Validates the given 1-based index to ensure it is within the valid range for the TaskList.
     * This method checks if the TaskList is empty and throws a SallyException if it is.
     * It also checks if the index is less than 1 or greater than the number of tasks in the TaskList and throws a SallyException if the index is invalid.
     * This validation ensures that operations that require a valid index (such as getting, deleting, marking, or unmarking a task) are performed safely
     * and that appropriate error messages are provided to the user when invalid indices are used.
     *
     * @param indexOneBased Takes a 1-based index and validates it to ensure it is within the valid range for the TaskList.
     */
    private void validateIndex(int indexOneBased) {
        if (tasks.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You have no tasks!");
        }
        if (indexOneBased < 1 || indexOneBased > tasks.size()) {
            throw new SallyException("\t すみません🙇‍♀️ This index is invalid!");
        }
    }
}
