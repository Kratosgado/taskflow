# Sprint 1 Review

**Sprint:** 1
**Duration:** Jan 14 - Jan 27, 2026
**Sprint Goal:** Deliver the core task management functionality (create, list, complete tasks) and establish the CI/CD pipeline.

---

## Sprint Summary

Sprint 1 was successfully completed. All three planned user stories were delivered and meet the Definition of Done. The CI/CD pipeline was established and is functional.

## Completed Stories

### US-1: Create a New Task (3 points) - DONE
**Acceptance Criteria Status:**
- [x] User can create a task by providing a title (required) and description (optional)
- [x] Each task is automatically assigned a unique ID
- [x] Each task is assigned a default status of "TODO" upon creation
- [x] Each task records the creation timestamp
- [x] The system confirms successful task creation by displaying the task details
- [x] Creating a task with an empty title is rejected with an error message

**Demo:**
```
taskflow> add "Buy groceries" "Milk, eggs, bread"
Task created successfully!
  [1] Buy groceries (Status: TODO, Created: 2026-01-20 14:30)
```

### US-2: List All Tasks (2 points) - DONE
**Acceptance Criteria Status:**
- [x] User can list all tasks in the system
- [x] Each task displays its ID, title, status, and creation date
- [x] Tasks are displayed in a formatted, readable table
- [x] If no tasks exist, a friendly message is shown ("No tasks found.")
- [x] The list shows the total count of tasks

**Demo:**
```
taskflow> list
──────────────────────────────────────────────────────────────────────
ID    Title                     Status          Created
──────────────────────────────────────────────────────────────────────
1     Buy groceries             TODO            2026-01-20 14:30
2     Write report              TODO            2026-01-20 14:31
3     Review PR                 TODO            2026-01-20 14:32
──────────────────────────────────────────────────────────────────────
Total: 3 task(s)
```

### US-3: Mark Task as Complete (2 points) - DONE
**Acceptance Criteria Status:**
- [x] User can mark a task as complete by specifying its ID
- [x] The task status changes from "TODO" or "IN_PROGRESS" to "DONE"
- [x] The system displays a confirmation message with the updated task
- [x] Attempting to complete a non-existent task shows an error message
- [x] Attempting to complete an already-completed task shows a warning

**Demo:**
```
taskflow> complete 1
Task marked as complete!
  [1] Buy groceries (Status: DONE, Created: 2026-01-20 14:30)

taskflow> complete 999
Error: Task not found with ID: 999

taskflow> complete 1
Error: Task is already completed: Buy groceries
```

## DevOps Pipeline

- GitHub Actions CI pipeline configured (`.github/workflows/ci.yml`)
- Pipeline runs on every push to `main` and on pull requests
- Pipeline steps: Checkout -> JDK 21 setup -> Maven cache -> Build -> Test -> Package
- Test results are uploaded as artifacts

## Test Results

- **Total tests:** 27
- **Passed:** 27
- **Failed:** 0
- **Test coverage areas:** Task model (12 tests), TaskService (15 tests)

## Velocity

- **Planned:** 7 story points (3 stories)
- **Completed:** 7 story points (3 stories)
- **Velocity:** 7 points/sprint

## Key Metrics

| Metric | Value |
|--------|-------|
| Stories completed | 3/3 |
| Story points delivered | 7/7 |
| Tests written | 27 |
| Tests passing | 27 |
| Build status | GREEN |
