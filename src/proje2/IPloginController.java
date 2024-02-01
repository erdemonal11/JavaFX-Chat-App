//@author Erdem Onal
package proje2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IPloginController implements Initializable {

    @FXML
    private TextField txtIP;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnOk.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Portlogin.fxml"));

                Parent root = loader.load();
                PortloginController plc = loader.getController();
                if (!txtIP.getText().equals("")) {
                    plc.transferData(txtIP.getText());

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

}
