package com.minsait.onesait.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "timestamp", "calculatedValue", "zoneId" })

public class FnEntityOutput {

	@JsonProperty("timestamp")
	private String timestamp;
	@JsonProperty("calculatedValue")
	private double calculatedValue;
	@JsonProperty("zoneId")
	private String zoneId;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public FnEntityOutput() {
	}

	/**
	 *
	 * @param zoneId
	 * @param calculatedValue
	 * @param timestamp
	 */
	public FnEntityOutput(String timestamp, double calculatedValue, String zoneId) {
		super();
		this.timestamp = timestamp;
		this.calculatedValue = calculatedValue;
		this.zoneId = zoneId;
	}

	@JsonProperty("timestamp")
	public String getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@JsonProperty("calculatedValue")
	public double getCalculatedValue() {
		return calculatedValue;
	}

	@JsonProperty("calculatedValue")
	public void setCalculatedValue(double calculatedValue) {
		this.calculatedValue = calculatedValue;
	}

	@JsonProperty("zoneId")
	public String getZoneId() {
		return zoneId;
	}

	@JsonProperty("zoneId")
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

}
