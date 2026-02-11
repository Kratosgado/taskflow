# Sprint Plans

<!--toc:start-->

- [Sprint Plans](#sprint-plans)
  - [Sprint 0: Planning (Dec 31, 2025 - Jan 13, 2026)](#sprint-0-planning-dec-31-2025-jan-13-2026)
    - [Sprint Goal](#sprint-goal)
    - [Deliverables](#deliverables)
  - [Sprint 1: First Increment (Jan 14 - Jan 27, 2026)](#sprint-1-first-increment-jan-14-jan-27-2026)
    - [Sprint Goal](#sprint-goal-1)
    - [Selected Stories (7 story points)](#selected-stories-7-story-points)
    - [Sprint Backlog Tasks](#sprint-backlog-tasks)
  - [Sprint 2: Second Increment (Jan 28 - Feb 10, 2026)](#sprint-2-second-increment-jan-28-feb-10-2026) - [Sprint Goal](#sprint-goal-2) - [Selected Stories (12 story points)](#selected-stories-12-story-points) - [Sprint Backlog Tasks](#sprint-backlog-tasks-1)
  <!--toc:end-->

## Sprint 0: Planning (Dec 31, 2025 - Jan 13, 2026)

### Sprint Goal

Establish the project foundation, define the product vision, create and refine the product backlog, and set up the development environment.

### Deliverables

- Product Vision document
- Product Backlog with 7 user stories, acceptance criteria, and story point estimates
- Definition of Done
- Sprint Plans for Sprint 1 and Sprint 2
- Maven project structure initialized
- Git repository initialized

---

## Sprint 1: First Increment (Jan 14 - Jan 27, 2026)

### Sprint Goal

Deliver the core task management functionality (create, list, complete tasks) and establish the CI/CD pipeline.

### Selected Stories (7 story points)

| Story                       | Points | Priority |
| --------------------------- | ------ | -------- |
| US-1: Create a New Task     | 3      | High     |
| US-2: List All Tasks        | 2      | High     |
| US-3: Mark Task as Complete | 2      | High     |

### Sprint Backlog Tasks

1. **US-1: Create a New Task**
   - Create Task model class with fields (id, title, description, status, createdAt)
   - Create TaskStatus enum (TODO, IN_PROGRESS, DONE)
   - Implement TaskService with addTask() method
   - Implement input validation (title required)
   - Write unit tests for Task creation
   - Integrate with CLI (TaskApp main class)

2. **US-2: List All Tasks**
   - Implement TaskService.getAllTasks() method
   - Create formatted table display in CLI
   - Handle empty task list scenario
   - Write unit tests for listing

3. **US-3: Mark Task as Complete**
   - Implement TaskService.completeTask(id) method
   - Handle non-existent task error
   - Handle already-completed task warning
   - Write unit tests for completion

4. **DevOps Setup**
   - Create GitHub Actions CI pipeline (build + test)
   - Verify pipeline runs on push

---

## Sprint 2: Second Increment (Jan 28 - Feb 10, 2026)

### Sprint Goal

Extend task management with filtering, deletion, status updates, and persistent storage. Improve test coverage and add logging.

### Selected Stories (12 story points)

| Story                        | Points | Priority |
| ---------------------------- | ------ | -------- |
| US-4: Filter Tasks by Status | 3      | Medium   |
| US-5: Delete a Task          | 2      | Medium   |
| US-6: Update Task Status     | 2      | Medium   |
| US-7: Persistent Storage     | 5      | Low      |

### Sprint Backlog Tasks

1. **US-4: Filter Tasks by Status**
   - Implement TaskService.getTasksByStatus(status) method
   - Add CLI command for filtering
   - Handle case-insensitive input
   - Write unit tests

2. **US-5: Delete a Task**
   - Implement TaskService.deleteTask(id) method
   - Add confirmation prompt in CLI
   - Handle non-existent task error
   - Write unit tests

3. **US-6: Update Task Status**
   - Implement TaskService.updateTaskStatus(id, status) method
   - Add status transition validation
   - Write unit tests

4. **US-7: Persistent Storage**
   - Implement JsonTaskRepository for file-based persistence
   - Add Gson dependency for JSON serialization
   - Auto-save on every operation
   - Auto-load on application start
   - Handle file not found and corruption gracefully
   - Write integration tests

5. **Process Improvements (from Sprint 1 Retrospective)**
   - Add SLF4J logging throughout the application
   - Improve test coverage with edge case tests
   - Add test results reporting to CI pipeline
