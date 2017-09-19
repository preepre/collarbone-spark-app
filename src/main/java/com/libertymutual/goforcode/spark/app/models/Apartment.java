package com.libertymutual.goforcode.spark.app.models;

import java.util.ArrayList;
import java.util.List;

public class Apartment {
	
	private int id;
	private int rent;
	
	private int numberOfBedrooms;
	private double numberOfBathrooms;
	private int squareFootage;
	private String address;
	private String city;
	private String state;
	private String zip;
	
	public Apartment() {}

	public Apartment(int id, int rent, int numberOfBedrooms, 
			double numberOfBathrooms, int squareFootage, 
			String address, String city, String state, String zip) {

		this.id = id;
		this.rent = rent;
		this.numberOfBedrooms = numberOfBedrooms;
		this.numberOfBathrooms = numberOfBathrooms;
		this.squareFootage = squareFootage;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}

	public void setNumberOfBedrooms(int numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}

	public double getNumberOfBathrooms() {
		return numberOfBathrooms;
	}

	public void setNumberOfBathrooms(double numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}

	public int getSquareFootage() {
		return squareFootage;
	}

	public void setSquareFootage(int squareFootage) {
		this.squareFootage = squareFootage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public static List<Apartment> findAll() {
		List<Apartment> apartments = new ArrayList<Apartment>();
		apartments.add(new Apartment(1, 6200, 1, 0, 350, "123 Main St", 
				"San Francisco", "CA", "95125"));
		apartments.add(new Apartment(2, 1400, 5, 6, 4000, "123 Cowboy Way", 
				"Houston", "CA", "77096"));
		return apartments;
	}
	
	//lists are 0 based so you want 1 less 
	public static Apartment findById(int id) {
		if(id == 1 || id == 2) {
			return findAll().get(id-1);
		}
		
		return null;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
