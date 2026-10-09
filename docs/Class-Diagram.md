# Class / Module Diagram

The diagram reflects the high-level code modules: React client → REST controllers → service/repository layers → JPA entities and MySQL. Some existing CRUD controllers directly call repositories; the architecture can be further improved by moving all business logic into service classes.

![Module dependency diagram](diagrams/modules.png)

Editable source: `diagrams/modules.dot`.
