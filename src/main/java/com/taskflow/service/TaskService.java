package com.taskflow.service;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class that manages task operations.
 * Provides core business logic for creating, listing, updating, and deleting tasks.
 */
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final List<Task> tasks;
    private final TaskRepository repository;
    private int nextId;

    public TaskService() {
        this.tasks = new ArrayList<>();
        this.repository = null;
        this.nextId = 1;
        logger.info("TaskService initialized (in-memory mode)");
    }

    public TaskService(TaskRepository repository) {
        this.repository = repository;
        this.tasks = new ArrayList<>(repository.loadAll());
        this.nextId = tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
        logger.info("TaskService initialized with repository, loaded {} tasks", tasks.size());
    }

    /**
     * Creates a new task with the given title and description.
     *
     * @param title       the task title (required)
     * @param description the task description (optional)
     * @return the created task
     * @throws IllegalArgumentException if title is null or empty
     */
    public Task addTask(String title, String description) {
        logger.info("Creating task: '{}'", title);
        Task task = new Task(nextId++, title, description);
        tasks.add(task);
        persist();
        logger.info("Task created with ID: {}", task.getId());
        return task;
    }

    /**
     * Returns an unmodifiable list of all tasks.
     *
     * @return list of all tasks
     */
    public List<Task> getAllTasks() {
        logger.debug("Fetching all tasks, count: {}", tasks.size());
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Finds a task by its ID.
     *
     * @param id the task ID
     * @return Optional containing the task if found
     */
    public Optional<Task> getTaskById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    /**
     * Marks a task as complete (DONE).
     *
     * @param id the task ID
     * @return the updated task
     * @throws IllegalArgumentException if the task is not found
     * @throws IllegalStateException    if the task is already completed
     */
    public Task completeTask(int id) {
        logger.info("Marking task {} as complete", id);
        Task task = getTaskById(id)
                .orElseThrow(() -> {
                    logger.error("Task not found with ID: {}", id);
                    return new IllegalArgumentException("Task not found with ID: " + id);
                });

        if (task.getStatus() == TaskStatus.DONE) {
            logger.warn("Task {} is already completed", id);
            throw new IllegalStateException("Task is already completed: " + task.getTitle());
        }

        task.setStatus(TaskStatus.DONE);
        persist();
        logger.info("Task {} marked as DONE", id);
        return task;
    }

    /**
     * Filters tasks by their status.
     *
     * @param status the status to filter by
     * @return list of tasks matching the given status
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        logger.info("Filtering tasks by status: {}", status);
        List<Task> filtered = tasks.stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
        logger.info("Found {} tasks with status {}", filtered.size(), status);
        return filtered;
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the task ID
     * @return the deleted task
     * @throws IllegalArgumentException if the task is not found
     */
    public Task deleteTask(int id) {
        logger.info("Deleting task with ID: {}", id);
        Task task = getTaskById(id)
                .orElseThrow(() -> {
                    logger.error("Cannot delete: task not found with ID: {}", id);
                    return new IllegalArgumentException("Task not found with ID: " + id);
                });

        tasks.remove(task);
        persist();
        logger.info("Task {} deleted: '{}'", id, task.getTitle());
        return task;
    }

    /**
     * Updates a task's status to IN_PROGRESS.
     *
     * @param id the task ID
     * @return the updated task
     * @throws IllegalArgumentException if the task is not found
     * @throws IllegalStateException    if the status transition is invalid
     */
    public Task startTask(int id) {
        logger.info("Setting task {} to IN_PROGRESS", id);
        Task task = getTaskById(id)
                .orElseThrow(() -> {
                    logger.error("Task not found with ID: {}", id);
                    return new IllegalArgumentException("Task not found with ID: " + id);
                });

        if (task.getStatus() == TaskStatus.DONE) {
            logger.warn("Cannot move completed task {} back to IN_PROGRESS", id);
            throw new IllegalStateException("Cannot move a completed task back to IN_PROGRESS. Task: " + task.getTitle());
        }

        if (task.getStatus() == TaskStatus.IN_PROGRESS) {
            logger.warn("Task {} is already IN_PROGRESS", id);
            throw new IllegalStateException("Task is already IN_PROGRESS: " + task.getTitle());
        }

        task.setStatus(TaskStatus.IN_PROGRESS);
        persist();
        logger.info("Task {} set to IN_PROGRESS", id);
        return task;
    }

    /**
     * Returns the total number of tasks.
     *
     * @return task count
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Persists tasks to the repository if available.
     */
    private void persist() {
        if (repository != null) {
            try {
                repository.saveAll(tasks);
                logger.debug("Tasks persisted to repository");
            } catch (Exception e) {
                logger.error("Failed to persist tasks: {}", e.getMessage(), e);
            }
        }
    }
}
