## Libraries and Design Decisions
Here I list my design decisions, and the motivation for use the libraries founded at `pom.xml`.

### Libraries
  - API: I'm using `Spark Java` which is a lightweight web framework
  - Json serialization/deserialization: For simplicity I decided to use `Gson`
  - Environment variables: I'm using `Typesafe's Config` library for use in memory environment variables inside the project and I'm `java-dotenv` to load .env vars int the memory
  - HTTP test calls: I decided to use `Apache HTTP Components` for making requests on end to end tests
  - Tests: Just using `JUnit`
  - Loggin: `Log4j` and `Sl4j`

### Design Decisions
I've decided to separate features in self-contained apps. I think that in some scenarios is easier to understand the project when all you want lies at the same place.

For tests, I'm separating unit tests, integration tests and end to end tests in respective folders: test, it and e2e.

I'm grouping all database's and environment variables' setup at `infra` package.

All common code (e.g. time and string parsing) lies on `shared` package.
