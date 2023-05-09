package com.nashtech.tshape.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;



public class ConfigLoader {
    
    private static JSONObject environmentConfiguration;
    private static JSONObject frameworkConfiguration;
    
    private static void initConfig() throws FileNotFoundException, IOException {
        if(environmentConfiguration == null && frameworkConfiguration == null) {
        	readConfiguration();
        }
    }
    
    private static void readConfiguration() throws FileNotFoundException, IOException {
    	InputStream iev = ConfigLoader.class.getResourceAsStream("/environment.json");
        InputStream icf = ConfigLoader.class.getResourceAsStream("/config.json");
        
        environmentConfiguration = new JSONObject(new JSONTokener(iev));
        frameworkConfiguration = new JSONObject(new JSONTokener(icf));
        
        String configEnv = System.getProperty("environment");
		if (configEnv != null) {
			environmentConfiguration = environmentConfiguration.getJSONObject(configEnv);
		}else {
			String env = environmentConfiguration.getString("environment");
			environmentConfiguration = environmentConfiguration.getJSONObject(env);
		}
        
        
        System.out.println("test env: " + environmentConfiguration.getString("url"));
    }
    
    public static String getEnv(String key) throws FileNotFoundException, IOException {
    	initConfig();
    	String config = System.getProperty(key);
		if (config != null) {
			return config;
		}
		String a = environmentConfiguration.getString(key);
		return a;
    }
    
    public static String getConfig(String key) throws FileNotFoundException, IOException {
    	initConfig();
    	String config = System.getProperty(key);
		if (config != null) {
			return config;
		}
		String a = frameworkConfiguration.getString(key);
		return a;
    }

    
    

    
}