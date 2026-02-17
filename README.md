# Wallet Service API

A robust Spring Boot backend service for managing digital wallets, supporting atomic transfers, idempotency, and balance integrity.

## 🚀 Features

* **Wallet Management**: Create wallets and retrieve real-time balances.
* **Transaction Engine**: Support for Credits, Debits, and Transfers.
* **ACID Transactions**: Ensures atomic updates between sender and receiver using `@Transactional`.
* **Idempotency**: Prevents duplicate transaction processing using unique stored keys to ensure safety across retries.
* **Precision**: Uses **minor units** (integers/longs) to prevent floating-point rounding errors, adhering to financial best practices.

## 🛠 Technical Stack

* **Java 21** & **Spring Boot 3**
* **Spring Data JPA**: For ORM and robust transaction management.
* **H2 Database**: In-memory SQL database for fast testing and portability.
* **Jakarta Validation**: To ensure API request integrity and data quality.



---

## 🔧 Setup & Installation

### Prerequisites
* **JDK 21** (Recommended) or JDK 17+
* **Maven 3.6+**

### Running the Application
1.  **Clone the repository**:
    ```bash
    git clone https://github.com/ladey21/wallet-service.git
    ```
2.  **Navigate to the root directory**:
    ```bash
    cd wallet-service
    ```
3.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

The server will start at `http://localhost:8080`.

### Database Access
You can inspect the in-memory data via the **H2 Console**:
* **URL**: `http://localhost:8080/h2-console`
* **JDBC URL**: `jdbc:h2:mem:walletdb`
* **User**: `sa`
* **Password**: *(leave blank)*

---

## 🧪 Running Tests
To execute the test suite (including unit and integration tests for idempotency and atomic transfers):
```bash
mvn test
