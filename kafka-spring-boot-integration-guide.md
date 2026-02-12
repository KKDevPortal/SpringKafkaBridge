# Kafka + Spring Boot Integration Guide

This guide explains how to integrate **Apache Kafka** with a **Spring Boot** application.

You will learn:

* How to configure Spring Boot for Kafka
* How to create a Producer
* How to create a Consumer
* How to send and receive messages
* How Kafka works inside a Spring application

---

# 📌 Prerequisites

Make sure you have:

* Java 17+ (recommended)
* Spring Boot 3+
* Kafka running (via Docker Compose)
* Maven or Gradle

Kafka must be accessible at:

```
localhost:9092
```

---

# 📦 1️⃣ Add Dependencies

## Maven

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

---

# ⚙️ 2️⃣ Configure application.properties

```properties
spring.application.name=kafka-spring-demo

# Kafka broker
spring.kafka.bootstrap-servers=localhost:9092

# Producer
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

# Consumer
spring.kafka.consumer.group-id=demo-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```

If running Spring Boot inside Docker:

```properties
spring.kafka.bootstrap-servers=kafka:29092
```

---

# 🧱 3️⃣ Create a Kafka Topic

Inside Kafka container:

```bash
kafka-topics --create \
  --topic demo-topic \
  --bootstrap-server localhost:9092 \
  --partitions 3 \
  --replication-factor 1
```

---

# ✉️ 4️⃣ Create a Producer

## Kafka Producer Service

```java
@Service
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send("demo-topic", message);
    }
}
```

---

# 🌍 5️⃣ Create REST Controller to Send Messages

```java
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageProducer producer;

    public MessageController(MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String sendMessage(@RequestParam String message) {
        producer.sendMessage(message);
        return "Message sent to Kafka!";
    }
}
```

Test:

```bash
curl -X POST "http://localhost:8080/api/messages?message=HelloKafka"
```

---

# 📥 6️⃣ Create a Consumer

```java
@Service
public class MessageConsumer {

    @KafkaListener(topics = "demo-topic", groupId = "demo-group")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}
```

When a message is sent, it will appear in application logs.

---

# 🧠 How It Works

1. REST API receives request
2. Producer sends message to Kafka topic
3. Kafka stores message in partition
4. Consumer (within same app or another service) receives it
5. Message is processed

---

# 📊 Message Flow Diagram

```
Client → REST API → Kafka Producer → Topic → Kafka Broker
                                             ↓
                                     Kafka Consumer → Business Logic
```

---

# 📦 JSON Message Example

If sending objects instead of strings:

## Update properties

```properties
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*
```

## Send Object

```java
public record Order(Long id, String product, int quantity) {}
```

Producer:

```java
kafkaTemplate.send("orders", new Order(1L, "Laptop", 2));
```

Consumer:

```java
@KafkaListener(topics = "orders", groupId = "order-group")
public void consume(Order order) {
    System.out.println(order);
}
```

---

# 👥 Consumer Groups Explained

If you run:

* 1 instance → all partitions handled by 1 consumer
* 3 instances (same group) → partitions distributed
* Different group ID → each group receives all messages

Consumer groups enable horizontal scaling.

---

# ⚠️ Common Issues

## Cannot connect to Kafka

Check:

```bash
docker ps
```

Check logs:

```bash
docker logs kafka
```

---

## Messages not consumed

* Ensure topic name matches
* Ensure group ID matches
* Check `auto-offset-reset=earliest`

---

# 🏗 Production Considerations

For production:

* Use replication factor 3
* Enable SASL/SSL
* Configure retries
* Configure dead-letter topics
* Monitor with Prometheus + Grafana
* Use idempotent producer

Example production settings:

```properties
spring.kafka.producer.acks=all
spring.kafka.producer.retries=3
spring.kafka.producer.properties.enable.idempotence=true
```

---

# 🚀 Example Microservice Use Case

Order Service → publishes `order-created`

Inventory Service → consumes `order-created`

Payment Service → consumes `order-created`

Notification Service → consumes `payment-completed`

Kafka enables:

* Loose coupling
* Event-driven architecture
* Independent scaling

---

# 🧹 Stop Kafka

```bash
docker-compose down
```

---

# 📚 Summary

| Component      | Role                |
| -------------- | ------------------- |
| Kafka          | Message broker      |
| Topic          | Message stream      |
| Producer       | Sends events        |
| Consumer       | Processes events    |
| Consumer Group | Scales consumption  |
| Spring Kafka   | Integration library |

---
