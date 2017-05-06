package projekt.src;

import java.util.Calendar;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class PopupGraph {
	private Stage window;
	private LineChart<Number, Number> chart;
	private XYChart.Series<Number, Number> series;
	
	PopupGraph(String stock) {
		window = new Stage(StageStyle.UTILITY);
		
		GridPane pane = new GridPane();
		Scene windowScene = new Scene(pane, 200, 200);
		pane.setStyle("-fx-background-color: #e6f7ff;");
		
		//graafik
		chart = new LineChart<>(new NumberAxis("aeg (min)", 120, 130, 10), new NumberAxis("y", 45, 55, 10));
		chart.prefWidthProperty().bind(windowScene.heightProperty());
		chart.prefHeightProperty().bind(windowScene.heightProperty());
		chart.setStyle("-fx-background-color: #ffffff;");
		pane.add(chart, 0, 0);
		
		//andmed
		series = new XYChart.Series<>();
		series.setName(stock);
		chart.getData().add(series);
		
		//TODO: nupud, kastid graafiku kontrollimiseks
		
		//sulgemisel kustuta objekt
		window.setOnCloseRequest(event -> {
			try {this.finalize();}
			catch (Throwable e) {e.printStackTrace();}
		});
		
		window.setTitle(stock + ": graafik");
		window.setScene(windowScene);
		window.show();
	}
		
	void addData(Calendar calendar, double value) {
		//TODO
		chart.getData().get(0).getData().add(new XYChart.Data<>(
				calendar.get(Calendar.HOUR_OF_DAY)*60 + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND)/60.0,
				value
				));
		//TODO: kohanda piirkond
		//((ValueAxis<Number>)chart.getXAxis()).setUpperBound(series.getData().);
	}
}
