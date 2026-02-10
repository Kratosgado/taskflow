# Sprint 2 Retrospective

**Sprint:** 2
**Duration:** Jan 28 - Feb 10, 2026

---

## What Went Well

1. **Retrospective improvements were applied:** Both action items from Sprint 1 (add logging and improve test coverage) were successfully implemented. SLF4J logging was integrated throughout the application, and test count grew from 27 to 46 tests including new integration tests for the repository layer. This demonstrates that the retrospective process led to real, measurable improvements.

2. **Higher velocity:** Sprint 2 delivered 12 story points compared to Sprint 1's 7 points. This increase was possible because the foundational architecture from Sprint 1 (clean separation of model/service/CLI) made it straightforward to add new features. The existing test suite also provided confidence when refactoring.

3. **Stretch goal completed:** The persistent storage feature (US-7, 5 points) was initially considered a stretch goal but was completed within the sprint. The TaskRepository interface pattern made it clean to add persistence without disrupting existing functionality.

4. **Logging provides observability:** With SLF4J integrated, we now have structured logging at INFO, WARN, and ERROR levels. This immediately proved useful during development when debugging the JSON serialization of LocalDateTime objects.

## What Didn't Go Well

1. **JSON persistence is fragile:** While the Gson-based JSON persistence works, it required a custom TypeAdapter for `LocalDateTime`. A more robust approach would have been to use a proper database (SQLite) or at least use Java's built-in serialization. The current approach could have edge cases with special characters in task titles.

2. **CLI input parsing still basic:** Although this was identified in Sprint 1, the CLI input parsing was not improved beyond adding new commands. Multi-word titles still require using the split approach, and there is no input sanitization.

3. **No automated code coverage reporting:** While we have more tests, we don't have a code coverage tool (like JaCoCo) integrated into the build pipeline. We cannot quantitatively measure our test coverage percentage.

## What Would I Do Differently

1. **Set up logging and persistence from Sprint 0:** Both of these are cross-cutting concerns that affect the entire codebase. Adding them after Sprint 1 required touching multiple files. If included from the start, the code would have been cleaner and the Sprint 1 codebase would have been more production-ready.

2. **Use TDD more consistently:** For Sprint 1, tests were written after the implementation. For Sprint 2, I wrote some tests first (particularly for the filter and delete operations), which led to better-designed interfaces. Applying TDD consistently from the beginning would have improved code quality earlier.

3. **Add code coverage to CI pipeline:** Integrating JaCoCo from Sprint 1 would have given quantitative feedback on test coverage and helped prioritize which areas needed more testing.

## Key Takeaway

The most valuable learning from this project is the power of the retrospective process. The two specific improvements identified in Sprint 1 (logging and test coverage) were both implemented in Sprint 2 and delivered measurable benefits. This validates the Agile principle that continuous improvement through inspect-and-adapt cycles leads to better outcomes. The team velocity increase from 7 to 12 points also demonstrates how a stable architecture and growing confidence (through tests) accelerates delivery.

## Process Metrics Comparison

| Metric | Sprint 1 | Sprint 2 | Improvement |
|--------|----------|----------|-------------|
| Velocity | 7 pts | 12 pts | +71% |
| Tests | 27 | 46 | +70% |
| Logging | None | SLF4J | Added |
| Persistence | In-memory | JSON file | Added |
| Stories delivered | 3 | 4 | +33% |
| Retrospective items addressed | N/A | 2/2 (100%) | - |
