# Sprint 1 Retrospective

<!--toc:start-->

- [Sprint 1 Retrospective](#sprint-1-retrospective)
  - [What Went Well](#what-went-well)
  - [What Didn't Go Well](#what-didnt-go-well)
  - [Action Items for Sprint 2](#action-items-for-sprint-2)
  - [Key Takeaway](#key-takeaway)
  <!--toc:end-->

**Sprint:** 1
**Duration:** Jan 14 - Jan 27, 2026

---

## What Went Well

1. **All stories completed:** We delivered all three planned stories (7 story points) within the sprint, achieving 100% of planned velocity.

2. **Strong test foundation:** 27 unit tests were written covering both the model and service layers. All tests pass consistently, giving confidence in the codebase.

3. **CI/CD established early:** Setting up the GitHub Actions pipeline early in the sprint meant every subsequent commit was automatically validated, catching issues quickly.

4. **Clean architecture:** The separation into model, service, and CLI layers makes the code easy to understand and extend.

## What Didn't Go Well

1. **No logging framework:** We did not include any logging in Sprint 1. When debugging issues during development, we relied on `System.out.println` which is not production-ready. This made troubleshooting harder and will be difficult to maintain.

2. **No persistence:** Tasks are only stored in memory. If the application exits, all data is lost. This limits the practical usefulness of the prototype and should have been factored in earlier.

3. **Limited CLI error handling:** Some error messages are generic and don't guide the user well enough. The CLI input parsing is basic (split by whitespace) which makes multi-word titles awkward.

4. **No integration tests:** We only wrote unit tests. We lack integration tests that verify the full flow from CLI input to service response.

## Action Items for Sprint 2

| #   | Improvement               | Priority | Action                                                                                                                                                |
| --- | ------------------------- | -------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1   | **Add SLF4J logging**     | High     | Integrate SLF4J with simple logger backend. Add logging at INFO level for operations and ERROR level for failures throughout TaskService and TaskApp. |
| 2   | **Improve test coverage** | High     | Add integration tests that test the full workflow. Add edge case tests for the new Sprint 2 features. Aim for testing both success and error paths.   |
| 3   | Implement persistence     | Medium   | Add JSON-based file storage (US-7) so tasks survive across sessions.                                                                                  |
| 4   | Better CLI input handling | Low      | Improve command parsing to handle quoted multi-word strings better.                                                                                   |

## Key Takeaway

Sprint 1 established a solid foundation. The main lesson is that observability (logging) should be part of the initial setup, not an afterthought. For Sprint 2, we will prioritize adding logging alongside new feature development, and improve our testing strategy by adding integration tests.
