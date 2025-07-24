package server;

public class Request {
  protected RequestTypes requestType;

  public Request(RequestTypes requestType) {
    this.requestType = requestType;
  }

  public RequestTypes getRequestType() {
    return requestType;
  }

  public void setRequestType(RequestTypes requestType) {
    this.requestType = requestType;
  }

}
