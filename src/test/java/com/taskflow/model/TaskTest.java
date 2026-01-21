package com.taskflow.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Task model.
 */
class TaskTest {

    @Test
    @DisplayName("Should create a task with valid title and description")
    void shouldCreateTaskWithTitleAndDescription() {
        Task task = new Task(1, "Buy groceries", "Milk, eggs, bread");

        assertEquals(1, task.getId());
        assertEquals("Buy groceries", task.getTitle());
        assertEquals("Milk, eggs, bread", task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertNotNull(task.getCreatedAt());
    }

    @Test
    @DisplayName("Should create a task with default TODO status")
    void shouldCreateTaskWithDefaultTodoStatus() {
        Task task = new Task(1, "Test task", "");
        assertEquals(TaskStatus.TODO, task.getStatus());
    }

    @Test
    @DisplayName("Should trim whitespace from title")
    void shouldTrimTitle() {
        Task task = new Task(1, "  My Task  ", "desc");
        assertEquals("My Task", task.getTitle());
    }

    @Test
    @DisplayName("Should handle null description as empty string")
    void shouldHandleNullDescription() {
        Task task = new Task(1, "Task", null);
        assertEquals("", task.getDescription());
    }

    @Test
    @DisplayName("Should throw exception for null title")
    void shouldThrowExceptionForNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Task(1, null, "desc"));
    }

    @Test
    @DisplayName("Should throw exception for empty title")
    void shouldThrowExceptionForEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Task(1, "", "desc"));
    }

    @Test
    @DisplayName("Should throw exception for blank title")
    void shouldThrowExceptionForBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Task(1, "   ", "desc"));
    }

    @Test
    @DisplayName("Should update status correctly")
    void shouldUpdateStatus() {
        Task task = new Task(1, "Task", "desc");
        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());

        task.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus());
    }

    @Test
    @DisplayName("Tasks with same ID should be equal")
    void shouldBeEqualWithSameId() {
        Task task1 = new Task(1, "Task A", "");
        Task task2 = new Task(1, "Task B", "");
        assertEquals(task1, task2);
    }

    @Test
    @DisplayName("Tasks with different IDs should not be equal")
    void shouldNotBeEqualWithDifferentIds() {
        Task task1 = new Task(1, "Task A", "");
        Task task2 = new Task(2, "Task A", "");
        assertNotEquals(task1, task2);
    }

    @Test
    @DisplayName("toString should contain task details")
    void toStringShouldContainDetails() {
        Task task = new Task(1, "My Task", "Description");
        String result = task.toString();
        assertTrue(result.contains("1"));
        assertTrue(result.contains("My Task"));
        assertTrue(result.contains("TODO"));
    }

    @Test
    @DisplayName("Should create task with explicit status and date")
    void shouldCreateTaskWithExplicitValues() {
        LocalDateTime now = LocalDateTime.of(2026, 1, 15, 10, 30);
        Task task = new Task(5, "Task", "desc", TaskStatus.IN_PROGRESS, now);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        assertEquals(now, task.getCreatedAt());
    }
}
