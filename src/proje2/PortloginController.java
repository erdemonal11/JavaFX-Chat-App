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

public class PortloginController implements Initializable {

    @FXML
    private TextField txtPort;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    private String ip;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnOk.setOnMouseClicked(e -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Namelogin.fxml"));

                Parent root = loader.load();
                NameloginController nlc = loader.getController();
                if (!txtPort.getText().equals("")) {
                    nlc.transferData(ip, txtPort.getText());
                    Stage stage = new Stage();

                    stage.setMinHeight(401);
                    stage.setMinWidth(602);

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

    public void transferData(String data) {
        ip = data;
    }

}
