# Rewards Calculator - Spring Boot RESTful API

## Project Overview

This is a Spring Boot application that implements a customer rewards program. The application calculates and tracks reward points for customers based on their purchase transactions. It provides RESTful endpoints to query customer information and view their earned rewards.

### Business Logic

The rewards program awards points based on transaction amounts:
- **2 points** for every dollar spent **over $100** in each transaction
- **1 point** for every dollar spent **over $50** in each transaction

**Example:** A $120 purchase = 2×$20 (over $100) + 1×$50 (over $50) = 40 + 50 = **90 points**

## Project Structure

```
calc-for-rewards/
├── pom.xml                          # Maven project configuration
├── README.md                         # This file
├── .gitignore                       # Git ignore rules
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/service/springbootrewards/
│   │   │       ├── SpringBootRewardsApplication.java      # Main application entry point
│   │   │       ├── model/
│   │   │       │   ├── Reward.java                        # Abstract reward entity
│   │   │       │   ├── Customer.java                      # Customer entity
│   │   │       │   └── CustomerTransaction.java           # Transaction entity
│   │   │       ├── rewards/
│   │   │       │   ├── RewardsController.java            # REST controller
│   │   │       │   ├── RewardsService.java               # Business logic service
│   │   │       │   ├── CustomerRepository.java           # JPA repository
│   │   │       │   └── RewardsServiceMock.java           # Mock service (for testing)
│   │   │       └── utils/
│   │   │           └── Utils.java                        # Utility interface
│   │   └── resources/
│   │       ├── application.properties                     # Application configuration
│   │       └── data.sql                                  # Initial data script
│   └── test/
│       └── java/
│           └── com/example/service/springbootrewards/
│               └── SpringBootRewardsApplicationTests.java # Integration tests
```

## Technology Stack

- **Framework:** Spring Boot 2.1.9.RELEASE
- **Language:** Java 8
- **Build Tool:** Maven
- **Database:** H2 (in-memory database)
- **ORM:** JPA/Hibernate
- **Testing:** JUnit, Spring Boot Test

## Key Components

### Model Classes

#### `Reward` (Abstract)
- Base class for reward calculations
- Defines the contract for `getPoints()` method

#### `Customer`
- Represents a customer in the system
- Fields: `id`, `name`, `transactions`
- Transient fields: `rewardPoints`, `totalPurchases` (calculated on demand)
- Methods:
  - `getRewardPoints()`: Calculates total reward points across all transactions
  - `getTotalPurchases()`: Calculates total amount spent across all transactions

#### `CustomerTransaction`
- Extends `Reward` class
- Represents a single customer purchase transaction
- Fields: `id`, `customer`, `total`, `description`, `saveDate`
- Methods:
  - `getPoints()`: Implements reward calculation logic based on transaction amount

### Service Layer

#### `RewardsService`
- Provides business logic for rewards operations
- Methods:
  - `getCustomerAll()`: Retrieves all customers with their transactions
  - `getCustomerById(Integer customerId)`: Retrieves a specific customer by ID

#### `RewardsController`
- REST API endpoints for the application
- Endpoints:
  - `GET /customers` - Returns all customers
  - `GET /customers/{id}` - Returns a specific customer by ID

#### `CustomerRepository`
- JPA repository interface for CRUD operations on Customer entity
- Extends `JpaRepository<Customer, Integer>`

## Running the Application

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher

### Build and Run

1. **Build the project:**
   ```bash
   mvn clean install
   ```

2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **The application will start on:** `http://localhost:8080`

## API Endpoints

### Get All Customers
```
GET /customers
```
Returns a list of all customers with their transactions and calculated reward points.

**Response Example:**
```json
[
  {
    "id": 100,
    "name": "Pavala",
    "transactions": [
      {
        "id": 111,
        "total": 100.0,
        "description": "Electronics Purchase",
        "saveDate": "2020-10-25T10:20:10.000+0000",
        "points": 50
      },
      {
        "id": 113,
        "total": 120.0,
        "description": "Furniture",
        "saveDate": "2020-10-24T10:20:10.000+0000",
        "points": 90
      }
    ],
    "rewardPoints": 669,
    "totalPurchases": 909.36
  }
]
```

### Get Specific Customer by ID
```
GET /customers/{id}
```
Returns a specific customer with their transactions and calculated reward points.

**Example:**
```
GET /customers/100
```

**Response Example:**
```json
{
  "id": 100,
  "name": "Pavala",
  "transactions": [
    {
      "id": 111,
      "total": 100.0,
      "description": "Electronics Purchase",
      "saveDate": "2020-10-25T10:20:10.000+0000",
      "points": 50
    },
    {
      "id": 113,
      "total": 120.0,
      "description": "Furniture",
      "saveDate": "2020-10-24T10:20:10.000+0000",
      "points": 90
    }
  ],
  "rewardPoints": 669,
  "totalPurchases": 909.36
}
```

## Database Configuration

### H2 Console
The application uses H2 in-memory database with console access:
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** (leave blank)

### Data Initialization
- Initial data is loaded from `src/main/resources/data.sql`
- Contains test data for multiple customers with various transactions
- The SQL script uses absolute dates (no hardcoded month references)

## Testing

### Running Tests
```bash
mvn test
```

### Test Coverage
The application includes comprehensive integration tests covering:
- Application context loading
- Retrieving all customers
- Retrieving specific customers by ID
- Handling non-existent customers
- Reward point calculations for various transaction amounts
- Edge cases (negative amounts, null values, zero points)
- Customer name and transaction date handling
- Multiple customers with multiple transactions

## Code Standards

This project follows Java coding standards:
- **Package Naming:** `com.example.service.springbootrewards`
- **Class Naming:** PascalCase (e.g., `RewardsService`, `CustomerTransaction`)
- **Method Naming:** camelCase (e.g., `getCustomerById()`)
- **Variable Naming:** camelCase with meaningful names
- **JavaDoc:** All public classes and methods include comprehensive JavaDoc comments

## Code Quality

- **No Hardcoded Values:** Reward calculation logic uses dynamic thresholds
- **No Hardcoded Dates:** All test data uses dates in SQL scripts, not hardcoded in code
- **No Wildcard Imports:** All imports are explicit
- **Consistent Formatting:** Code follows Java conventions
- **Exception Handling:** Comprehensive error handling for edge cases

## Building and Deployment

### Build Output
```bash
mvn clean package
```
This creates a JAR file in the `target/` directory.

### Note on Version Control
The `.gitignore` file is configured to exclude:
- `/target/` - Build output directory
- `/bin/` - Compiled classes directory
- IDE-specific files (`.classpath`, `.project`, `.settings/`)
- OS-specific files (`.DS_Store`, `Thumbs.db`)
- IDE directories (`.vscode/`, `.idea/`)

## Dependencies

### Main Dependencies
- `spring-boot-starter-data-jpa` - Data access layer
- `spring-boot-starter-web` - REST API support
- `spring-boot-devtools` - Development tools
- `h2` - In-memory database

### Test Dependencies
- `spring-boot-starter-test` - Testing framework

## Future Enhancements

Possible improvements for the future:
1. Add monthly/yearly reward point summaries
2. Implement reward redemption functionality
3. Add customer authentication and authorization
4. Create frontend UI for the API
5. Support for different reward tiers
6. Export customer rewards as reports
7. Integration with external payment systems

## Author
Spring Boot Rewards Team

## Version
1.0

## License
MIT License

## Support
For issues or questions, please contact the development team.

