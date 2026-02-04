I have used MySql database
RUN below query in MySql database before run the program
CREATE SCHEMA `weather` ;
CREATE TABLE `weather`.`SENSOR_DATA` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `sensor_id` VARCHAR(100) NOT NULL,
  `temperature` INT NULL,
  `humidity` INT NULL,
  `windspeed` INT NULL,
  `timestamp` DATETIME NULL,
  `created_at` DATETIME NULL,
  PRIMARY KEY (`id`));

POSTMAN Details for testing 
Method to create sensor data- POST request
URL - http://localhost:8080/weather/createsensordata
Sample Json
-----------
{
  "sensorId": "sensor-1",
  "temperature": -10,
  "humidity": 85,
  "windspeed": 25,
  "timestamp": "2026-01-07T21:30:00"
}

Method to find sensor data- POST request
URL -http://localhost:8080/weather/getdetails
Sample Json
----------
{
  "sensorIdList": ["sensor-1"],
  "metricsList": ["HUMIDITY","TEMPERATURE"],
  "statistic": "MIN",
  "fromDateTime": "2026-01-01T21:30:00",
  "toDateTime": "2026-02-01T00:30:00"
}
