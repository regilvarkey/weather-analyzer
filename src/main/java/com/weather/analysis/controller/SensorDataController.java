package com.weather.analysis.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.weather.analysis.exception.SensiorException;
import com.weather.analysis.request.SensorDataRequest;
import com.weather.analysis.request.SensorDetailsRequest;
import com.weather.analysis.request.StatisticType;
import com.weather.analysis.response.SensorDetailsResponse;
import com.weather.analysis.service.SensorDataService;
import jakarta.validation.Valid;

@RestController
public class SensorDataController {

	@Autowired
	private SensorDataService sensorDataService;

	@PostMapping("/createsensordata")
	public ResponseEntity<String> createSensorData(@Valid @RequestBody SensorDataRequest sensorDataRequest){
		String transactionId = UUID.randomUUID().toString();
		validateSensorDataInput(sensorDataRequest);
		String response = sensorDataService.createSensorData(sensorDataRequest);
		return ResponseEntity.ok(response);
	}

	private void validateSensorDataInput(SensorDataRequest sensorDataRequest){

		if(sensorDataRequest.getSensorId().isEmpty())
			throw new SensiorException("Sensor Id cannot be empty");
	}

	@PostMapping("/getdetails")
	public  ResponseEntity<List<SensorDetailsResponse>> getSensorDetails(@Valid @RequestBody SensorDetailsRequest sensorDetailRequest) {
		String transactionId = UUID.randomUUID().toString();
		validateSensorDetailsInput(sensorDetailRequest);
		List<SensorDetailsResponse> snsorDetailsResponseList = sensorDataService.getSensorDetails(sensorDetailRequest);
		return ResponseEntity.ok(snsorDetailsResponseList);
	}
	/**
	 * A date range (between one day and a month)
	 * @param sensorDetailsRequest
	 */
	private void validateSensorDetailsInput(SensorDetailsRequest sensorDetailsRequest){

		if(null!=sensorDetailsRequest.getFromDateTime() && null!=sensorDetailsRequest.getToDateTime() ) {
			if(sensorDetailsRequest.getFromDateTime().isAfter(sensorDetailsRequest.getToDateTime()))
				throw new SensiorException("From Date Time should be before To Date Time");
			LocalDateTime oneMonthAfterFromDateTime = sensorDetailsRequest.getFromDateTime().plusMonths(1);
			if(sensorDetailsRequest.getToDateTime().isAfter(oneMonthAfterFromDateTime))
				throw new SensiorException("Date range exceeds one month");
		}
           // if (!Arrays.asList(StatisticType.values()).contains(sensorDetailsRequest.getStatistic().name().toUpperCase())) {
            //	throw new SensiorException("Statistic value should be MIN or MAX or SUM or AVG");
          //  }
       
	}

}
