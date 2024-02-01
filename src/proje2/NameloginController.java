//@author Birkan Durgun
package proje2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NameloginController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    private String ip;
    private String port;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnOk.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("chat.fxml"));

                Parent root = loader.load();
                ChatController cc = loader.getController();
                if (!txtName.getText().equals("")) {
                    cc.setConnection(ip, port, txtName.getText());
                    Stage stage = new Stage();

                    stage.setMinHeight(450);
                    stage.setMinWidth(600);

                    stage.setScene(new Scene(root));
                    stage.show();
                }

            } catch (Exception exception) {
                System.out.println(exception.toString());
            }

            Stage stage = (Stage) btnOk.getScene().getWindow();
            stage.close();
        });

        btnCancel.setOnMouseClicked(e -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });

    }

    public void transferData(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

}
