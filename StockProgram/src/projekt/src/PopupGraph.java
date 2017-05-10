package projekt.src;

import java.util.Calendar;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class PopupGraph {
	private Stage window;
	
	private LineChart<String, Number> chart;
	private XYChart.Series<String, Number> series;
	private CategoryAxis axisX = new CategoryAxis();
	private NumberAxis axisY = new NumberAxis();
	
	private Button buttonDateLimit;
	private TextField fieldDateLimit = new TextField();
	private int numOfDataPoints = 10;
	
	PopupGraph(String stock) {
		window = new Stage(StageStyle.UTILITY);
		
		GridPane pane = new GridPane();
		Scene windowScene = new Scene(pane, 200, 200);
		pane.setStyle("-fx-background-color: #e6f7ff;");
		
		//graafik
		chart = new LineChart<>(axisX, axisY);
		chart.prefWidthProperty().bind(windowScene.heightProperty());
		chart.prefHeightProperty().bind(windowScene.heightProperty());
		chart.setStyle("-fx-background-color: #ffffff;");
		pane.add(chart, 0, 0);
		
		axisX.setAutoRanging(true); axisX.setLabel("Aeg");
		axisY.setAutoRanging(true); axisY.setLabel("Vaartus");
		
		//andmed
		series = new XYChart.Series<>();
		series.setName(stock);
		chart.getData().add(series);
		
		//TODO: nupud, kastid graafiku kontrollimiseks
		//kontroll selle ule, kui palju andmeid korraga naidatakse
		//nupud: autoupdate; force update
		
		
		//sulgemisel kustuta objekt
		//FIXME: andmed kaovad
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
				calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " +
				calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND),
				value
				));
	}
}
