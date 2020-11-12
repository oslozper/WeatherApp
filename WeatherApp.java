/*
Course:				CP1340 - Object Oriented Programming
Project:			Term Project - The Weather App
File:				WeatherApp.java
Description:		Driver file to run the Weather GUI Application
					View/Controller part of Project
Date:				November 12, 2020
Name:				OSCAR LOZANO-PEREZ
Student Number:		20164974
*/

// Regular GUI Imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// Required image imports
import java.awt.image.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

// For Array lists
import java.util.*;

// Date import
import java.util.Date;
import java.text.SimpleDateFormat;

// Used for JSlider Labels
import java.util.Hashtable;




public class WeatherApp extends JFrame{
	// JFrame and ImageIcon for setting application icon
	private JFrame weatherAppFrame;
	private ImageIcon weatherAppIcon;

	// Stores forecast from DataModel
	private ArrayList<Forecast> f;
	// stores size of Array list f
	private int size;

	// String arrays storing data from array list of forecasts f
	private String[] days;
	private String[] icons;
	private String[] temperatures;

	// Default NL City
	private String city = "St.John's";

	// URL root address where NL XMLs are stored
	private String urlPrefix = "https://dd.weather.gc.ca/citypage_weather/xml/NL/";

	// XML forecast file for default city
	private String xmlLoc = "s0000280_e.xml";

	// Appends xmlLoc to urlPrefix
	private String url;

	// String variables storing values displayed in top panel
	private String currentTemperature;
	private String currentIcon;
	private String currentDate;
	private String currentCondition;

	// JPanels containing the App layout
	private JPanel top;
	private JPanel center;
	private JPanel bottom;

	// Current Condition Labels
	private JLabel cityLabel;
	private JLabel temperatureLabel;
	private JLabel iconLabel;
	private JLabel conditionLabel;
	private JLabel dateLabel;

	// Upcoming weather labels
	private JLabel[] daysLabels;
	private JLabel[] iconsLabels;
	private JLabel[] temperatureLabels;

	// Units radiobuttons
	private JRadioButton celsiusRButton;
	private JRadioButton farenheitRButton;
	private ButtonGroup unitsGroup;

	// Units Slider
	private JSlider unitsSlider;

	// Unit variable
	private boolean isCelsius = true;

	// Change lock due to the nature of JSLider setting values twice
	private boolean unitsChanged = false;

	// Main Method creating an App object
	public static void main(String[] args) throws Exception{
		WeatherApp wa = new WeatherApp();
	}

	// Constructor seting the default settings
	public WeatherApp() throws Exception{
		// Sets the application title
		super("Weather App");

		// Sets the application icon
		weatherAppIcon = new ImageIcon("Images/01.gif");
		setIconImage(weatherAppIcon.getImage());

		// Sets the date
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE', 'MMMM d");
		Date date = new Date();
		currentDate = formatter.format(date);

		// Initializes the application
		getData();
		buildGUI();
		setData();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	// Method creating objects to populate data
	public void getData() throws Exception{
		url = urlPrefix+ xmlLoc;
		DataModel dataModel = new DataModel(url);
		ArrayList<Forecast> forecastAL = dataModel.getData();
		size = forecastAL.size();

		// Values stored in the global variables
		days = new String[forecastAL.size()-1];
		icons = new String[forecastAL.size()-1];
		temperatures = new String[forecastAL.size()-1];

		Forecast temp = forecastAL.get(0);
		currentTemperature = temp.getTemperature();
		currentIcon = "Images/" + temp.getIcon();
		currentCondition = temp.getCondition();
		for(int i = 1; i < size; i++){
			temp = forecastAL.get(i);
			days[i-1] = temp.getDay();
			icons[i-1] = "Images/" + temp.getIcon();
			temperatures[i-1] = temp.getTemperature();
		}
	}

	// Method contructing the App Layout
	private void buildGUI() throws Exception{

		// Window Container creation
		Container c = getContentPane();
		c.setLayout(new BorderLayout());


		// NORTH section of the APP
		top = new JPanel();

		top.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 10, 10);

		// City Label
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;

		cityLabel = new JLabel();
		cityLabel.setFont(cityLabel.getFont().deriveFont(25.0f));
		top.add(cityLabel, gbc);

		// Current Condition Icon Label
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		iconLabel = new JLabel();
		iconLabel.setFont(iconLabel.getFont().deriveFont(36.0f));
		top.add(iconLabel, gbc);

		// Current Temperature Label
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		temperatureLabel = new JLabel();
		temperatureLabel.setFont(temperatureLabel.getFont().deriveFont(40.0f));
		top.add(temperatureLabel, gbc);

		// Condition Label
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;

		conditionLabel = new JLabel();
		conditionLabel.setFont(conditionLabel.getFont().deriveFont(20.0f));
		top.add(conditionLabel, gbc);

		// Date Label
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;

		dateLabel = new JLabel(currentDate);
		dateLabel.setFont(dateLabel.getFont().deriveFont(16.0f));
		top.add(dateLabel, gbc);

		// Units Slider
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;

		unitsSlider = new JSlider(0,1);
		unitsSlider.setPaintLabels(true);
		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
		table.put (0, new JLabel("\u00B0C"));
	    table.put (1, new JLabel("\u00B0F"));
    	unitsSlider.setLabelTable (table);
    	unitsSlider.setPreferredSize(new Dimension(50,30));
    	SliderListener sll = new SliderListener();
    	unitsSlider.addChangeListener(sll);
    	top.add(unitsSlider, gbc);

		c.add(top, BorderLayout.NORTH);

		// CENTER Section Separator
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		c.add(sep, BorderLayout.CENTER);

		// SOUTH section of the app
		bottom = new JPanel();
		bottom.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(10,10,10,10);

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;

		daysLabels = new JLabel[size];
		iconsLabels = new JLabel[size];
		temperatureLabels = new JLabel[size];

		// Creates programatically the labels for the days of the week
		for(int i = 0; i < size; i++){
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.WEST;
			gc.gridy = i;
			gc.gridwidth = 1;
			gc.gridheight = 1;
			daysLabels[i] = new JLabel();
			daysLabels[i].setFont(daysLabels[i].getFont().deriveFont(12.0f));
			bottom.add(daysLabels[i], gc);

			gc.gridx = 1;
			gc.anchor = GridBagConstraints.CENTER;
			iconsLabels[i] = new JLabel();
			iconsLabels[i].setFont(iconsLabels[i].getFont().deriveFont(12.0f));
			bottom.add(iconsLabels[i], gc);


			gc.gridx = 2;
			gc.anchor = GridBagConstraints.WEST;
			temperatureLabels[i] = new JLabel();
			temperatureLabels[i].setFont(temperatureLabels[i].getFont().deriveFont(12.0f));
			bottom.add(temperatureLabels[i], gc);			
		}

		// Creates the options in the ComboBox
		gc.gridx = 1;
		gc.gridy = daysLabels.length-1;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.CENTER;
		String[] choices = { 
			"St. John\'s",
			"Happy Valley-Goose Bay", 
			"Labrador City"
		};
		JComboBox<String> cb = new JComboBox<String>(choices);
		ComboListener cbl = new ComboListener();
		cb.addItemListener(cbl);
		bottom.add(cb, gc);

		c.add(bottom, BorderLayout.SOUTH);
	}

