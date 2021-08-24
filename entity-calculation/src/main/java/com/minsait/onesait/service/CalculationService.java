package com.minsait.onesait.service;

import java.util.Random;

public class CalculationService {

	public static double calculateOutput(Double value) {
		final double d = new Random().nextDouble();

		return Math.pow(value, d);
	}

}
