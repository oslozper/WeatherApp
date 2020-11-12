/*
Course:				CP1340 - Object Oriented Programming
Project:			Term Project - The Weather App
File:				TestConnector.java
Description:		Driver file to test the model part of the project.
Date:				November 12, 2020
Name:				OSCAR LOZANO-PEREZ
Student Number:		20164974
*/

import java.util.*;

public class TestConnector{
	public static void main(String[] args) throws Exception{
		String url = "https://dd.weather.gc.ca/citypage_weather/xml/NL/s0000280_e.xml";

		DataModel dm = new DataModel(url);
		ArrayList<Forecast> f = dm.getData();

		Forecast temp = f.get(0);
		System.out.println("Current Temperature: " + temp.getTemperature());
		for(int i = 1; i < f.size(); i++){
			temp = f.get(i);
			System.out.println(temp.getDay() + " " + temp.getIcon() + " " + temp.getTemperature());
		}
	}
}