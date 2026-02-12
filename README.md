---

# 🚀 SpringKafkaBridge

SpringKafkaBridge is a learning project built with **Spring Boot** and **Apache Kafka** to understand event-driven architecture, asynchronous communication, and message streaming between distributed services.

---

## 📌 Project Goal

This project demonstrates how to:

- Produce and consume messages using Apache Kafka
- Build asynchronous communication between services
- Implement event-driven architecture
- Configure Kafka with Spring Boot
- Handle serialization/deserialization
- Manage consumer groups and offsets
- Implement retry and error handling
- Monitor Kafka message flow

---

## 🧠 Key Learning Topics

### 1️⃣ Apache Kafka Fundamentals
- What is Apache Kafka?
- Kafka architecture (Broker, Topic, Partition, Offset)
- Producers & Consumers
- Consumer Groups
- Zookeeper (or KRaft mode)
- Message retention
- Scaling with partitions

### 2️⃣ Spring Boot + Kafka Integration
- `spring-kafka` dependency
- KafkaTemplate (Producer)
- @KafkaListener (Consumer)
- Kafka configuration (ProducerFactory, ConsumerFactory)
- JSON serialization/deserialization
- Error handling and retries
- Dead Letter Topics (DLT)

### 3️⃣ Communication Types Covered

| Communication Type | Description                        |
| ------------------ | ---------------------------------- |
| Synchronous        | REST API call (Controller layer)   |
| Asynchronous       | Kafka-based messaging              |
| Event-Driven       | Service reacts to published events |
| One-to-Many        | One producer → multiple consumers  |
| Consumer Groups    | Parallel message consumption       |

---

## 🏗️ Architecture Overview

This project acts as a **bridge** between REST APIs and Kafka topics.

### Flow:

1. Client sends HTTP request
2. REST Controller receives request
3. Service publishes event to Kafka Topic
4. Kafka broker stores message
5. Consumer listens to topic
6. Consumer processes message
7. Optional: Forward to another topic / log / DB

---

## 🔄 System Flow Diagram

```
    +-------------+
    |   Client    |
    +-------------+
           |
           v
    +-------------+
    | REST API    |
    | Controller  |
    +-------------+
           |
           v
    +-------------+
    |   Producer  |
    | KafkaTemplate|
    +-------------+
           |
           v
    =====================
          Kafka Broker
    =====================
           |
           v
    +-------------+
    |  Consumer   |
    | @KafkaListener |
    +-------------+
           |
           v
    +-------------+
    | Processing  |
    | / Logging / |
    |  Forwarding |
    +-------------+

```



### Project structure

```
SpringKafkaBridge
│── src/main/java
│ ├── controller
│ ├── service
│ ├── producer
│ ├── consumer
│ ├── config
│ └── model
│
│── src/main/resources
│ ├── application.yml
│
│── docker-compose.yml (optional)
│── README.md
```



```

---

## ⚙️ Technologies Used

- Java 17+
- Spring Boot
- Spring Kafka
- Apache Kafka
- Maven / Gradle
- Docker (optional)
- Lombok (optional)

---

## 🔧 Configuration Example

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: bridge-group
      auto-offset-reset: earliest
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer

```



