# asyncReading

> A tiny Java/Maven demo that reads data from local files (e.g., `.txt`, `.csv`) and inserts it into PostgreSQL — done in a clean, async-friendly way.

---

## ✨ Features

- ✅ Reads multiple file types (TXT / CSV)
- ⚡ Processes files asynchronously to keep I/O fast and non-blocking
- 🐘 Persists parsed rows into PostgreSQL using plain JDBC
- 🧪 Easy to run & test with Maven

---

## 🛠 Tech Stack

- **Java** 17+  
- **Maven** for build & dependency management  
- **PostgreSQL** as the target database  
- **JDBC** driver for DB access  
- **CompletableFuture** for async work
