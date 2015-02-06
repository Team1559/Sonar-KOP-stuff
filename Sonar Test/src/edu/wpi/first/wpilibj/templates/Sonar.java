package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;

public class Sonar {

	AnalogChannel sensor;
	double conversionFactor;
	double dist;

	public Sonar(int channel) {
		sensor = new AnalogChannel(channel);
		conversionFactor = 9.3;
	}

	public double getInches() {
		return ((sensor.getVoltage() * conversionFactor / 12));
	}

	public double getFeet() {
		return ((sensor.getVoltage() * conversionFactor));
	}

	public double getVoltage() {
		return (sensor.getVoltage());
	}
}