# Class Diagram

```mermaid
classDiagram

class User {
    Long id
    String name
    String email
    String password
    Role role
}

class Role {
    <<enumeration>>
    ADMIN
    OWNER
    ANNOTATOR
    REVIEWER
    LABELER
}

class Dataset {
    Long id
    String title
    String description
    String filePath
    User owner
}

class AuthController {
    register()
    login()
    getAllUsers()
}

class DatasetController {
    getAllDatasets()
    getDatasetById()
    createDataset()
    deleteDataset()
}

class UserController {
    createUser()
    getAllUsers()
    getUserById()
    getUserByEmail()
    deleteUser()
}

class UserService {
    register()
    createUser()
    login()
    getAllUsers()
    getUserById()
    getUserByEmail()
    deleteUser()
}

class UserRepository {
    <<interface>>
}

class DatasetRepository {
    <<interface>>
}

class JwtService {
    generateToken()
    extractEmail()
    isTokenExpired()
    isTokenValid()
}

class JwtAuthenticationFilter {
    doFilterInternal()
}

class SecurityConfig {
    passwordEncoder()
    corsConfigurationSource()
    securityFilterChain()
}

User --> Role
Dataset --> User : owner

AuthController --> UserService
AuthController --> JwtService
UserController --> UserService
DatasetController --> DatasetRepository
DatasetController --> UserRepository
UserService --> UserRepository
JwtAuthenticationFilter --> JwtService
SecurityConfig --> JwtAuthenticationFilter
UserRepository --> User
DatasetRepository --> Dataset