package com.weather.analysis.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weather.analysis.entity.SensorData;
import com.weather.analysis.exception.SensiorException;
import com.weather.analysis.repository.SensorDataRepository;
import com.weather.analysis.request.SensorDataRequest;
import com.weather.analysis.request.SensorDetailsRequest;
import com.weather.analysis.response.SensorDetailsResponse;
import com.weather.analysis.service.SensorDataService;

import jakarta.validation.Valid;

@Service
public class SensorDataServiceImpl implements SensorDataService {


	@Autowired
	private SensorDataRepository sensorDataRepository;
	/**
	 * Method to save sensor details
	 */
	@Override
	public String createSensorData(@Valid SensorDataRequest sensorDataRequest) {
		SensorData sensorData = SensorData.builder().sensorId(sensorDataRequest.getSensorId()).temperature(sensorDataRequest.getTemperature())
				.humidity(sensorDataRequest.getHumidity()).windspeed(sensorDataRequest.getWindspeed())
				.timestamp(sensorDataRequest.getTimestamp()).build();
		try {
			sensorDataRepository.save(sensorData);
		}catch(Exception ex) {
			throw new SensiorException("Sensor data cannot be saved");
		}
		return "Data saved successfully";
	}
	/**
	 * Method to find sensor details 
	 */
	@Override
	public List<SensorDetailsResponse> getSensorDetails(SensorDetailsRequest sensorDetailRequest) {

		LocalDateTime fromDateTime = Optional.ofNullable(sensorDetailRequest.getFromDateTime()).orElse(LocalDateTime.now().minusMonths(1));
		/**
		 * A date range (if not specified, the latest data should be queried)
		 * Not specified the latest data of how many days required. Assuming that for previous 1 month
		 */
		LocalDateTime toDateTime = Optional.ofNullable(sensorDetailRequest.getToDateTime()).orElse(LocalDateTime.now());


		List<SensorData> sensorDataList = sensorDataRepository.findSensorDetails(sensorDetailRequest.getSensorIdList(),fromDateTime, toDateTime );
		Map<String,List<SensorData>> sensorIdMap = new HashMap<>();

		sensorDataList.forEach(sensordetails->{
			if(null!=sensorIdMap.get(sensordetails.getSensorId())) {
				sensorIdMap.get(sensordetails.getSensorId()).add(sensordetails);
			}else {
				List<SensorData> sensorList = new ArrayList<>();
				sensorList.add(sensordetails);
				sensorIdMap.put(sensordetails.getSensorId(), sensorList);
			}

		});

		return generateSensorDetailsResponse(sensorIdMap,  sensorDetailRequest);
	}
	/**
	 * 
	 * @param sensorIdMap
	 * @param sensorDetailRequest
	 * @return
	 */
	List<SensorDetailsResponse> generateSensorDetailsResponse(Map<String,List<SensorData>> sensorIdMap,SensorDetailsRequest sensorDetailRequest){
		List<SensorDetailsResponse> sensorDetailsResponseList = new ArrayList<>();
		sensorIdMap.values().forEach(sensorDetails->{
			sensorDetailRequest.getMetricsList().forEach(metrics->{
				SensorDetailsResponse sensorDetailsResponse = calculateMetricsValue(sensorDetailRequest.getStatistic().name(),metrics.name(),sensorDetails);
				sensorDetailsResponseList.add(sensorDetailsResponse);
			});
		});

		return sensorDetailsResponseList;
	}
	/**
	 * 
	 * @param statistic
	 * @param metrics
	 * @param sensorDetails
	 * @return
	 */
	private SensorDetailsResponse calculateMetricsValue(String statistic, String metrics,List<SensorData> sensorDetails) {
		int value=0;
		SensorDetailsResponse sensorDetailsResponse = new SensorDetailsResponse();
		sensorDetailsResponse.setSensorId(sensorDetails.get(0).getSensorId());
		sensorDetailsResponse.setStatistic(statistic);

		switch (metrics.toUpperCase()) {
		case "TEMPERATURE":
			value = calculateStatisticValue(statistic,sensorDetails.stream().map(temp->temp.getTemperature()).collect(Collectors.toList()));
			sensorDetailsResponse.setMetricName(metrics);
			sensorDetailsResponse.setValue(value);
			break;
		case "HUMIDITY":
			value = calculateStatisticValue(statistic,sensorDetails.stream().map(temp->temp.getHumidity()).collect(Collectors.toList()));
			sensorDetailsResponse.setMetricName(metrics);
			sensorDetailsResponse.setValue(value);
			break;
		case "WINDSPEED" :
			value = calculateStatisticValue(statistic,sensorDetails.stream().map(temp->temp.getWindspeed()).collect(Collectors.toList()));
			sensorDetailsResponse.setMetricName(metrics);
			sensorDetailsResponse.setValue(value);
			break;
		}
		return sensorDetailsResponse;
	}
	/**
	 * 
	 * @param statistic
	 * @param value
	 * @return
	 */
	private Integer calculateStatisticValue(String statistic,List<Integer> value){
		switch (statistic.toUpperCase()) {
		case "MIN": return value.stream().min(Integer::compareTo).orElse(0);
		case "MAX": return value.stream().max(Integer::compareTo).orElse(0);
		case "SUM": return value.stream().mapToInt(Integer::intValue).sum();
		case "AVG": return (int)value.stream().mapToInt(Integer::intValue).average().orElse(0);
		}
		return 0;
	}



}
