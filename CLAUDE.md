# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build and test
./mvnw clean install        # full build with tests
./mvnw test                 # run all tests
./mvnw test -Dtest=MyTest   # run a single test class
./mvnw spring-boot:run      # run locally (requires PostgreSQL)

# Run with dev profile (uses config/application-dev.yml)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Database migrations
./mvnw liquibase:update     # apply pending Liquibase changesets

# Docker
docker build -t dawidkaszuba/home-budget:<version> .
docker push dawidkaszuba/home-budget:<version>
```

## Architecture

Spring Boot 3.5 / Java 21 MVC web app for household budget tracking. Stack: Thymeleaf templates, Spring Security, Spring Data JPA with PostgreSQL, Liquibase migrations, MapStruct, Lombok.

**Multi-tenant via `Home`**: Every domain entity (Account, Category, Expense, Income) belongs to a `Home`. Users (`BudgetUser`) belong to a `Home` and all data queries are scoped to `home_id`. The `Principal` is resolved to a `BudgetUser` → `Home` chain in every service method.

**Soft deletes**: All entities extend `AuditableEntity` which holds `created_at`, `updated_at`, `deleted_at`. Deletes use `@SQLDelete` to set `deleted_at = now()`. `HibernateSoftDeleteAspect` enables a Hibernate `deletedFilter` (condition: `deleted_at IS NULL`) on every `@Transactional` method automatically — this filter must remain active for all queries to exclude soft-deleted rows.

**Role model**: Two roles — `ROLE_ADMIN` (manages accounts, categories, home settings, users) and regular authenticated users (record expenses/incomes, view reports). Security rules are in `SecurityConfig`.

**Exception flow**: Domain exceptions either implement `FieldAwareException` (bound to a specific form field) or are general runtime exceptions. `DomainExceptionMapper` converts them to `BindingResult` errors for Thymeleaf form re-rendering. `GlobalExceptionHandler` handles uncaught cases.

**Layers** (standard for each domain: expense, income, category, account, home, user):
- `controller/` → form submission, model population, redirect-after-post
- `service/` (interface) + `service/impl/` (implementation)
- `repository/` → Spring Data JPA repositories
- `model/db/` → JPA entities; `model/dto/` → form/view DTOs
- `mapper/` → MapStruct mappers between entity ↔ DTO

**Database migrations**: Liquibase changesets live under `db/changesets/<date>/`. The master file is `db/changelog-master.xml`. New changesets must be added there in chronological order.

**Configuration**: `src/main/resources/application.yml` is production config; `config/application-dev.yml` is the dev override (points to local PostgreSQL at `10.0.0.110:5432`, disables Liquibase auto-run, enables SQL logging). Dev mail uses MailHog on port 1025.

**Internationalization**: User-facing messages are in `src/main/resources/messages_pl.properties` (Polish). Validation error codes and exception messages reference keys from this file.

**Reports**: `ReportController` + `ReportServiceImpl` aggregate expenses/incomes by category for a date range. Custom reports support filtering by `CategoryType` (EXPENSE/INCOME) and specific category IDs.
