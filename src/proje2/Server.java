//@author Birkan Durgun
package proje2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Application {

    private Label lblServer;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("server.fxml"));

        Parent root = loader.load();

        lblServer = (Label) loader.getNamespace().get("lblServer");

        Scene scene = new Scene(root);
        
        stage.setResizable(false);
        
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();

        new Thread(new MultiThreadServer(lblServer)).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class MultiThreadServer implements Runnable {

    private Label lblServer;
    private List<HandleAClient> connectedClients = new ArrayList<>();

    public MultiThreadServer(Label lblServer) {
        this.lblServer = lblServer;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(12000);

            Platform.runLater(() -> {
                lblServer.setText("Server is running on port " + serverSocket.getLocalPort());
            });

            while (true) {
                Socket s = serverSocket.accept();
                HandleAClient task = new HandleAClient(s);
                connectedClients.add(task);
                new Thread(task).start();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    class HandleAClient implements Runnable {
        
        private Socket socket; 

        public HandleAClient(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            
            try {
                
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient;
                while (true) {
                    String message = inputFromClient.readUTF();

                    for (HandleAClient client : connectedClients) {
                        if (!client.socket.isClosed()) {
                            outputToClient = new DataOutputStream(client.socket.getOutputStream());
                            outputToClient.writeUTF(message);
                            outputToClient.flush();
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e);
                connectedClients.remove(this);
            }
        }
    }
}
