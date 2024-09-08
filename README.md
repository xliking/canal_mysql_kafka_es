# 项目介绍 (Project Introduction)

[中文](#中文) | [English](#english)

## 中文

### 项目介绍

**本项目是一个整合Canal、MySQL、Kafka和Elasticsearch的示例项目。它演示了如何使用Canal监听MySQL数据库的变化，将变化的数据发送到Kafka，然后使用Kafka Consumer消费消息，并将数据索引到Elasticsearch中。**

### 项目结构

* `com.easy.api.bean.constants`: 常量类
* `com.easy.api.config`: 配置类
* `com.easy.api.service`: 服务类
* `com.easy.api.utils`: 工具类

### 使用方法

1. 搭建环境
   * 安装MySQL数据库
   * 安装Canal
   * 安装Kafka
   * 安装Elasticsearch
2. 配置项目
   * 修改 `application.properties` 文件中的数据库、Canal、Kafka和Elasticsearch的连接信息
3. 在你需要监听的类上打上`ElasticSync`注解，字段上打上`ElasticField`注解，可参考 `pojo文件夹下的类`
   (注意: `ElasticField` 可以指定ES字段的名称, 如果该字段上没有此注解，ES则也不会存储该字段)
4. 运行项目
   * 运行 `DataCenterApplication`

### 代码解释

#### CanalClientService

* 使用Canal连接MySQL数据库
* 监听MySQL数据库的变化
* 将变化的数据发送到Kafka

#### KafkaConsumerService

* 使用Kafka Consumer消费Kafka中的消息
* 将消息中的数据索引到Elasticsearch中

#### ElasticSyncProcessor

* 使用Elasticsearch的 `IndexOperations` 创建索引
* 使用Elasticsearch的 `Mapping` 设置映射

#### FieldMapperUtils

* 将数据从MySQL的列名映射到Elasticsearch的字段名

#### AppConstants

* 定义常量

### 示例用途

* 监听MySQL数据库的变化
* 将变化的数据发送到Kafka
* 将Kafka中的数据索引到Elasticsearch中

### 注意事项

* Canal需要配置MySQL的binlog_format为ROW
* Kafka需要配置 partition 和 replica 的数量
* Elasticsearch需要配置索引的 mapping 和 setting

### 版本计划

* V1.0 : 实现数据监听同步
* V1.1 : 实现数据告警，推送至飞书等
* V1.2 : 暂无规划

### LICENSE

This project is licensed under the Apache License 2.0.

### 贡献

如果您发现任何 bug 或者有新的需求，请提交 issue 或者 pull request。

## English

### Project Introduction

**This project is a sample project that integrates Canal, MySQL, Kafka, and Elasticsearch. It demonstrates how to use Canal to monitor changes in the MySQL database, send the changed data to Kafka, then use Kafka Consumer to consume the messages, and index the data into Elasticsearch.**

### Project Structure

* `com.easy.api.bean.constants`: Constants class
* `com.easy.api.config`: Configuration class
* `com.easy.api.service`: Service class
* `com.easy.api.utils`: Utility class

### Usage

1. Set up the environment
   * Install MySQL database
   * Install Canal
   * Install Kafka
   * Install Elasticsearch
2. Configure the project
   * Modify the connection information for database, Canal, Kafka, and Elasticsearch in the `application.properties` file
3. Add `ElasticSync` annotation to the classes you need to monitor, and `ElasticField` annotation to the fields. Refer to the classes in the `pojo` folder.
   (Note: `ElasticField` can specify the field name in ES. If this annotation is not present on a field, that field will not be stored in ES.)
4. Run the project
   * Run `DataCenterApplication`

### Code Explanation

#### CanalClientService

* Connects to the MySQL database using Canal
* Monitors changes in the MySQL database
* Sends the changed data to Kafka

#### KafkaConsumerService

* Consumes messages from Kafka using Kafka Consumer
* Indexes the data from the messages into Elasticsearch

#### ElasticSyncProcessor

* Creates indexes using Elasticsearch's `IndexOperations`
* Sets mappings using Elasticsearch's `Mapping`

#### FieldMapperUtils

* Maps data from MySQL column names to Elasticsearch field names

#### AppConstants

* Defines constants

### Example Use Cases

* Monitor changes in the MySQL database
* Send the changed data to Kafka
* Index the data from Kafka into Elasticsearch

### Notes

* Canal needs to configure MySQL's binlog_format to ROW
* Kafka needs to configure the number of partitions and replicas
* Elasticsearch needs to configure the index's mapping and setting

### Version Plan

* V1.0: Implement data monitoring and synchronization
* V1.1: Implement data alerts and push notifications to Feishu, etc.
* V1.2: No plans yet

### LICENSE

This project is licensed under the Apache License 2.0.

### Contributions

If you find any bugs or have new requirements, please submit an issue or pull request.
