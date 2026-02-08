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

    // --- US-4: Filter Tasks by Status ---

    @Test
    @DisplayName("US-4: Should filter tasks by TODO status")
    void shouldFilterByTodoStatus() {
        taskService.addTask("Task 1", "");
        taskService.addTask("Task 2", "");
        taskService.addTask("Task 3", "");
        taskService.completeTask(2);

        List<Task> todoTasks = taskService.getTasksByStatus(TaskStatus.TODO);
        assertEquals(2, todoTasks.size());
        assertTrue(todoTasks.stream().allMatch(t -> t.getStatus() == TaskStatus.TODO));
    }

    @Test
    @DisplayName("US-4: Should filter tasks by DONE status")
    void shouldFilterByDoneStatus() {
        taskService.addTask("Task 1", "");
        taskService.addTask("Task 2", "");
        taskService.completeTask(1);

        List<Task> doneTasks = taskService.getTasksByStatus(TaskStatus.DONE);
        assertEquals(1, doneTasks.size());
        assertEquals("Task 1", doneTasks.get(0).getTitle());
    }

    @Test
    @DisplayName("US-4: Should return empty list when no tasks match filter")
    void shouldReturnEmptyListForNoMatches() {
        taskService.addTask("Task 1", "");
        List<Task> inProgressTasks = taskService.getTasksByStatus(TaskStatus.IN_PROGRESS);
        assertTrue(inProgressTasks.isEmpty());
    }

    @Test
    @DisplayName("US-4: Should filter tasks by IN_PROGRESS status")
    void shouldFilterByInProgressStatus() {
        taskService.addTask("Task 1", "");
        taskService.addTask("Task 2", "");
        taskService.startTask(1);

        List<Task> inProgressTasks = taskService.getTasksByStatus(TaskStatus.IN_PROGRESS);
        assertEquals(1, inProgressTasks.size());
        assertEquals("Task 1", inProgressTasks.get(0).getTitle());
    }

    // --- US-5: Delete a Task ---

    @Test
    @DisplayName("US-5: Should delete a task by ID")
    void shouldDeleteTask() {
        taskService.addTask("Task 1", "");
        taskService.addTask("Task 2", "");

        Task deleted = taskService.deleteTask(1);
        assertEquals("Task 1", deleted.getTitle());
        assertEquals(1, taskService.getTaskCount());
    }

    @Test
    @DisplayName("US-5: Should throw exception when deleting non-existent task")
    void shouldThrowWhenDeletingNonExistent() {
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(999));
    }

    @Test
    @DisplayName("US-5: Deleted task should not appear in list")
    void deletedTaskShouldNotAppearInList() {
        taskService.addTask("Task 1", "");
        taskService.addTask("Task 2", "");
        taskService.deleteTask(1);

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals("Task 2", tasks.get(0).getTitle());
    }

    // --- US-6: Update Task Status ---

    @Test
    @DisplayName("US-6: Should set task to IN_PROGRESS")
    void shouldSetTaskToInProgress() {
        taskService.addTask("Task 1", "");
        Task updated = taskService.startTask(1);
        assertEquals(TaskStatus.IN_PROGRESS, updated.getStatus());
    }

    @Test
    @DisplayName("US-6: Should throw when setting completed task to IN_PROGRESS")
    void shouldThrowWhenStartingCompletedTask() {
        taskService.addTask("Task 1", "");
        taskService.completeTask(1);
        assertThrows(IllegalStateException.class, () -> taskService.startTask(1));
    }

    @Test
    @DisplayName("US-6: Should throw when task is already IN_PROGRESS")
    void shouldThrowWhenAlreadyInProgress() {
        taskService.addTask("Task 1", "");
        taskService.startTask(1);
        assertThrows(IllegalStateException.class, () -> taskService.startTask(1));
    }

    @Test
    @DisplayName("US-6: Should throw for non-existent task when starting")
    void shouldThrowForNonExistentTaskOnStart() {
        assertThrows(IllegalArgumentException.class, () -> taskService.startTask(999));
    }

    // --- General ---

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
