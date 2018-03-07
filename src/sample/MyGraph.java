package sample;

import javafx.scene.chart.XYChart;

import java.util.function.Function;

//Klasa służąca do stworzenia wykresu
public class MyGraph {

    //obiekt typu wykres oraz zakres osi
    private XYChart<Double, Double> graph;
    private double range;

//powolanie do zycia obiektu typu wykres za pomoca podania typu wykresu oraz jego zasiegu
    public MyGraph(final XYChart<Double, Double> graph, final double range) {
        this.graph = graph;
        this.range = range;
    }

    //rysuj funkcje na podstawie funkcji podanej jako parametr (lambda)
    public void plotLine(final Function<Double, Double> function) {
        //stworz obiekt typu seria ktory zawiera czyli zbior punktow - plotted points podanej przez nas funkcji
        final XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();

        //rysuj kazdy punkt serii na calym zasiegu naszego wykresu, przesuwaj sie co 0.01
        for (double x = -range; x <= range; x = x + 0.01) {
            plotPoint(x, function.apply(x), series);
        }
        //dodaj do wykresu te serie
        graph.getData().add(series);
    }

    private void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        //pobierz do
        series.getData().add(new XYChart.Data<Double, Double>(x, y));
    }
    //wyczysc wykres
    public void clear() {
        graph.getData().clear();
    }
}
