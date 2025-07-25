package server;

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
      this.inputStream = new ObjectInputStream(socket.getInputStream());
      this.outputStream = new ObjectOutputStream(socket.getOutputStream());

      clientHandlers.add(this);
      System.out.println("SERVER: Someone has joined the game!");

    } catch (IOException e) {
      closeEverything();
    }
  }

  @Override
  public void run() {
    while (socket.isConnected()) {
      try {
        Request clientRequest = (Request) inputStream.readObject();
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
      case DISCONNECT:
        this.removeClientHandler();
        this.closeEverything();
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
    clientHandlers.remove(this);
    System.out.println("SERVER: Someone has left");
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
