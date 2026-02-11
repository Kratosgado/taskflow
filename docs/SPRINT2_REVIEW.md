# Sprint 2 Review

<!--toc:start-->

- [Sprint 2 Review](#sprint-2-review)
  - [Sprint Summary](#sprint-summary)
  - [Completed Stories](#completed-stories)
    - [US-4: Filter Tasks by Status (3 points) - DONE](#us-4-filter-tasks-by-status-3-points-done)
    - [US-5: Delete a Task (2 points) - DONE](#us-5-delete-a-task-2-points-done)
    - [US-6: Update Task Status (2 points) - DONE](#us-6-update-task-status-2-points-done)
    - [US-7: Persistent Storage (5 points) - DONE (Stretch Goal)](#us-7-persistent-storage-5-points-done-stretch-goal)
  - [Retrospective Improvements Implemented](#retrospective-improvements-implemented)
  - [Test Results](#test-results)
  - [Velocity](#velocity)
  - [Key Metrics](#key-metrics)
  <!--toc:end-->

**Sprint:** 2
**Duration:** Jan 28 - Feb 10, 2026
**Sprint Goal:** Extend task management with filtering, deletion, status updates, and persistent storage. Improve test coverage and add logging.

---

## Sprint Summary

Sprint 2 was successfully completed. All four planned user stories (including the stretch goal of persistent storage) were delivered. The retrospective improvements from Sprint 1 (logging and better test coverage) were also implemented.

## Completed Stories

### US-4: Filter Tasks by Status (3 points) - DONE

**Acceptance Criteria Status:**

- [x] User can filter tasks by status: TODO, IN_PROGRESS, or DONE
- [x] Only tasks matching the specified status are displayed
- [x] If no tasks match the filter, a message is shown ("No tasks found with status: X")
- [x] The filter is case-insensitive
- [x] The filtered list displays the count of matching tasks

**Demo:**

```
taskflow> filter TODO
──────────────────────────────────────────────────────────────────────
ID    Title                     Status          Created
──────────────────────────────────────────────────────────────────────
2     Write report              TODO            2026-01-30 10:15
3     Review PR                 TODO            2026-01-30 10:16
──────────────────────────────────────────────────────────────────────
Found: 2 task(s) with status TODO

taskflow> filter done
──────────────────────────────────────────────────────────────────────
ID    Title                     Status          Created
──────────────────────────────────────────────────────────────────────
1     Buy groceries             DONE            2026-01-30 10:14
──────────────────────────────────────────────────────────────────────
Found: 1 task(s) with status DONE

taskflow> filter in_progress
No tasks found with status: IN_PROGRESS
```

### US-5: Delete a Task (2 points) - DONE

**Acceptance Criteria Status:**

- [x] User can delete a task by specifying its ID
- [x] The system asks for confirmation before deleting
- [x] The system displays a confirmation message after successful deletion
- [x] Attempting to delete a non-existent task shows an error message
- [x] Deleted tasks are permanently removed from the system

**Demo:**

```
taskflow> delete 2
Are you sure you want to delete task 2? (y/n): y
Task deleted successfully!
  [2] Write report (Status: TODO, Created: 2026-01-30 10:15)

taskflow> delete 999
Error: Task not found with ID: 999
```

### US-6: Update Task Status (2 points) - DONE

**Acceptance Criteria Status:**

- [x] User can change a task's status to IN_PROGRESS
- [x] The system displays the updated task details
- [x] Attempting to update a non-existent task shows an error message
- [x] Status transitions are validated (cannot move DONE back to IN_PROGRESS)

**Demo:**

```
taskflow> progress 3
Task set to IN_PROGRESS!
  [3] Review PR (Status: IN_PROGRESS, Created: 2026-01-30 10:16)

taskflow> progress 1
Error: Cannot move a completed task back to IN_PROGRESS. Task: Buy groceries
```

### US-7: Persistent Storage (5 points) - DONE (Stretch Goal)

**Acceptance Criteria Status:**

- [x] Tasks are automatically saved to a JSON file after every operation
- [x] Tasks are loaded from the file when the application starts
- [x] If the file doesn't exist, the application starts with an empty task list
- [x] File corruption is handled gracefully with an error message

**Demo:**

```
# First session
taskflow> add "Important task" "Do not lose this"
Task created successfully!
  [1] Important task (Status: TODO, Created: 2026-02-05 14:00)
taskflow> exit
Goodbye!

# Second session - tasks persist
taskflow> list
──────────────────────────────────────────────────────────────────────
ID    Title                     Status          Created
──────────────────────────────────────────────────────────────────────
1     Important task            TODO            2026-02-05 14:00
──────────────────────────────────────────────────────────────────────
Total: 1 task(s)
```

## Retrospective Improvements Implemented

| Improvement from Sprint 1 | Status                                                                                        |
| ------------------------- | --------------------------------------------------------------------------------------------- |
| Add SLF4J logging         | Done - INFO/WARN/ERROR logging added throughout TaskService, TaskApp, and JsonTaskRepository  |
| Improve test coverage     | Done - Added 19 new tests (from 27 to 46), including integration tests for JsonTaskRepository |

## Test Results

- **Total tests:** 46
- **Passed:** 46
- **Failed:** 0
- **Test breakdown:**
  - Task model tests: 12
  - TaskService tests: 26 (up from 15)
  - JsonTaskRepository integration tests: 8 (new)

## Velocity

- **Planned:** 12 story points (4 stories including stretch)
- **Completed:** 12 story points (4 stories)
- **Sprint 1 Velocity:** 7 points
- **Sprint 2 Velocity:** 12 points
- **Average Velocity:** 9.5 points/sprint

## Key Metrics

| Metric                 | Sprint 1  | Sprint 2  |
| ---------------------- | --------- | --------- |
| Stories completed      | 3/3       | 4/4       |
| Story points delivered | 7         | 12        |
| Total tests            | 27        | 46        |
| Tests passing          | 27        | 46        |
| Build status           | GREEN     | GREEN     |
| Logging                | None      | SLF4J     |
| Persistence            | In-memory | JSON file |
