# Uchat Project
Uchat is a communication project designed to provide efficient and scalable messaging services. This project leverages several technologies to build a robust microservices architecture with asynchronous communication and offline message storage.
## Project Highlights

- **Microservices Architecture**: Utilizes Spring Cloud in conjunction with Alibaba's ecosystem to construct a layered and aggregated microservices architecture.
- **Asynchronous Communication**: Integrates with Netty clusters for asynchronous communication and offline message storage.
- **Message Queuing**: Employs RabbitMQ to handle asynchronous communication between microservices and Netty clusters, including message broadcasting within the Netty cluster and decoupling of chat offline message storage.
- **Service Discovery and Management**: Uses Zookeeper for Netty service node registration, listening to and cleaning invalid ports and queues, distributed read/write locks for shared resources, and online user statistics.
- **Chat Service Scalability**: Builds a WebSocket server using Netty for chat services, combined with Zookeeper, Redis, and RabbitMQ to achieve cluster-based chat services, user heartbeat mechanisms, and group chat session management.
- **Load Balancing and Scalability**: Implements Nginx for horizontal scaling of the system, load balancing, and serving static resources and web projects.
- **Containerization**: Utilizes Docker for convenient containerized deployment of intermediate services such as Zookeeper, Redis, and RabbitMQ for the Netty cluster.
## Getting Started

1. **Prerequisites**:
   - Java 11 or later
   - Docker
   - Maven
   - Zookeeper
   - Redis
   - RabbitMQ
   - Nginx

2. **Setup Instructions**:
   - Clone the repository: `git clone https://github.com/yourusername/uchat.git`
   - Navigate to the project directory: `cd uchat`
   - Build the project: `mvn clean install`
   - Set up Docker containers for Zookeeper, Redis, and RabbitMQ.
   - Configure application properties as needed in `application.yml` or `application.properties`.
   - Start the services and deploy the application.

3. **Running the Project**:
   - Use Docker Compose to start the necessary services and applications: `docker-compose up`
   - Access the application via the provided Nginx
