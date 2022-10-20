package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
	private String brand;
	private String color;

	public Car() {
		this.brand = "Default brand";
		this.color = "Default color";
	}

	public Car(String brand, String color) {
		this.brand = brand;
		this.color = color;

	}

	public void run() {
		System.out.println("Car is running!");
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
				.add("brand='" + brand + "'")
				.add("color=" + color)
				.toString();
	}
}
