package com.taskflow.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.taskflow.model.Task;

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

    private final Path filePath;
    private final Gson gson;

    public JsonTaskRepository(String filePath) {
        this.filePath = Path.of(filePath);
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    @Override
    public void saveAll(List<Task> tasks) {
        try {
            String json = gson.toJson(tasks.toArray(new Task[0]));
            Files.writeString(filePath, json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save tasks: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> loadAll() {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(filePath);
            if (json.trim().isEmpty()) {
                return new ArrayList<>();
            }

            Task[] loaded = gson.fromJson(json, Task[].class);
            return new ArrayList<>(Arrays.asList(loaded));
        } catch (IOException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Warning: Failed to parse tasks file, starting fresh.");
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
