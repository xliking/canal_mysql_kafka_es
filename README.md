Canal + MySQL + Kafka + Elasticsearch --- V1.0
=====================================================

项目介绍
-----------

**本项目是一个整合Canal、MySQL、Kafka和Elasticsearch的示例项目。它演示了如何使用Canal监听MySQL数据库的变化，将变化的数据发送到Kafka，然后使用Kafka Consumer消费消息，并将数据索引到Elasticsearch中。**


项目结构
------------

* `com.easy.api.bean.constants`: 常量类
* `com.easy.api.config`: 配置类
* `com.easy.api.service`: 服务类
* `com.easy.api.utils`: 工具类

使用方法
----------

1. 搭建环境
    * 安装MySQL数据库
    * 安装Canal
    * 安装Kafka
    * 安装Elasticsearch
2. 配置项目
    * 修改 `application.properties` 文件中的数据库、Canal、Kafka和Elasticsearch的连接信息
3. 运行项目
    * 运行 `CanalClientService` 类，开始监听MySQL数据库的变化
    * 运行 `KafkaConsumerService` 类，开始消费Kafka中的消息并将数据索引到Elasticsearch中

代码解释
------------

### CanalClientService

* 使用Canal连接MySQL数据库
* 监听MySQL数据库的变化
* 将变化的数据发送到Kafka

### KafkaConsumerService

* 使用Kafka Consumer消费Kafka中的消息
* 将消息中的数据索引到Elasticsearch中

### ElasticSyncProcessor

* 使用Elasticsearch的 `IndexOperations` 创建索引
* 使用Elasticsearch的 `Mapping` 设置映射

### FieldMapperUtils

* 将数据从MySQL的列名映射到Elasticsearch的字段名

### AppConstants

* 定义常量

示例用途
------------

* 监听MySQL数据库的变化
* 将变化的数据发送到Kafka
* 将Kafka中的数据索引到Elasticsearch中

注意事项
----------

* Canal需要配置MySQL的binlog_format为ROW
* Kafka需要配置 partition 和 replica 的数量
* Elasticsearch需要配置索引的 mapping 和 setting


版本计划
----------
* V1.0 : 实现数据监听同步
* V1.1 : 实现数据告警，推送至飞书等
* V1.2 : 暂无规划

LICENSE
-------

This project is licensed under the Apache License 2.0.

贡献
--------

如果您发现任何 bug 或者有新的需求，请提交 issue 或者 pull request。