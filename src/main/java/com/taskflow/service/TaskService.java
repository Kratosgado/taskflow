package com.taskflow.service;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.repository.TaskRepository;

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

    private final List<Task> tasks;
    private final TaskRepository repository;
    private int nextId;

    public TaskService() {
        this.tasks = new ArrayList<>();
        this.repository = null;
        this.nextId = 1;
    }

    public TaskService(TaskRepository repository) {
        this.repository = repository;
        this.tasks = new ArrayList<>(repository.loadAll());
        this.nextId = tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Creates a new task with the given title and description.
     */
    public Task addTask(String title, String description) {
        Task task = new Task(nextId++, title, description);
        tasks.add(task);
        persist();
        return task;
    }

    /**
     * Returns an unmodifiable list of all tasks.
     */
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Finds a task by its ID.
     */
    public Optional<Task> getTaskById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    /**
     * Marks a task as complete (DONE).
     */
    public Task completeTask(int id) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new IllegalStateException("Task is already completed: " + task.getTitle());
        }

        task.setStatus(TaskStatus.DONE);
        persist();
        return task;
    }

    /**
     * Filters tasks by their status.
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a task by its ID.
     */
    public Task deleteTask(int id) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));

        tasks.remove(task);
        persist();
        return task;
    }

    /**
     * Updates a task's status to IN_PROGRESS.
     */
    public Task startTask(int id) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new IllegalStateException("Cannot move a completed task back to IN_PROGRESS. Task: " + task.getTitle());
        }

        if (task.getStatus() == TaskStatus.IN_PROGRESS) {
            throw new IllegalStateException("Task is already IN_PROGRESS: " + task.getTitle());
        }

        task.setStatus(TaskStatus.IN_PROGRESS);
        persist();
        return task;
    }

    /**
     * Returns the total number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Persists tasks to the repository if available.
     */
    private void persist() {
        if (repository != null) {
            repository.saveAll(tasks);
        }
    }
}
