package com.weather.analysis.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.weather.analysis.entity.SensorData;

public interface SensorDataRepository extends JpaRepository<SensorData, Integer>{

	
	@Query("SELECT s FROM SensorData s WHERE  s.sensorId IN :sensorIdList AND s.timestamp BETWEEN :fromDateTime AND :toDateTime")
	public List<SensorData> findSensorDetails(@Param("sensorIdList") List<String> sensorIdList, @Param("fromDateTime") LocalDateTime fromDateTime,  
			@Param("toDateTime") LocalDateTime toDateTime);

}
