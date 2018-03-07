package sample;

//3x^3-2x2+4
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Newton_Raphson_Controller implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        goToAllIterations.setOnMouseClicked((event) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("IterationsView.fxml"));
        /*
         * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
         */
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("New Window");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        });

    }

    @FXML
    private JFXButton solveBtn;
    @FXML
    private Label label0;

    @FXML
    private Label label3;
    @FXML
    private JFXButton goToAllIterations;

    @FXML
    private JFXTextField degreeInput;

    @FXML
    private Label label1;

    @FXML
    private Label iterationLabel;

    @FXML
    private Label rootLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXTextField coefficients;

    @FXML
    private FontAwesomeIconView check1;

    @FXML
    private FontAwesomeIconView check2;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton addX0;

    @FXML
    private FontAwesomeIconView check3;

    @FXML
    private JFXTextField x0;

    double[] C;
    private double x0_value;
    int counter = 0;
    int degree;
    private double F,F_prime;

    NewtonRaphson newtonRaphson= NewtonRaphson.getInstance();

    @FXML
    public void save(MouseEvent event) {


       degree= Integer.parseInt(degreeInput.getText()) ;
       check1.setVisible(true);
       label1.setText("Enter "+(degree+1-counter)+" coefficients in descending order: ");
       label1.setVisible(true);
       addBtn.setVisible(true);
       coefficients.setVisible(true);

       //array for store coefficients
        C = new double[degree+1];

        degreeInput.setText("");

        label0.setText("Degree is set!");
        label1.setVisible(true);
        coefficients.setVisible(true);
        addBtn.setVisible(true);



    }


    @FXML
    public void add(MouseEvent event) {
        double coefficient =Double.parseDouble(coefficients.getText());
        C[counter]=coefficient;
        counter++;



        label1.setText("Enter "+(degree+1-counter)+" coefficients in descending order: ");
        if(counter==degree+1){
            check2.setVisible(true);
            label1.setText("Done! All coefficients has been loaded!");
            label3.setVisible(true);
            x0.setVisible(true);
            addX0.setVisible(true);


            //check array
                for(int i=0;i<C.length;i++)
                    System.out.println("Wspolczynniki przy iksach wynosza odpowiednio "+C[i]);


        }

        coefficients.setText("");

    }
    @FXML
    public void solve(MouseEvent event) {

        //find derivatives
        newtonRaphson.findDerivative(C);
        double D[] = newtonRaphson.getDerivatives();
        for(int i=0;i<D.length;i++)
            System.out.println("Pochodne wspolczynnikow przy x wynosza odpowiednio "+D[i]+". Ilosc wspolczynnikow: "+D.length);


        F= newtonRaphson.F(x0_value,newtonRaphson.getPolynomial());
        F_prime=newtonRaphson.F_prime(x0_value,newtonRaphson.getWzorPochodnej());
        System.out.println("Wynik funkcji F="+newtonRaphson.getPolynomial()+" dla x= "+x0_value+" wynosi "+F+". Wynik pochodnej funkcji F wynosi " +F_prime);


        newtonRaphson.solvePolynomial(x0_value,newtonRaphson.getPolynomial(),newtonRaphson.getWzorPochodnej());
        errorLabel.setText(String.valueOf(newtonRaphson.getError()));
        iterationLabel.setText(String.valueOf(newtonRaphson.getIteration()));
        rootLabel.setText(String.valueOf(newtonRaphson.getRoot()));



    }
    @FXML
    public void addX0(MouseEvent event) {
        x0_value=Double.parseDouble(x0.getText());
        check3.setVisible(true);
        solveBtn.setVisible(true);

        label3.setText("Value X0 is set!");
        x0.setText("");

    }



}
