package com.weather.analysis.service;

import java.util.List;

import com.weather.analysis.request.SensorDataRequest;
import com.weather.analysis.request.SensorDetailsRequest;
import com.weather.analysis.response.SensorDetailsResponse;

import jakarta.validation.Valid;

public interface SensorDataService {

	public String createSensorData(@Valid SensorDataRequest sensorDataRequest);

	public List<SensorDetailsResponse> getSensorDetails(@Valid SensorDetailsRequest sensorDetailRequest);

}
