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
        System.out.println("A new client has connected!");
        ClientHandler clientHandler = new ClientHandler(socket);

        new Thread(clientHandler);
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
    server.startServer();
  }
}