	// Sets the forecast for the different cities
	private class ComboListener implements ItemListener {
        @Override
    	public void itemStateChanged(ItemEvent event)  {
       		if (event.getStateChange() == ItemEvent.SELECTED) {
       		
          		Object item = event.getItem();
          		city = item.toString();
          		unitsChanged = false;
          
          		switch(city) {
          			case "St. John\'s": 
          				city = "St. John\'s";
          				xmlLoc = "s0000280_e.xml";

          				break;
          			case "Happy Valley-Goose Bay":  
          				city = "Happy Valley-Goose Bay";
          				xmlLoc = "s0000780_e.xml"; 
          				break;
          			case "Labrador City": 
          				city = "Labrador City";
          				xmlLoc = "s0000737_e.xml";
          				break;
          			default: break;
          		}
      		    try {
      		    	getData();
        			setData();
    			}
    			catch (Exception e) {
        			System.out.println("Error while assigning city");
    			}          		
				
				
       		}
    	}  
	}


	// Sets the units in which data is presented
	public class SliderListener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent event){
			int value = unitsSlider.getValue();
			if(value == 0)
				isCelsius = true;
	
			else
				isCelsius = false;

			try {
				setData();
			}
			catch (Exception e) {
    			System.out.println("Error while setting data");
			}
		}
    }

	// Method filling the lables for display
	private void setData() throws Exception{

		// North part of the app data load.
		cityLabel.setText(city);

		// Loading data or converting depending in the units
		if(!isCelsius && !unitsChanged){
			convertTemperature();
			unitsChanged = true;
		}
		else if(!isCelsius && unitsChanged){

		}
		else if(isCelsius && unitsChanged){
			getData();
			unitsChanged = false;
		}
		else
			getData();

		temperatureLabel.setText(numberFormat(currentTemperature));

		BufferedImage image = ImageIO.read(new File(currentIcon));
		ImageIcon imageCurrentIcon = new ImageIcon(image);
		iconLabel.setIcon(imageCurrentIcon);

		conditionLabel.setText(currentCondition);

		BufferedImage[] images = new BufferedImage[size - 1];

		// Programatically filling the South part of the app.
		for(int i = 0; i < size-1; i++){
			daysLabels[i].setText(days[i]);

			images[i] = ImageIO.read(new File(icons[i]));
			ImageIcon imageIcon = new ImageIcon(images[i]);
			Image temp = imageIcon.getImage();
			Image newimg = temp.getScaledInstance(30, 25, java.awt.Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(newimg);

			iconsLabels[i].setIcon(imageIcon);
			temperatureLabels[i].setText(numberFormat(temperatures[i]));
		}
	}

	// Formula to change Celsius to Farenheit
	private double toFarenheit(double celsius){
		double farenheit = (1.8 * celsius) + 32;
		return farenheit;
	}

	// Method changing the celsius values to farenheit
	private void convertTemperature(){
		double cCelsius = 0;
		cCelsius = Double.parseDouble(currentTemperature);
		currentTemperature = String.valueOf(toFarenheit(cCelsius));
		for(int i = 0; i < temperatures.length; i++){
			cCelsius = Double.parseDouble(temperatures[i]);
			temperatures[i] = String.valueOf(toFarenheit(cCelsius));
		}
	}

	// Number formatting method to display degrees celsius of farenheit accordingly
	private String numberFormat(String number){
		float fNumber = Float.parseFloat(number);
		number = number + "";
		String outputNumber = String.format("%.1f", fNumber);
		if(isCelsius)
			outputNumber = outputNumber + "\u00B0C";
		else
			outputNumber = outputNumber + "\u00B0F";

		return outputNumber;
	}
}