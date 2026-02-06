package com.taskflow.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JSON file-based implementation of TaskRepository.
 * Persists tasks to a JSON file using the Gson library.
 */
public class JsonTaskRepository implements TaskRepository {

    private static final Logger logger = LoggerFactory.getLogger(JsonTaskRepository.class);

    private final Path filePath;
    private final Gson gson;

    public JsonTaskRepository(String filePath) {
        this.filePath = Path.of(filePath);
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        logger.info("JsonTaskRepository initialized with file: {}", filePath);
    }

    @Override
    public void saveAll(List<Task> tasks) {
        try {
            String json = gson.toJson(tasks.toArray(new Task[0]));
            Files.writeString(filePath, json);
            logger.info("Saved {} tasks to {}", tasks.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to save tasks to {}: {}", filePath, e.getMessage());
            throw new RuntimeException("Failed to save tasks: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> loadAll() {
        if (!Files.exists(filePath)) {
            logger.info("No existing task file found at {}, starting fresh", filePath);
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(filePath);
            if (json.trim().isEmpty()) {
                logger.info("Task file is empty, starting fresh");
                return new ArrayList<>();
            }

            Task[] loaded = gson.fromJson(json, Task[].class);
            logger.info("Loaded {} tasks from {}", loaded.length, filePath);
            return new ArrayList<>(Arrays.asList(loaded));
        } catch (IOException e) {
            logger.error("Failed to read tasks from {}: {}", filePath, e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            logger.error("Failed to parse tasks file {}: {}", filePath, e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Custom Gson TypeAdapter for LocalDateTime serialization.
     */
    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            out.value(value.format(FORMATTER));
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            return LocalDateTime.parse(in.nextString(), FORMATTER);
        }
    }
}
