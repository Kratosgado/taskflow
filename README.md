# TaskFlow - Minimal Task Management System

A lightweight, command-line task management application built with Java and Maven.

<!--toc:start-->

- [TaskFlow - Minimal Task Management System](#taskflow-minimal-task-management-system)
  - [Product Vision](#product-vision)
  - [Features](#features)
  - [Prerequisites](#prerequisites)
  - [Build & Run](#build-run)
  - [Usage](#usage)
  - [Project Structure](#project-structure)
  <!--toc:end-->

![Test Results](./docs/test_result.png)

## Product Vision

TaskFlow allows individuals to efficiently create, organize, track, and manage their daily tasks from the terminal.

## Features

- Create tasks with title and description
- List all tasks in a formatted table
- Mark tasks as complete
- Filter tasks by status (TODO, IN_PROGRESS, DONE)
- Delete tasks
- Update task status
- Persistent storage (JSON file)

## Prerequisites

- Java 21+
- Maven 3.9+

## Build & Run

```bash
# Build the project
mvn clean package

# Run the application
java -jar target/taskflow-1.0-SNAPSHOT.jar

# Run tests
mvn test
```

## Usage

```
TaskFlow - Task Management System
Commands:
  add <title> [description]  - Create a new task
  list                       - List all tasks
  complete <id>              - Mark a task as complete
  filter <status>            - Filter tasks by status
  progress <id>              - Set task to IN_PROGRESS
  delete <id>                - Delete a task
  help                       - Show this help message
  exit                       - Exit the application
```

## Project Structure

```
src/
├── main/java/com/taskflow/
│   ├── model/          # Domain models (Task, TaskStatus)
│   ├── service/        # Business logic (TaskService)
│   ├── repository/     # Data persistence
│   └── cli/            # Command-line interface
├── test/java/com/taskflow/
│   ├── model/          # Model tests
│   ├── service/        # Service tests
│   └── repository/     # Repository tests
docs/
├── PRODUCT_VISION.md
├── PRODUCT_BACKLOG.md
├── DEFINITION_OF_DONE.md
├── SPRINT_PLANS.md
├── SPRINT1_REVIEW.md
├── SPRINT1_RETROSPECTIVE.md
├── SPRINT2_REVIEW.md
└── SPRINT2_RETROSPECTIVE.md
```
