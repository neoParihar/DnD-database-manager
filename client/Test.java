package client;

import java.net.Socket;
import java.io.BufferedWriter;

public class Test {
  private Socket socket;
  private BufferedWriter bufferedWriter;

  public Test(Socket socket, BufferedWriter bufferedWriter) {
    this.socket = socket;
    this.bufferedWriter = bufferedWriter;
  }

}
