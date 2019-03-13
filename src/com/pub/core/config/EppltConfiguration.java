package com.pub.core.config;

import java.util.Properties;

public abstract class EppltConfiguration
{
  protected static Object lock = new Object();

  public abstract Properties getProperties();
}