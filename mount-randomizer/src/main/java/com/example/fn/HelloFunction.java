package com.example.fn;

import java.util.Random;

public class HelloFunction {

	public String handleRequest(String input) {
		final Integer total = (input == null || input.isEmpty()) ? 1000 : Integer.valueOf(input);
		final int drop = 17;

		boolean found = false;
		int iterations = 0;

		while (!found) {
			final Integer guessed = new Random().nextInt(total);
			if (guessed == drop) {
				found = true;
			}
			if (iterations >= total * 100) {
				found = true;
			}
			iterations++;
		}

		System.out.println("Mount drop after " + iterations + " tries.");
		return "Mount drop after " + iterations + " tries, with special number being: " + drop;
	}

}