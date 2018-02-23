package gces;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author pradish
 */
public class VotingViewController implements Initializable, UserFoundListener{

    @FXML
    private Label barcode_label;

    @FXML
    private Label name_label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextViewController.setUserFoundListener(this);
    }

    @Override
    public void onUserFound(User user, String userKey) {
        barcode_label.setText(Integer.toString(user.getBarCode()));
        name_label.setText(user.getName());
    }
}
