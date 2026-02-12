# Product Backlog

<!--toc:start-->

- [Product Backlog](#product-backlog)
  - [User Stories](#user-stories)
    - [US-1: Create a New Task](#us-1-create-a-new-task)
    - [US-2: List All Tasks](#us-2-list-all-tasks)
    - [US-3: Mark Task as Complete](#us-3-mark-task-as-complete)
    - [US-4: Filter Tasks by Status](#us-4-filter-tasks-by-status)
    - [US-5: Delete a Task](#us-5-delete-a-task)
    - [US-6: Update Task Status](#us-6-update-task-status)
    - [US-7: Persistent Storage](#us-7-persistent-storage)
  - [Backlog Summary](#backlog-summary)
  <!--toc:end-->

## User Stories

### US-1: Create a New Task

**Priority:** High | **Story Points:** 3 | **Sprint:** 1

> As a user, I want to create a new task with a title and description so that I can track work I need to do.

**Acceptance Criteria:**

- [x] User can create a task by providing a title (required) and description (optional)
- [x] Each task is automatically assigned a unique ID
- [x] Each task is assigned a default status of "TODO" upon creation
- [x] Each task records the creation timestamp
- [x] The system confirms successful task creation by displaying the task details
- [x] Creating a task with an empty title is rejected with an error message

---

### US-2: List All Tasks

**Priority:** High | **Story Points:** 2 | **Sprint:** 1

> As a user, I want to view all my tasks so that I can see everything I need to work on.

**Acceptance Criteria:**

- [x] User can list all tasks in the system
- [x] Each task displays its ID, title, status, and creation date
- [x] Tasks are displayed in a formatted, readable table
- [x] If no tasks exist, a friendly message is shown ("No tasks found.")
- [x] The list shows the total count of tasks

---

### US-3: Mark Task as Complete

**Priority:** High | **Story Points:** 2 | **Sprint:** 1

> As a user, I want to mark a task as complete so that I can track my progress.

**Acceptance Criteria:**

- [x] User can mark a task as complete by specifying its ID
- [x] The task status changes from "TODO" or "IN_PROGRESS" to "DONE"
- [x] The system displays a confirmation message with the updated task
- [x] Attempting to complete a non-existent task shows an error message
- [x] Attempting to complete an already-completed task shows a warning

---

### US-4: Filter Tasks by Status

**Priority:** Medium | **Story Points:** 3 | **Sprint:** 2

> As a user, I want to filter tasks by their status so that I can focus on specific categories of work.

**Acceptance Criteria:**

- [x] User can filter tasks by status: TODO, IN_PROGRESS, or DONE
- [x] Only tasks matching the specified status are displayed
- [x] If no tasks match the filter, a message is shown ("No tasks found with status: X")
- [x] The filter is case-insensitive
- [x] The filtered list displays the count of matching tasks

---

### US-5: Delete a Task

**Priority:** Medium | **Story Points:** 2 | **Sprint:** 2

> As a user, I want to delete a task so that I can remove items that are no longer relevant.

**Acceptance Criteria:**

- [x] User can delete a task by specifying its ID
- [x] The system asks for confirmation before deleting
- [x] The system displays a confirmation message after successful deletion
- [x] Attempting to delete a non-existent task shows an error message
- [x] Deleted tasks are permanently removed from the system

---

### US-6: Update Task Status

**Priority:** Medium | **Story Points:** 2 | **Sprint:** 2

> As a user, I want to update a task's status to IN_PROGRESS so that I can indicate what I'm currently working on.

**Acceptance Criteria:**

- [x] User can change a task's status to IN_PROGRESS
- [x] The system displays the updated task details
- [x] Attempting to update a non-existent task shows an error message
- [x] Status transitions are validated (cannot move DONE back to TODO directly)

---

### US-7: Persistent Storage

**Priority:** Low | **Story Points:** 5 | **Sprint:** 2 (stretch)

> As a user, I want my tasks to be saved to a file so that they persist between application sessions.

**Acceptance Criteria:**

- [x] Tasks are automatically saved to a JSON file after every operation
- [x] Tasks are loaded from the file when the application starts
- [x] If the file doesn't exist, the application starts with an empty task list
- [x] File corruption is handled gracefully with an error message

---

## Backlog Summary

| Story                        | Priority | Points | Sprint      |
| ---------------------------- | -------- | ------ | ----------- |
| US-1: Create a New Task      | High     | 3      | 1           |
| US-2: List All Tasks         | High     | 2      | 1           |
| US-3: Mark Task as Complete  | High     | 2      | 1           |
| US-4: Filter Tasks by Status | Medium   | 3      | 2           |
| US-5: Delete a Task          | Medium   | 2      | 2           |
| US-6: Update Task Status     | Medium   | 2      | 2           |
| US-7: Persistent Storage     | Low      | 5      | 2 (stretch) |

**Total Story Points:** 19
**Sprint 1 Velocity Target:** 7 points (3 stories)
**Sprint 2 Velocity Target:** 7-12 points (3-4 stories)
