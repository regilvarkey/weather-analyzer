package com.weather.analysis.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SensorDataRequest {
	@NotNull(message = "Sensor Id cannot be empty")
	private String sensorId;   
	@NotNull(message = "Temperature cannot be empty")
	@Min(value = -100, message = "Temperature is too low")
	@Max(value = 100, message = "Temperature is too high")
    private Integer temperature;  
    private Integer humidity; 
    @Min(value = 0, message = "Wind speed cannot be less than 0")
    private Integer windspeed;
    @NotNull(message = "Time cannot be empty")
    private LocalDateTime timestamp;
}
