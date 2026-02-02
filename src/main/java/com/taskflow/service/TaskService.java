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
     */
    public Task addTask(String title, String description) {
        Task task = new Task(nextId++, title, description);
        tasks.add(task);
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
     *
     * @param id the task ID
     * @return the deleted task
     * @throws IllegalArgumentException if the task is not found
     */
    public Task deleteTask(int id) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));

        tasks.remove(task);
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
        Task task = getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new IllegalStateException("Cannot move a completed task back to IN_PROGRESS. Task: " + task.getTitle());
        }

        if (task.getStatus() == TaskStatus.IN_PROGRESS) {
            throw new IllegalStateException("Task is already IN_PROGRESS: " + task.getTitle());
        }

        task.setStatus(TaskStatus.IN_PROGRESS);
        return task;
    }

    /**
     * Returns the total number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }
}
