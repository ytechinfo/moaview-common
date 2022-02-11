package com.moaview.ep.config;

public class EpConfigException extends Exception
{
  public EpConfigException()
  {
  }

  public EpConfigException(String s)
  {
    super(s);
  }

  public EpConfigException(String s, Exception exeception) {
    super(s, exeception);
  }
}