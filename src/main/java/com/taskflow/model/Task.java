package com.taskflow.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a task in the task management system.
 */
public class Task {

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final int id;
    private String title;
    private String description;
    private TaskStatus status;
    private final LocalDateTime createdAt;

    public Task(int id, String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }
        this.id = id;
        this.title = title.trim();
        this.description = description != null ? description.trim() : "";
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
    }

    public Task(int id, String title, String description, TaskStatus status, LocalDateTime createdAt) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }
        this.id = id;
        this.title = title.trim();
        this.description = description != null ? description.trim() : "";
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }
        this.title = title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : "";
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getFormattedCreatedAt() {
        return createdAt.format(DISPLAY_FORMAT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (Status: %s, Created: %s)", id, title, status, getFormattedCreatedAt());
    }
}
