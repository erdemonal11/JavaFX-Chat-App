//@author Birkan Durgun
package proje2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ChatController implements Initializable {

    @FXML
    private TextArea taMessages;
    @FXML
    private TextField tfSendMessage;
    @FXML
    private ListView<String> lwClients;

    private static String senderUsername;

    private String ip;
    private int port;
    private String username;

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taMessages.setEditable(false);
    }

    private void sendReceive(String message) {
        try {
            toServer.writeUTF(ChatController.senderUsername + ": " + message);
            toServer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setConnection(String ip, String port, String username) {
        senderUsername = username;
        try {
            Socket s = new Socket(ip, Integer.parseInt(port));
            fromServer = new DataInputStream(s.getInputStream());
            toServer = new DataOutputStream(s.getOutputStream());
            lwClients.getItems().add(username);
            new Thread(() -> {
                while (true) {
                    try {
                        String receivedMessage = fromServer.readUTF();
                        taMessages.appendText(receivedMessage + "\n");
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                        break;
                    }
                }
            }).start();
        } catch (Exception ex) {

            Platform.runLater(() -> {

                Stage chatStage = (Stage) taMessages.getScene().getWindow();
                chatStage.close();
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("connError.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void enter() {
        String message = tfSendMessage.getText().trim();
        if (!message.isEmpty()) {
            sendReceive(message);
            tfSendMessage.clear();
        }
    }

}
