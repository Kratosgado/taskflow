package com.taskflow.service;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TaskService.
 */
class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    // --- US-1: Create a New Task ---

    @Test
    @DisplayName("US-1: Should create a task with title and description")
    void shouldCreateTask() {
        Task task = taskService.addTask("Buy groceries", "Milk, eggs");

        assertEquals(1, task.getId());
        assertEquals("Buy groceries", task.getTitle());
        assertEquals("Milk, eggs", task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertNotNull(task.getCreatedAt());
    }

    @Test
    @DisplayName("US-1: Should assign unique IDs to tasks")
    void shouldAssignUniqueIds() {
        Task task1 = taskService.addTask("Task 1", "");
        Task task2 = taskService.addTask("Task 2", "");
        Task task3 = taskService.addTask("Task 3", "");

        assertEquals(1, task1.getId());
        assertEquals(2, task2.getId());
        assertEquals(3, task3.getId());
    }

    @Test
    @DisplayName("US-1: Should reject task with empty title")
    void shouldRejectEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> taskService.addTask("", "desc"));
    }

    @Test
    @DisplayName("US-1: Should reject task with null title")
    void shouldRejectNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> taskService.addTask(null, "desc"));
    }

    @Test
    @DisplayName("US-1: Should create task with null description")
    void shouldCreateTaskWithNullDescription() {
        Task task = taskService.addTask("Task", null);
        assertEquals("", task.getDescription());
    }

    // --- US-2: List All Tasks ---

    @Test
    @DisplayName("US-2: Should return empty list when no tasks")
    void shouldReturnEmptyList() {
        List<Task> tasks = taskService.getAllTasks();
        assertTrue(tasks.isEmpty());
    }

    @Test
    @DisplayName("US-2: Should return all tasks")
    void shouldReturnAllTasks() {
        taskService.addTask("Task 1", "");
        taskService.addTask("Task 2", "");
        taskService.addTask("Task 3", "");

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(3, tasks.size());
    }

    @Test
    @DisplayName("US-2: Should return correct task count")
    void shouldReturnCorrectTaskCount() {
        assertEquals(0, taskService.getTaskCount());
        taskService.addTask("Task 1", "");
        assertEquals(1, taskService.getTaskCount());
        taskService.addTask("Task 2", "");
        assertEquals(2, taskService.getTaskCount());
    }

    @Test
    @DisplayName("US-2: Returned list should be unmodifiable")
    void shouldReturnUnmodifiableList() {
        taskService.addTask("Task 1", "");
        List<Task> tasks = taskService.getAllTasks();
        assertThrows(UnsupportedOperationException.class, () -> tasks.add(new Task(99, "Hack", "")));
    }

    // --- US-3: Mark Task as Complete ---

    @Test
    @DisplayName("US-3: Should mark task as complete")
    void shouldMarkTaskAsComplete() {
        taskService.addTask("Task 1", "");
        Task completed = taskService.completeTask(1);
        assertEquals(TaskStatus.DONE, completed.getStatus());
    }

    @Test
    @DisplayName("US-3: Should mark IN_PROGRESS task as complete")
    void shouldCompleteInProgressTask() {
        Task task = taskService.addTask("Task 1", "");
        task.setStatus(TaskStatus.IN_PROGRESS);

        Task completed = taskService.completeTask(1);
        assertEquals(TaskStatus.DONE, completed.getStatus());
    }

    @Test
    @DisplayName("US-3: Should throw exception for non-existent task")
    void shouldThrowForNonExistentTask() {
        assertThrows(IllegalArgumentException.class, () -> taskService.completeTask(999));
    }

    @Test
    @DisplayName("US-3: Should throw exception when completing already-done task")
    void shouldThrowForAlreadyCompletedTask() {
        taskService.addTask("Task 1", "");
        taskService.completeTask(1);
        assertThrows(IllegalStateException.class, () -> taskService.completeTask(1));
    }

    @Test
    @DisplayName("Should find task by ID")
    void shouldFindTaskById() {
        taskService.addTask("Task 1", "");
        taskService.addTask("Task 2", "");

        Optional<Task> found = taskService.getTaskById(2);
        assertTrue(found.isPresent());
        assertEquals("Task 2", found.get().getTitle());
    }

    @Test
    @DisplayName("Should return empty Optional for non-existent ID")
    void shouldReturnEmptyForNonExistentId() {
        Optional<Task> found = taskService.getTaskById(999);
        assertTrue(found.isEmpty());
    }
}
