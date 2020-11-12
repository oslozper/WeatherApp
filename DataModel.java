/*
Course:				CP1340 - Object Oriented Programming
Project:			Term Project - The Weather App
File:				DataModel.java
Description:		Class that ties the connector and xmlparser objects 
					together.
Date:				November 12, 2020
Name:				OSCAR LOZANO-PEREZ
Student Number:		20164974
*/

import java.util.ArrayList;

public class DataModel{
	private String url;

	// Constructor that brings a url address
	public DataModel(String url){
		this.url = url;
	}

	// Method that creates Connectorm, XMLParser objects and the source file address.
	// Returns the parsed data from the source file.
	public ArrayList<Forecast> getData() throws Exception{
		Connector aConnector = new Connector(url);
		String xmlSource = aConnector.getContent();
		XMLParser xmlParse = new XMLParser(xmlSource);

		return xmlParse.parse(xmlSource);

	}
}