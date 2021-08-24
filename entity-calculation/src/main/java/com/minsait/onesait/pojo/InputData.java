package com.minsait.onesait.pojo;

public class InputData {

	private Double value;
	private String zoneId;

	public InputData() {

	}

	public InputData(Double value, String zoneId) {
		this.value = value;
		this.zoneId = zoneId;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

}
