package client;

import server.Request;
import server.RequestTypes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client {
  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private String username;

  // TODO: Wrap constructor in a try-catch and close everything on error
  public Client() throws IOException {
    this.socket = new Socket("localhost", 1234);
    this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  public static void main(String[] args) throws IOException {
    new Client();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        intititateTestGUI();
      }
    });
  }

  public static void intititateTestGUI() {
    JPanel pane = new JPanel(new FlowLayout());
    JButton button = new JButton("Test");
    pane.add(button);

    JFrame window = new JFrame("Test");

  }

}
