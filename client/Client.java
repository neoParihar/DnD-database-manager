package client;

import server.Request;
import server.RequestTypes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {
  private Socket socket;
  private ObjectInputStream inputStream;
  private ObjectOutputStream outputStream;

  private Test testGUI;

  public Client() {
    try {
      this.socket = new Socket("localhost", 1234);
      this.outputStream = new ObjectOutputStream(socket.getOutputStream());
      this.inputStream = new ObjectInputStream(socket.getInputStream());
      this.testGUI = new Test(this);
    } catch (IOException e) {
      closeEverything();
    }
  }

  @Override
  public void run() {
    try {
      while (socket.isConnected()) {
        Request serverRequest = (Request) inputStream.readObject();
        handleRequest(serverRequest);
      }
    } catch (Exception e) {
      closeEverything();
    }
  }

  public static void main() throws IOException {
    new Client();
  }

  public void handleRequest(Request request) {
    switch (request.getRequestType()) {
      case TEST:
        testGUI.setlabel(request.getMessage());
      default:

    }
  }

  public void closeEverything() {
    sendRequest(new Request(RequestTypes.DISCONNECT));
    try {
      if (inputStream != null)
        inputStream.close();
      if (outputStream != null)
        outputStream.close();
      if (socket != null)
        socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.exit(0);
    }
  }

  public void sendRequest(Request request) {
    try {
      outputStream.writeObject(request);
      outputStream.flush();
    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }

}
