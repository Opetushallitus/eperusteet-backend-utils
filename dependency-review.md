# Services parent POM dependency review

Reviewed: 2026-11-13.

Target: `services-parent-pom/pom.xml`.

The static review covered source and configuration in `eperusteet-backend-utils` and the inheriting `eperusteet-service`, `eperusteet-ylops-service`, `eperusteet-amosaa-service`, and `eperusteet-pdf-service` projects. Cached Spring Boot POMs were also inspected for transitive dependencies.

These are removal or scope-change candidates, not confirmed safe removals. Missing direct references do not rule out runtime, reflective, auto-configuration, or transitive usage.

## Strongest removal candidates

| Dependency | Evidence |
| --- | --- |
| `org.jacoco:jacoco-maven-plugin` under `<dependencies>` | Already configured under `<build><plugins>`. The application dependency appears unnecessary; retain the build plugin. |
| `org.jeasy:easy-random` | No usage found. Declared with `<type>pom</type>`, which does not supply Easy Random implementation classes. |
| `org.springframework.plugin:spring-plugin-core` | No source or configuration references found. |
| `org.springframework.plugin:spring-plugin-metadata` | No source or configuration references found. |
| `one.util:streamex` | No usage found. |
| `com.fasterxml.jackson.dataformat:jackson-dataformat-toml` | No TOML mapper or factory usage found. |
| `javax.el:javax.el-api` | No usage found. This legacy `javax` API is declared alongside Jakarta EL dependencies. |
| `org.glassfish:javax.el` | No usage found. This legacy EL implementation is declared alongside Jakarta EL dependencies. |

## Candidates requiring transitive-dependency checks

| Dependency | Evidence and remaining uncertainty |
| --- | --- |
| `com.google.code.gson:gson` | No direct usage found; external libraries might require it. |
| `org.apache.httpcomponents:httpclient-cache` | No caching-client usage found; check external consumers before removal. |
| `commons-beanutils:commons-beanutils` | No direct usage found; it may support another library. |

## Redundant declarations and scope candidates

These libraries may still be needed. Removing a redundant declaration does not necessarily remove the library from the dependency graph.

| Dependency | Finding |
| --- | --- |
| `spring-boot-starter` | Already supplied through other starters. |
| `HikariCP` | Already supplied through the JDBC starter, inherited through the JPA starter. |
| `assertj-core` | Already supplied through test starters. |
| `spring-test` | Already supplied through test starters. |
| `mockito-core` | No production usage found. Currently defaults to compile scope; consider test scope. |
| `jsonassert` | Test usage found, with no production usage found. Currently defaults to compile scope; consider test scope. |

## Validation limits

No dependencies were changed. Maven dependency resolution, builds, service startup, and tests were not run. Before removing candidates, inspect the resolved dependency trees for all inheriting services, preserve any intentional version constraints, and verify affected builds, tests, and runtime behavior.
