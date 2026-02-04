package com.weather.analysis.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SensorDetailsRequest {
	/**
	 * One or more (or all sensors) to include in results.
	 * In assumption that atlease one sensor id is present as it mentioned one or more or all sensor id
	 * Example query: Give me the average temperature and humidity for sensor 1 in the last week.
	 */
	@NotEmpty(message = "sensor Id's cannot be empty")
	public List<String> sensorIdList;
	@NotEmpty(message = "Metrics cannot be empty")
    private List<MetricsType> metricsList;        
	@NotNull(message = "Statistic cannot be empty")
    //private String statistic; 
    private StatisticType statistic;
	private LocalDateTime fromDateTime;
	private LocalDateTime toDateTime;
	
}
