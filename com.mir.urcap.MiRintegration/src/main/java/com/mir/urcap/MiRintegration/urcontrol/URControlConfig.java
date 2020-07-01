package com.mir.urcap.MiRintegration.urcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class URControlConfig {
	
	private static final String CONFIG_FILE = "/root/.urcontrol/urcontrol.conf";
	private static final String POWER_SUPPLY_KEY = "power_supply";
	
	private static void changeProperty(String filename, String key, String value) throws IOException {
	    final File tmpFile = new File(filename + ".tmp");
	    final File file = new File(filename);
	    PrintWriter printWriter = new PrintWriter(tmpFile);
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
	    boolean found = false;
	    final String toAdd = key + " = " + value;
	    for (String line; (line = bufferedReader.readLine()) != null; ) {
	        if (line.startsWith(key + "=")||line.startsWith(key + " ")) {
	            line = toAdd;
	            found = true;
	        }
	        printWriter.println(line);
	    }
	    if (!found)
	        printWriter.println(toAdd);
	    bufferedReader.close();
	    printWriter.close();
	    tmpFile.renameTo(file);
	}

	private static String getProperty(String filename, String key) throws IOException {
			Properties prop = new Properties();
			InputStream inputStream = new FileInputStream(filename);
			prop.load(inputStream);
			return prop.getProperty(key);
	}	

	private static void restart() throws IOException {
		Runtime.getRuntime().exec("pkill java");
	}

	public static boolean setPowerSupplyLimit(int maxPower) {
		boolean returnValue = true;
	
		try {
			String powerString = getProperty(CONFIG_FILE, POWER_SUPPLY_KEY);
			if(Math.round(Double.parseDouble(powerString)) != maxPower) {
				changeProperty(CONFIG_FILE, POWER_SUPPLY_KEY, Integer.toString(maxPower));
				restart();
			}			

		} catch (IOException e) {
			returnValue = false;
		}

	
		return returnValue;
	}

 
	 
	public static void main(String[] args) throws IOException {
		System.out.println(getProperty(CONFIG_FILE,POWER_SUPPLY_KEY));
		setPowerSupplyLimit(480);
	}	



}
