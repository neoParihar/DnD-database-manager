package server;

import shared.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

  public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
  private Socket socket;
  private ObjectInputStream inputStream;
  private ObjectOutputStream outputStream;

  public ClientHandler(Socket socket) {
    try {
      this.socket = socket;
      this.outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.flush();
      this.inputStream = new ObjectInputStream(socket.getInputStream());

      clientHandlers.add(this);
      System.out.println("ClientHandler created!");

    } catch (IOException e) {
      closeEverything();
    }
  }

  @Override
  public void run() {
    while (socket.isConnected()) {
      try {
        System.out.println("SERVER: waiting for request..");
        Request clientRequest = (Request) inputStream.readObject();
        System.out.println("SERVER: Received " + clientRequest.getRequestType() + " Request!");
        handleRequest(clientRequest);
      } catch (Exception e) {
        closeEverything();
        e.printStackTrace();
        break;
      }
    }
  }

  public void handleRequest(Request request) throws IOException {
    switch (request.getRequestType()) {
      case TEST:
        Request testComplete = new Request(RequestTypes.TEST, "Server Connected");
        outputStream.writeObject(testComplete);
        outputStream.flush();
        break;
      case DISCONNECT:
        this.removeClientHandler();
        this.closeEverything();
        break;
      default:
        System.out.println("Something was wrong with Request");
        break;
    }
  }

  /**
   * public void printToLog(String clientMessage) {
   * for (ClientHandler clientHandler : clientHandlers) {
   * try {
   * clientHandler.bufferedWriter.write(clientMessage);
   * clientHandler.bufferedWriter.newLine();
   * clientHandler.bufferedWriter.flush();
   * } catch (IOException e) {
   * closeEverything(socket, inputStream, bufferedWriter);
   * }
   * }
   * }
   **/
  public void removeClientHandler() {
    System.out.println("SERVER: Someone has left");
    clientHandlers.remove(this);
  }

  public void closeEverything() {
    removeClientHandler();
    try {
      if (inputStream != null) {
        inputStream.close();
      }
      if (outputStream != null) {
        outputStream.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
