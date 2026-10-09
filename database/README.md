# Database Setup

1. Start MySQL 8.
2. Create the empty database with `CREATE DATABASE labelai;` or run `schema.sql` from this folder.
3. Set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` for the backend process.
4. Hibernate updates the six JPA tables on local startup. `schema.sql` documents the same current entities; do not mix it with the stale schema from older project versions.

For a clean demo, register users through the app so passwords are stored as BCrypt hashes. Do not insert plaintext passwords manually.
