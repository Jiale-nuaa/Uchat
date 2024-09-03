## This project is named Uchat, which is a communication project 
###项目亮点
	使用SpringCloud 结合 alibaba 体系构建分层的聚合微服务架构项目，与Netty 集群进行异步通信并且进行离线消息存储。
	通过RabbitMQ 实现微服务系统与 Netty 集群异步通信，Netty 集群内部消息广播，聊天离线消息异步解耦与存储。
	通过Zookeeper 实现 Netty 服务节点注册，监听清理无效端口与队列，共享资源的分布式读写锁，Netty 服务在线人数统计。
	使用Netty 聊天业务集群化，构建 Web-Socket 服务器，结合 Zookeeper&Redis&RabbitMQ 实现聊天业务集群化，聊天用户心跳机制，群组会话分配。
	通过Nginx 实现系统集群的水平扩展，实现系统的负载均衡，发布静态资源与 Web 项目。
	Docker 为 Netty 集群服务提供便捷的Zookeeper&Redis&RabbitMQ 等中间件的容器化部署。
