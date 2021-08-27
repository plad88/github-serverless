package com.minsait.onesait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fnproject.fn.api.FnConfiguration;
import com.fnproject.fn.api.RuntimeContext;
import com.minsait.onesait.pojo.FnEntityOutput;
import com.minsait.onesait.pojo.FnEntityOutputWrapper;
import com.minsait.onesait.pojo.InputData;
import com.minsait.onesait.service.APIService;
import com.minsait.onesait.service.CalculationService;

public class OntologyCalculationFn {

	private String backend;

	private String apiKey;

	private static final Logger logger = LogManager.getLogger(OntologyCalculationFn.class);

	@FnConfiguration
	public void config(RuntimeContext ctx) {
		backend = ctx.getConfigurationByKey("BACKEND_SERVER")
				.orElseThrow(() -> new RuntimeException("No backend configured"));
		apiKey = ctx.getConfigurationByKey("API_KEY").orElseThrow(() -> new RuntimeException("No apikey configured"));

	}

	public InputData handleRequest(InputData input) {
		logger.info("New input data. Value: {} , zoneId: {}", input.getValue(), input.getZoneId());
		final APIService service = new APIService(apiKey, backend);
		final Double result = CalculationService.calculateOutput(input.getValue());
		final TimeZone tz = TimeZone.getTimeZone("UTC");
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(tz);
		final String nowAsISO = df.format(new Date());
		final FnEntityOutput output = new FnEntityOutput(nowAsISO, result, input.getZoneId());
		service.createEntry(new FnEntityOutputWrapper(output));

		return input;

	}

}