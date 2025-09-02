package client;

import javax.swing.*;

import shared.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Test implements WindowListener, ActionListener {
  private JFrame frame;
  private JButton sendButton;
  private JLabel label;
  private Client connection;

  public Test(Client connection) {
    this.connection = connection;
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        GUI();
      }
    });
  }

  public void GUI() {
    this.frame = new JFrame("Test Panel");
    this.sendButton = new JButton("Send Test Request");
    this.label = new JLabel("");

    sendButton.addActionListener(this);

    frame.setLayout(new FlowLayout());
    frame.add(sendButton);
    frame.add(label);
    frame.setSize(300, 100);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
    frame.addWindowListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Request request = new Request(RequestTypes.TEST);
    connection.sendRequest(request);
  }

  @Override
  public void windowClosing(WindowEvent we) {
    connection.closeEverything();
    frame.dispose();
  }

  @Override
  public void windowClosed(WindowEvent e) {
  }

  public void setlabel(String message) {
    label.setText(message);
  }

  @Override
  public void windowActivated(WindowEvent e) {
  }

  @Override
  public void windowDeactivated(WindowEvent e) {
  }

  @Override
  public void windowDeiconified(WindowEvent e) {
  }

  @Override
  public void windowIconified(WindowEvent e) {
  }

  @Override
  public void windowOpened(WindowEvent e) {
  }

}
