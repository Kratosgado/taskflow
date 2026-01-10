# Definition of Done (DoD)

A user story is considered "Done" when ALL of the following criteria are met:

## Code Quality
- [ ] Code is written and compiles without errors
- [ ] Code follows Java naming conventions and project coding standards
- [ ] No compiler warnings are present
- [ ] Code has been self-reviewed for correctness and readability

## Testing
- [ ] Unit tests are written for all new functionality
- [ ] All unit tests pass locally
- [ ] All tests pass in the CI/CD pipeline
- [ ] Test coverage covers the main success path and at least one error path

## Integration
- [ ] Code is committed to the Git repository with a meaningful commit message
- [ ] Code integrates cleanly with existing functionality (no regressions)
- [ ] CI/CD pipeline runs successfully (build + tests)

## Acceptance
- [ ] All acceptance criteria for the user story are met
- [ ] The feature works as described in the user story
- [ ] Edge cases have been considered and handled

## Documentation
- [ ] Code is self-documenting with clear method and variable names
- [ ] Complex logic includes inline comments where necessary
