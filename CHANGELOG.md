# Changelog & Patch Notes

All notable changes to the **Job Application Tracking System** are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]
### Planned
- JWT-based authentication and role-based access control (RBAC).
- Resume and attachment upload support via AWS S3 / MinIO.
- Automated email reminders for upcoming interviews.
- Export applications to CSV/PDF reports.

---

## [1.0.0] - 2026-10-14
### Added
- **Core Entities & JPA Mapping**:
  - `JobApplication`: Complete model with role title, description, application date, status, salary range, work type, notes, and relational mappings.
  - `Company`: Model capturing corporate profile, website, industry, location, and associated applications.
  - `Interview`: Model tracking interview rounds, schedule timestamp, interviewer info, notes, and completion flag.
  - Enums: `ApplicationStatus` (10 lifecycle stages) and `InterviewStage` (9 interview round classifications).
- **CRUD REST APIs**:
  - Full CRUD operations for Companies (`/api/v1/companies`).
  - Full CRUD operations for Job Applications (`/api/v1/applications`).
  - Status patch endpoint (`PATCH /api/v1/applications/{id}/status`).
  - Full CRUD operations for Interviews (`/api/v1/interviews`).
- **Dynamic Filtering & Sorting**:
  - Advanced query execution using Spring Data JPA Specifications and JPQL.
  - Multi-parameter filtering by `status`, `companyId`, `roleTitle` (case-insensitive search), and `applicationDate` range (`startDate` to `endDate`).
  - Configurable pagination (`page`, `size`) and dynamic multi-directional sorting (`sortBy`, `sortDir`).
- **Validation & Exception Handling**:
  - Bean Validation (`@Valid`, `@NotBlank`, `@NotNull`) across all DTO models.
  - Centralized `@RestControllerAdvice` (`GlobalExceptionHandler`) emitting standardized `ApiErrorResponse` payloads with HTTP status, timestamp, route path, and granular field-level validation errors.
- **Interactive Documentation**:
  - SpringDoc OpenAPI (Swagger UI) integration available at `/swagger-ui.html`.
  - Static responsive GitHub Pages documentation dashboard located in `docs/index.html`.
- **CI/CD Automation**:
  - GitHub Actions CI workflow (`.github/workflows/ci.yml`) supporting automated compilation, test execution on H2 in-memory DB, and build artifact packaging.
  - GitHub Actions CD Release pipeline (`.github/workflows/release.yml`) triggered on tag pushes (`v*.*.*`) generating GitHub Releases with packaged JARs.
  - GitHub Actions Pages workflow (`.github/workflows/pages.yml`) publishing API documentation to GitHub Pages on pushes to `main`.
- **Infrastructure & Development Environment**:
  - Maven Wrapper scripts (`mvnw` and `mvnw.cmd`).
  - Docker Compose configuration (`docker-compose.yml`) for local PostgreSQL containerization.
  - Test profile configuration with in-memory H2 database.
  - Comprehensive unit and integration test suite with MockMvc.
