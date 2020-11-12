/*
Course:				CP1340 - Object Oriented Programming
Project:			Term Project - The Weather App
File:				XMLParser.java
Description:		XMLParser Class that parses the XML source file.
					Creates a Forecast ArrayList object to store the values.
Date:				November 12, 2020
Name:				OSCAR LOZANO-PEREZ
Student Number:		20164974
*/

import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import java.util.ArrayList;

public class XMLParser{

	private String xmlSource;

	public XMLParser(String xmlSource){
		this.xmlSource = xmlSource;
	}

	public static ArrayList<Forecast> parse(String xmlSource) throws Exception{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));
		doc.getDocumentElement().normalize();

		NodeList list;
		Node node;
		Element e;

		list = doc.getElementsByTagName("forecast");
		ArrayList<Forecast> forecastList = new ArrayList<Forecast>();

		list = doc.getElementsByTagName("currentConditions");
		node = list.item(0);
		e = (Element) node;
		String cTemp = e.getElementsByTagName("temperature").item(0).getTextContent();
		String cIcon = e.getElementsByTagName("iconCode").item(0).getTextContent();
		String cCond = e.getElementsByTagName("condition").item(0).getTextContent();
		StringBuffer csb = new StringBuffer(cIcon);
		csb.append(".gif");
		Forecast current = new Forecast();
		current.setTemperature(cTemp);
		current.setIcon(csb.toString());
		current.setCondition(cCond);
	
		forecastList.add(current);

		list = doc.getElementsByTagName("forecast");

		for(int i = 0; i < list.getLength(); i++){
			node = list.item(i);
			e = (Element) node;

			Forecast f = new Forecast();

			String day = e.getElementsByTagName("period").item(0).getTextContent();
			String icon = e.getElementsByTagName("iconCode").item(0).getTextContent();
			StringBuffer sb = new StringBuffer(icon);
			sb.append(".gif");

			String temperature = e.getElementsByTagName("temperature").item(0).getTextContent();

			switch (day) {
				case "Monday":
				case "Tuesday":
				case "Wednesday":
				case "Thursday":
				//case "Wednesday night":
				case "Friday":
				case "Saturday":
				case "Sunday":				
					f.setDay(day);
					f.setIcon(sb.toString());
					f.setTemperature(temperature);
					forecastList.add(f);
			}
		}
		return forecastList;
	}
}