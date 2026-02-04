package com.weather.analysis.response;

import lombok.Data;

@Data
public class SensorDetailsResponse {
	
	public String sensorId;
	public String metricName;
	public String statistic;
	public Integer value;

}
