package com.taskflow.repository;

import com.taskflow.model.Task;

import java.util.List;

/**
 * Interface for task persistence operations.
 */
public interface TaskRepository {

    /**
     * Saves all tasks to persistent storage.
     *
     * @param tasks the list of tasks to save
     */
    void saveAll(List<Task> tasks);

    /**
     * Loads all tasks from persistent storage.
     *
     * @return list of loaded tasks, or empty list if none exist
     */
    List<Task> loadAll();
}
