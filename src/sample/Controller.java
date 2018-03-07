package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    @FXML
    private JFXTextField inputFunction;
    @FXML
    private Button btnOpenNewWindow;
    @FXML
    private LineChart<Double, Double> lineGraph;
    @FXML
    private AreaChart<Double, Double> areaGraph;
    @FXML
    private Button lineGraphButton;
    @FXML
    private Button areaGraphButton;
    @FXML
    private Button xyButton;
    @FXML
    private Button xyButton2;
    @FXML
    private Button squaredButton;
    @FXML
    private Button squaredButton2;
    @FXML
    private Button cubedButton;
    @FXML
    private Button cubedButton2;
    @FXML
    private Label infoLabel;

    @FXML
    private JFXTextField highestPow;

    @FXML
    private JFXTextField nextCoeff;
    private double result;
    NewtonRaphson newtonRaphson = NewtonRaphson.getInstance();

    @FXML
    private Button clearButton;
    private MyGraph mathsGraph;
    private MyGraph areaMathsGraph;


    public void initialize(final URL url, final ResourceBundle rb) {
        //create our graphs by giving type and range
        mathsGraph = new MyGraph(lineGraph, 10);
        areaMathsGraph = new MyGraph(areaGraph, 10);

        //open new window
        btnOpenNewWindow.setOnMouseClicked((event) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Newton_Raphson.fxml"));
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

    //set LineGraph
    @FXML
    public void handleLineGraphButtonAction(final ActionEvent event) {
        lineGraph.setVisible(true);
        areaGraph.setVisible(false);
    }

    //set AreaGraph
    @FXML
    public void handleAreaGraphButtonAction(final ActionEvent event) {
        areaGraph.setVisible(true);
        lineGraph.setVisible(false);
    }

    //create plot on the graph
    public void plotLine(Function<Double, Double> function) {
        if (lineGraph.isVisible()) {
            mathsGraph.plotLine(function);
        } else {
            areaMathsGraph.plotLine(function);
        }
    }

    //draw function
    @FXML
    public void draw() {

        String funkcja = inputFunction.getText();

        plotLine((Double x) -> {
            Expression e = new ExpressionBuilder(funkcja).variable("x").build();
            e.setVariable("x", x);
            result = e.evaluate();
            newtonRaphson.setPolynomial(funkcja);
            return result;
        });
    }


    @FXML
    public void handleClearButtonAction(final ActionEvent event) {
        clear();
    }

    public void clear() {
        if (lineGraph.isVisible()) {
            mathsGraph.clear();
        } else {
            areaMathsGraph.clear();
        }
    }


}
