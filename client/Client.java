package client;

import shared.*;
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
      outputStream.flush();
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

  public static void main(String[] args) throws IOException {
    Client client = new Client();
    Thread clientThread = new Thread(client);
    clientThread.start();
  }

  public void handleRequest(Request request) {
    switch (request.getRequestType()) {
      case TEST:
        testGUI.setlabel(request.getMessage());
        break;
      default:
        System.out.println("Server sent a bad request");
        break;
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
      System.out.println("CLIENT: Sending request " + request.getRequestType());
      outputStream.writeObject(request);
      outputStream.flush();
    } catch (Exception ie) {
      ie.printStackTrace();
    }

  }

}
