# Contributing to Job Application Tracking System

Thank you for your interest in contributing to the **Job Application Tracking System**! We welcome bug reports, feature proposals, and pull requests.

---

## Code of Conduct
Please ensure a respectful, collaborative, and inclusive environment for everyone.

---

## Development Workflow

### 1. Fork & Clone
```bash
git clone https://github.com/pri-1207/job-application-tracker.git
cd job-application-tracker
```

### 2. Branching Strategy
We follow GitFlow guidelines:
- `main`: Production-ready code, directly deployed.
- `develop`: Integration branch for tested features.
- `feature/<feature-name>`: Dedicated feature development branches.
- `bugfix/<fix-name>`: Bug fix branches.
- `hotfix/<fix-name>`: Immediate production fixes.

Create a branch for your change:
```bash
git checkout -b feature/dynamic-company-tagging
```

### 3. Local Setup
1. Launch PostgreSQL with Docker:
   ```bash
   docker compose up -d
   ```
2. Run tests to confirm setup:
   ```bash
   ./mvnw test
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### 4. Coding Standards
- Follow official Java conventions and Google Java Style Guide.
- Use meaningful variable and method names.
- Ensure all public APIs have JavaDoc comments.
- Maintain test coverage for all newly introduced service and controller logic.
- Ensure all endpoints have Bean Validation annotations and appropriate error responses.

### 5. Commits & Pull Requests
- Use conventional commit messages:
  - `feat: add export to CSV functionality`
  - `fix: resolve null check in application filter`
  - `docs: update API documentation with new parameters`
  - `test: add controller unit tests for interview schedule`
- Fill out the provided [Pull Request Template](.github/PULL_REQUEST_TEMPLATE.md).
- Ensure all automated GitHub Actions CI checks pass.
