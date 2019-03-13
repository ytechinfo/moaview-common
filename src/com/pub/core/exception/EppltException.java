package com.pub.core.exception;

public class EppltException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  private int errorCode;
  private String errorMessage;
  private String messageCode;

  public EppltException()
  {
  }

  public EppltException(int errorCode, Exception exeception)
  {
    this(errorCode, null, exeception);
  }

  public EppltException(int errorCode, String messageCode, Exception exeception) {
    this(errorCode, messageCode, null, exeception);
  }
  public EppltException(int errorCode, String messageCode, String errorMessage, Exception exeception) {
    super(errorMessage, exeception);
    this.errorCode = errorCode;
    this.messageCode = messageCode;
    this.errorMessage = errorMessage;
  }

  public EppltException(String s)
  {
    super(s);
  }

  public EppltException(String s, Exception exeception)
  {
    super(s, exeception);
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