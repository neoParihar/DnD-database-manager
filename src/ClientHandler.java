import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

  public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private String clientUsername;

  public ClientHandler(Socket socket) {
    try {
      this.socket = socket;
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.clientUsername = bufferedReader.readLine();
      clientHandler.add(this);
      printToLog("DM: " + clientUsername + " has joined the game!");
    } catch (IOException e) {
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }

  @Override
  public void run() {
    String clientMessage;

    while (socket.isConnected()) {
      try {
        clientMessage = bufferedReader.readLine();
        printToLog(clientMessage);
      } catch (IOException e) {
        closeEverythig(socket, bufferedReader, bufferedWriter);
        break;
      }
    }
  }

  public void printToLog(String clientMessage) {
    for (ClientHandler clientHandler : clientHandlers) {
      try {
        clientHandler.bufferedWriter.write(clientMessage)
        clientHandler.bufferedWriter.newline();
        clientHandler.bufferedWriter.flush();
      } catch (IOException e) {
          closeEverything(socket, bufferedReader, bufferedWriter);
      }
    }
  }

  public void removeClientHandler() {
    clientHandlers.remove(this);
    printToLog("DM: " + clientUsername + " has left the game.");
  }

  public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
    removeClientHandler();
    try {
      if (bufferedReader != null) {
        bufferedReader.close();
      }
      if (bufferedWriter != null) {
        bufferedWriter.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
