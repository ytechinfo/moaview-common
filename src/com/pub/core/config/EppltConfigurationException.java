package com.pub.core.config;

public class EppltConfigurationException extends Exception
{
  public EppltConfigurationException()
  {
  }

  public EppltConfigurationException(String s)
  {
    super(s);
  }

  public EppltConfigurationException(String s, Exception exeception) {
    super(s, exeception);
  }
}