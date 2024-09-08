# Project Introduction

**This project is a sample project that integrates Canal, MySQL, Kafka, and Elasticsearch. It demonstrates how to use Canal to monitor changes in the MySQL database, send the changed data to Kafka, then use Kafka Consumer to consume the messages, and index the data into Elasticsearch.**

## Project Structure

* `com.easy.api.bean.constants`: Constants class
* `com.easy.api.config`: Configuration class
* `com.easy.api.service`: Service class
* `com.easy.api.utils`: Utility class

## Usage

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

## Code Explanation

### CanalClientService

* Connects to the MySQL database using Canal
* Monitors changes in the MySQL database
* Sends the changed data to Kafka

### KafkaConsumerService

* Consumes messages from Kafka using Kafka Consumer
* Indexes the data from the messages into Elasticsearch

### ElasticSyncProcessor

* Creates indexes using Elasticsearch's `IndexOperations`
* Sets mappings using Elasticsearch's `Mapping`

### FieldMapperUtils

* Maps data from MySQL column names to Elasticsearch field names

### AppConstants

* Defines constants

## Example Use Cases

* Monitor changes in the MySQL database
* Send the changed data to Kafka
* Index the data from Kafka into Elasticsearch

## Notes

* Canal needs to configure MySQL's binlog_format to ROW
* Kafka needs to configure the number of partitions and replicas
* Elasticsearch needs to configure the index's mapping and setting

## Version Plan

* V1.0: Implement data monitoring and synchronization
* V1.1: Implement data alerts and push notifications to Feishu, etc.
* V1.2: No plans yet

## LICENSE

This project is licensed under the Apache License 2.0.

## Contributions

If you find any bugs or have new requirements, please submit an issue or pull request.
