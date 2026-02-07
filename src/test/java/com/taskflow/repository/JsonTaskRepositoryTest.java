package com.taskflow.repository;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the JsonTaskRepository.
 * Tests file-based persistence operations.
 */
class JsonTaskRepositoryTest {

    private static final String TEST_FILE = "test_tasks.json";
    private JsonTaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JsonTaskRepository(TEST_FILE);
        cleanup();
    }

    @AfterEach
    void tearDown() {
        cleanup();
    }

    private void cleanup() {
        try {
            Files.deleteIfExists(Path.of(TEST_FILE));
        } catch (IOException ignored) {
        }
    }

    @Test
    @DisplayName("US-7: Should save and load tasks")
    void shouldSaveAndLoadTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "Task 1", "Description 1"));
        tasks.add(new Task(2, "Task 2", "Description 2"));

        repository.saveAll(tasks);

        List<Task> loaded = repository.loadAll();
        assertEquals(2, loaded.size());
        assertEquals("Task 1", loaded.get(0).getTitle());
        assertEquals("Task 2", loaded.get(1).getTitle());
    }

    @Test
    @DisplayName("US-7: Should preserve task status after save/load")
    void shouldPreserveTaskStatus() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1, "Task 1", "");
        task.setStatus(TaskStatus.DONE);
        tasks.add(task);

        repository.saveAll(tasks);
        List<Task> loaded = repository.loadAll();

        assertEquals(TaskStatus.DONE, loaded.get(0).getStatus());
    }

    @Test
    @DisplayName("US-7: Should return empty list when file does not exist")
    void shouldReturnEmptyListWhenFileNotExists() {
        List<Task> loaded = repository.loadAll();
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("US-7: Should handle empty file gracefully")
    void shouldHandleEmptyFile() throws IOException {
        Files.writeString(Path.of(TEST_FILE), "");
        List<Task> loaded = repository.loadAll();
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("US-7: Should handle corrupted file gracefully")
    void shouldHandleCorruptedFile() throws IOException {
        Files.writeString(Path.of(TEST_FILE), "this is not valid json{{{");
        List<Task> loaded = repository.loadAll();
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("US-7: Should overwrite existing file on save")
    void shouldOverwriteExistingFile() {
        List<Task> original = new ArrayList<>();
        original.add(new Task(1, "Original", ""));
        repository.saveAll(original);

        List<Task> updated = new ArrayList<>();
        updated.add(new Task(1, "Updated", ""));
        updated.add(new Task(2, "New Task", ""));
        repository.saveAll(updated);

        List<Task> loaded = repository.loadAll();
        assertEquals(2, loaded.size());
        assertEquals("Updated", loaded.get(0).getTitle());
    }

    @Test
    @DisplayName("US-7: Should save empty list as valid JSON")
    void shouldSaveEmptyList() {
        repository.saveAll(new ArrayList<>());
        List<Task> loaded = repository.loadAll();
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("US-7: Should preserve creation timestamp")
    void shouldPreserveCreationTimestamp() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1, "Task", "desc");
        tasks.add(task);

        repository.saveAll(tasks);
        List<Task> loaded = repository.loadAll();

        assertEquals(
                task.getCreatedAt().withNano(0),
                loaded.get(0).getCreatedAt().withNano(0)
        );
    }
}
