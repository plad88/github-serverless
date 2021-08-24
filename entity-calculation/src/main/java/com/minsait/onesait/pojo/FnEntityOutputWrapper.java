package com.minsait.onesait.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "FnEntityOutput" })
public class FnEntityOutputWrapper {

	@JsonProperty("FnEntityOutput")
	private FnEntityOutput fnEntityOutput;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public FnEntityOutputWrapper() {
	}

	/**
	 *
	 * @param fnEntityOutput
	 */
	public FnEntityOutputWrapper(FnEntityOutput fnEntityOutput) {
		super();
		this.fnEntityOutput = fnEntityOutput;
	}

	@JsonProperty("FnEntityOutput")
	public FnEntityOutput getFnEntityOutput() {
		return fnEntityOutput;
	}

	@JsonProperty("FnEntityOutput")
	public void setFnEntityOutput(FnEntityOutput fnEntityOutput) {
		this.fnEntityOutput = fnEntityOutput;
	}

}