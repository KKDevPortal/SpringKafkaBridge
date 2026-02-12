# Kafka + Zookeeper Basic Setup

This project provides a basic Docker setup for running:

* Apache Kafka
* Apache Zookeeper

It is intended for local development and learning purposes.

---

# 📌 What is Kafka?

**Apache Kafka** is a distributed event streaming platform used for:

* High-throughput message publishing
* Event-driven architectures
* Log aggregation
* Real-time data streaming
* Microservices communication

Kafka works using:

* **Producers** → Send messages
* **Topics** → Store messages
* **Partitions** → Split topic for scalability
* **Consumers** → Read messages
* **Consumer Groups** → Scale message processing

Kafka guarantees:

* Durability
* Scalability
* Fault tolerance
* High performance

---

# 📌 What is Zookeeper?

**Apache Zookeeper** is a distributed coordination service.

In Kafka (classic mode), Zookeeper is responsible for:

* Broker registration
* Leader election
* Maintaining cluster metadata
* Managing configuration

⚠️ Newer Kafka versions can run without Zookeeper (KRaft mode), but this setup uses the classic Zookeeper-based architecture.

---

# 🏗 Architecture Overview

This setup contains:

* 1 Zookeeper node
* 1 Kafka broker

Since there is only **one broker**, replication factor must be `1`.

---

# 🚀 How to Start

```bash
docker-compose up -d
```

Check running containers:

```bash
docker ps
```

---

# 🔎 How to Navigate to Containers

### Enter Kafka container

```bash
docker exec -it kafka bash
```

### Enter Zookeeper container

```bash
docker exec -it zookeeper bash
```

---

# 🧱 Kafka Concepts

## 1️⃣ Cluster

A Kafka **cluster** is a group of Kafka brokers.

In this setup:

* Cluster = 1 broker only

In production:

* Usually 3+ brokers

---

## 2️⃣ Topic

A **topic** is a category or stream of messages.

Example topics:

* `orders`
* `payments`
* `notifications`

---

## 3️⃣ Partition

Each topic can be split into multiple **partitions**.

Why partitions?

* Parallel processing
* Scalability
* Ordering per partition

---

## 4️⃣ Replication Factor

Replication factor defines how many copies of each partition exist.

Example:

* Replication factor = 3 → 3 brokers required
* In this setup → must use `1`

---

# 🛠 Kafka CLI Commands

All commands must be executed **inside the Kafka container**:

```bash
docker exec -it kafka bash
```

---

# 📦 Create Topic

```bash
kafka-topics --create \
  --topic demo-topic \
  --bootstrap-server localhost:9092 \
  --partitions 3 \
  --replication-factor 1
```

Explanation:

* `--topic` → Topic name
* `--partitions 3` → 3 partitions
* `--replication-factor 1` → Single broker

---

# 📋 List Topics

```bash
kafka-topics --list --bootstrap-server localhost:9092
```

---

# 🔍 Describe Topic

```bash
kafka-topics --describe \
  --topic demo-topic \
  --bootstrap-server localhost:9092
```

Shows:

* Partition count
* Leader
* Replicas
* In-sync replicas

---

# 🗑 Delete Topic

```bash
kafka-topics --delete \
  --topic demo-topic \
  --bootstrap-server localhost:9092
```

---

# ✉️ Produce Messages (Send Data)

```bash
kafka-console-producer \
  --topic demo-topic \
  --bootstrap-server localhost:9092
```

Then type messages:

```
Hello Kafka
Message 1
Message 2
```

Press `Ctrl + C` to exit.

---

# 📥 Consume Messages (Read Data)

```bash
kafka-console-consumer \
  --topic demo-topic \
  --bootstrap-server localhost:9092 \
  --from-beginning
```

---

# 👥 Consumer Group Example

```bash
kafka-console-consumer \
  --topic demo-topic \
  --bootstrap-server localhost:9092 \
  --group demo-group \
  --from-beginning
```

List consumer groups:

```bash
kafka-consumer-groups \
  --bootstrap-server localhost:9092 \
  --list
```

Describe consumer group:

```bash
kafka-consumer-groups \
  --bootstrap-server localhost:9092 \
  --describe \
  --group demo-group
```

---

# 📊 Example Use Case

### Microservices Communication

Service A (Order Service):

* Sends message to topic `orders`

Service B (Inventory Service):

* Listens to topic `orders`
* Updates stock

Service C (Notification Service):

* Listens to topic `orders`
* Sends confirmation email

Kafka decouples services:

* Services do not call each other directly
* They communicate via events

---

# 🧠 Why Use Kafka?

| Feature         | Benefit                      |
| --------------- | ---------------------------- |
| High throughput | Handles millions of messages |
| Partitioning    | Horizontal scaling           |
| Replication     | Fault tolerance              |
| Persistence     | Messages stored on disk      |
| Consumer groups | Parallel processing          |

---

# ⚠️ Important Notes

* This setup is for **development only**
* Replication factor is `1`
* Not suitable for production
* No security (SASL/SSL) configured

---

# 🧹 Stop the Cluster

```bash
docker-compose down
```

To remove volumes:

```bash
docker-compose down -v
```

---

# 📚 Summary

* Kafka = Distributed event streaming platform
* Zookeeper = Cluster coordination service
* Topic = Message category
* Partition = Scalability unit
* Replication = Fault tolerance mechanism
* Producer = Sends data
* Consumer = Reads data

---

