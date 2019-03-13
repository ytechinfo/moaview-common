package com.pub.core.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

public class EppltConfig extends EppltConfiguration
{
  private static long jdf_last_modified = 0L;

  protected static Properties props = null;

  private static String prop_file = "config/ep_config.properties";

  private EppltConfig() {
    try {
      props = new Properties();
      initialize();
    } catch (EppltConfigurationException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    System.out.println("asdfawefwef : " + getInstance().getProperty("runtime"));
  }

  public static EppltConfig getInstance()
  {
    return EpConfigHolder.instance;
  }

  protected void initialize() throws EppltConfigurationException {
    synchronized (lock) {
      File jdf_file = null;
      try {
        String configPropFile = getSystemProperty("pencake.config.path");

        if ((configPropFile != null) && (!"".equals(configPropFile))) {
          System.out.println("=========pencake config file load start==================");
          System.out.println("config.path : [" + configPropFile + "]");

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

          System.out.println("=========pencake config file load end==================");
        }

        if (jdf_file == null) {
          jdf_file = getJdfFile(prop_file);
        }

        if (!jdf_file.canRead()) {
          throw new EppltConfigurationException(
            getClass().getName() + " - Can't open jdf configuration file path: [" + prop_file + "]");
        }

        if (jdf_last_modified != jdf_file.lastModified()) {
          FileInputStream jdf_fin = new FileInputStream(jdf_file);
          props.load(new BufferedInputStream(jdf_fin));
          jdf_fin.close();
          jdf_last_modified = jdf_file.lastModified();
        }
      } catch (EppltConfigurationException e) {
        jdf_last_modified = 0L;
        throw new EppltConfigurationException(getClass().getName() + e.getMessage());
      } catch (Exception e) {
        jdf_last_modified = 0L;
        throw new EppltConfigurationException(
          getClass().getName() + " file: [" + jdf_file + "] msg : " + e.getLocalizedMessage() + "\n" + e.getMessage());
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

  public Properties getProperties() {
    try {
      initialize();
    } catch (EppltConfigurationException e) {
      e.printStackTrace();
    }
    return props;
  }

  public String getProperty(String key) {
    return getProperty(key, "");
  }
  public String getProperty(String key, String initVal) {
    return props.getProperty(key, initVal).trim();
  }

  public void reload() {
    try {
      initialize();
    } catch (EppltConfigurationException e) {
      e.printStackTrace();
    }
  }

  private static class EpConfigHolder
  {
    private static final EppltConfig instance = new EppltConfig();
  }
}