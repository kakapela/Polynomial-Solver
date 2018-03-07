package sample;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
//it shows full results window
public class IterationsController implements Initializable {

    @FXML
    private JFXTextArea textArea;

    @FXML
    private Label iterationLabel;

    @FXML
    private Label rootLabel;

    @FXML
    private Label errorLabel;
    NewtonRaphson newtonRaphson = NewtonRaphson.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textArea.appendText(newtonRaphson.getFullInfo());
        errorLabel.setText(String.valueOf(newtonRaphson.getError()));
        iterationLabel.setText(String.valueOf(newtonRaphson.getIteration()));
        rootLabel.setText(String.valueOf(newtonRaphson.getRoot()));

    }
}
