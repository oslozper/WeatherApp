/*
Course:				CP1340 - Object Oriented Programming
Project:			Term Project - The Weather App
File:				Forecast.java
Description:		Forecast class that sets and gets the forecast values.
Date:				November 12, 2020
Name:				OSCAR LOZANO-PEREZ
Student Number:		20164974
*/

public class Forecast{
	private String day;
	private String icon;
	private String temperature;
	private String condition;

	// Constructor that creates an object with empty variables
	public Forecast(){

	}

	// setDay method to store the day
	public void setDay(String day){
		this.day = day;
	}

	// setDay method to return the day
	public String getDay(){
		return day;
	}

	// setIcon method to store the icon
	public void setIcon(String icon){
		this.icon = icon;
	}

	// getIcon method to return the icon
	public String getIcon(){
		return icon;
	}

	// setTemperature method to set the temperature
	public void setTemperature(String temperature){
		this.temperature = temperature;
	}

	// getTemperature method to return the temperature
	public String getTemperature(){
		return temperature;
	}

	// setCondition method to set the condition Description
	public void setCondition(String condition){
		this.condition = condition;
	}

	// getCondition method to return the condition Description
	public String getCondition(){
		return condition;
	}
}