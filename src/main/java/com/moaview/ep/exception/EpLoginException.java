package com.moaview.ep.exception;

public class EpLoginException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  private int errorCode = 403;
  private String errorMessage;
  private String messageCode;

  public EpLoginException()
  {
  }

  public EpLoginException(String messageCode, Exception exeception)
  {
    this(messageCode, null, exeception);
  }
  public EpLoginException(String messageCode, String errorMessage, Exception exeception) {
    super(errorMessage, exeception);
    this.messageCode = messageCode;
    this.errorMessage = errorMessage;
  }

  public EpLoginException(String s)
  {
    super(s);
  }

  public int getErrorCode() {
    return this.errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessageCode() {
    return this.messageCode;
  }

  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}