package server;

public class Request {

  protected RequestTypes requestType;
  protected String message;

  public Request(RequestTypes requestType) {
    this.requestType = requestType;
  }

  public Request(RequestTypes requestType, String message) {
    this.requestType = requestType;
    this.message = message;
  }

  public RequestTypes getRequestType() {
    return requestType;
  }

  public void setRequestType(RequestTypes requestType) {
    this.requestType = requestType;
  }

  public String getMessage() {
    return message;
  }

}
