package com.taskflow.service;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;

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
    private int nextId;

    public TaskService() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
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
        Task task = new Task(nextId++, title, description);
        tasks.add(task);
        return task;
    }

    /**
     * Returns an unmodifiable list of all tasks.
     *
     * @return list of all tasks
     */
    public List<Task> getAllTasks() {
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
        Task task = getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new IllegalStateException("Task is already completed: " + task.getTitle());
        }

        task.setStatus(TaskStatus.DONE);
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
}
