package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private ServerSocket serverSocket;
  private static final int PORT = 1234;

  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  public void startServer() {
    try {
      while (!serverSocket.isClosed()) {
        Socket socket = serverSocket.accept();
        System.out.println("SERVER: Someone has joined the game!");

        Thread newHandler = new Thread(new ClientHandler(socket));
        newHandler.start();
      }
    } catch (IOException e) {
      closeServer();
    }
  }

  public void closeServer() {
    try {
      if (serverSocket != null) {
        serverSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(PORT);
    Server server = new Server(serverSocket);
    System.out.println("Server is listening..");
    server.startServer();
  }
}
