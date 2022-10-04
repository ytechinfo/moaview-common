package com.moaview.ep.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Properties;


/**
* @fileName	: EpConfig.java
* @desc		: ep_config factory
* @author	: ytkim
 */
public class EpConfig {

	protected Object lock = new Object();

	private long jdf_last_modified = 0L;

	protected Properties props = null;

	private String prop_file = "config/ep_config.properties";
	
	private String charSet= Charset.defaultCharset().displayName();

	private EpConfig() {
		try {
			props = new Properties();
			initialize();
		} catch (EpConfigException e) {
			e.printStackTrace();
		}
	}

	public static EpConfig getInstance() {
		return EpConfigHolder.instance;
	}

	protected void initialize() throws EpConfigException {
		synchronized (lock) {
			File jdf_file = null;
			try {
				String configPropFile = getSystemProperty("moaview.config.path");
				
				System.out.println("=========moaview config file load start==================");
				System.out.println("moaview.config.path : [" + configPropFile + "] default path : ["+ prop_file+"]");

				if ((configPropFile != null) && (!"".equals(configPropFile))) {

					if (configPropFile.startsWith("classpath:")) {
						configPropFile = configPropFile.replaceFirst("classpath:", "");
						jdf_file = getJdfFile(configPropFile);
					} else {
						jdf_file = new File(configPropFile);
					}

					System.out.println("path : [" + jdf_file + "]");
					if (!jdf_file.canRead()) {
						System.out.println("Can't open jdf configuration file path : " + configPropFile);
						jdf_file = null;
					}

					System.out.println("=========moaview config file load end==================");
				}

				if (jdf_file == null) {
					jdf_file = getJdfFile(prop_file);
				}

				if (!jdf_file.canRead()) {
					throw new EpConfigException(
							getClass().getName() + " - Can't open jdf configuration file path: [" + prop_file + "]");
				}

				if (jdf_last_modified != jdf_file.lastModified()) {
					FileInputStream jdf_fin = new FileInputStream(jdf_file);
					props.load(new BufferedInputStream(jdf_fin));
					jdf_fin.close();
					jdf_last_modified = jdf_file.lastModified();
				}
				
				charSet =getProperty("charset","UTF-8");
			} catch (Exception e) {
				jdf_last_modified = 0L;
				System.err.println(getClass().getName() + " file: [" + jdf_file + "] msg : "+ e.getMessage()); 
			}
		}
	}

	private File getJdfFile(String prop_file) throws UnsupportedEncodingException {
		URL url = Thread.currentThread().getContextClassLoader().getResource(prop_file);
		return new File(URLDecoder.decode(url.getPath(), "utf-8"));
	}

	private String getSystemProperty(String key) {
		return System.getProperty(key);
	}

	public String viewAllProperty() {
		return props.toString();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return getProperty(key, "");
	}
	
	/**
	 * get property value 
	 * @param key property key
	 * @param initVal default value
	 * @return
	 */
	public String getProperty(String key, String initVal) {
		return props.getProperty(key, initVal).trim();
	}
	
	/**
	 * get int property 
	 * @param key
	 * @return
	 */
	public int getIntProperty(String key) {
		return getIntProperty(key,-1);
	}
	
	/**
	 * get int property 
	 * @param key property key
	 * @param initVal default value
	 * @return
	 */
	public int getIntProperty(String key, int initVal) {
		return Integer.parseInt(getProperty(key, initVal+""));
	}
	
	/**
	 * get array property split char ","
	 * @param key
	 * @return
	 */
	public String[] getArrayProperty(String key) {
		return getArrayProperty(key,"");
	}
	
	/**
	 * get array property split char ","
	 * @param key
	 * @param initVal
	 * @return
	 */
	public String[] getArrayProperty(String key, String initVal) {
		return getProperty(key, initVal).split(",");
	}

	public void reload() {
		try {
			initialize();
		} catch (EpConfigException e) {
			e.printStackTrace();
		}
	}

	public String getCharSet() {
		return charSet;
	}

	private static class EpConfigHolder {
		private static final EpConfig instance = new EpConfig();
	}
}