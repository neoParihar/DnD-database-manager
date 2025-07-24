package client;

import java.net.Socket;

import javax.swing.*;
import java.awt.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Test {
  private Socket socket;
  private BufferedWriter bufferedWriter;

  public Test(Socket socket) throws IOException {
    this.socket = socket;
    this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    JFrame frame = new JFrame("Test Panel");
    JButton sendButton = new JButton("Send Test Request");
    sendButton.addActionListener(e -> {
      bufferedWriter.write("TEST_REQUEST");
    });

    frame.setLayout(new FlowLayout());
    frame.add(sendButton);
    frame.setSize(300, 100);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
  }

}
