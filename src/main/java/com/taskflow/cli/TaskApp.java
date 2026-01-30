package com.taskflow.cli;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.service.TaskService;

import java.util.List;
import java.util.Scanner;

/**
 * Command-line interface for the TaskFlow application.
 * Provides an interactive menu for task management operations.
 */
public class TaskApp {

    private final TaskService taskService;
    private final Scanner scanner;

    public TaskApp() {
        this.taskService = new TaskService();
        this.scanner = new Scanner(System.in);
    }

    public TaskApp(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        TaskApp app = new TaskApp();
        app.run();
    }

    /**
     * Main application loop.
     */
    public void run() {
        printWelcome();

        boolean running = true;
        while (running) {
            System.out.print("\ntaskflow> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+", 3);
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "add" -> handleAdd(parts);
                    case "list" -> handleList();
                    case "complete" -> handleComplete(parts);
                    case "filter" -> handleFilter(parts);
                    case "help" -> printHelp();
                    case "exit", "quit" -> {
                        System.out.println("Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Unknown command: " + command + ". Type 'help' for available commands.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleAdd(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: add <title> [description]");
            return;
        }

        String title = parts[1];
        String description = parts.length > 2 ? parts[2] : "";

        Task task = taskService.addTask(title, description);
        System.out.println("Task created successfully!");
        printTask(task);
    }

    private void handleList() {
        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        printTaskTable(tasks);
        System.out.printf("Total: %d task(s)%n", tasks.size());
    }

    private void handleComplete(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: complete <id>");
            return;
        }

        try {
            int id = Integer.parseInt(parts[1]);
            Task task = taskService.completeTask(id);
            System.out.println("Task marked as complete!");
            printTask(task);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid task ID. Please provide a number.");
        }
    }

    private void handleFilter(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: filter <status> (TODO, IN_PROGRESS, DONE)");
            return;
        }

        try {
            TaskStatus status = TaskStatus.valueOf(parts[1].toUpperCase());
            List<Task> filtered = taskService.getTasksByStatus(status);

            if (filtered.isEmpty()) {
                System.out.println("No tasks found with status: " + status);
                return;
            }

            printTaskTable(filtered);
            System.out.printf("Found: %d task(s) with status %s%n", filtered.size(), status);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid status. Use: TODO, IN_PROGRESS, or DONE");
        }
    }

    private void printTask(Task task) {
        System.out.println("  " + task);
    }

    private void printTaskTable(List<Task> tasks) {
        System.out.println("─".repeat(70));
        System.out.printf("%-5s %-25s %-15s %-20s%n", "ID", "Title", "Status", "Created");
        System.out.println("─".repeat(70));
        for (Task task : tasks) {
            System.out.printf("%-5d %-25s %-15s %-20s%n",
                    task.getId(),
                    truncate(task.getTitle(), 24),
                    task.getStatus(),
                    task.getFormattedCreatedAt());
        }
        System.out.println("─".repeat(70));
    }

    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }

    private void printWelcome() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     TaskFlow - Task Manager          ║");
        System.out.println("║     Type 'help' for commands         ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  add <title> [description]  - Create a new task");
        System.out.println("  list                       - List all tasks");
        System.out.println("  complete <id>              - Mark a task as complete");
        System.out.println("  filter <status>            - Filter tasks by status (TODO, IN_PROGRESS, DONE)");
        System.out.println("  help                       - Show this help message");
        System.out.println("  exit                       - Exit the application");
    }
}
