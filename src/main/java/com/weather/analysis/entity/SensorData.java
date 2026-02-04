package com.weather.analysis.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SENSOR_DATA")
public class SensorData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * sensor location can be identified by having sensor details and location table separately along with timezone
	 */
	private String sensorId;   
    private Integer temperature;  
    private Integer humidity; 
    private Integer windspeed;
    /**
     * sensor timestamp in respective time zone
     */
    private LocalDateTime timestamp;
	
	/**
	 * 
	 * in assumption that sensors are placed across the world , server timestamp 
	 */
	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
}
