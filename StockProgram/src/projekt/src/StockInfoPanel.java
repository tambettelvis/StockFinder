package projekt.src;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StockInfoPanel extends VBox {

	private Label stockName;
	private Label currentPrice;
	private Label daysLow;
	private Label daysMax;
	
	static int spacing = 10;
	
	Button viewGraphButton = new Button("Vaata graafi");
	
	public StockInfoPanel(){
		super(spacing);
		createLabels("", "", "", "");
	}
	
	public StockInfoPanel(String stockName, String currentPrice, String daysLow, String daysMax) {
		super(spacing);
		createLabels(stockName, currentPrice, daysLow, daysMax);
	}

	private void createLabels(String stockString, String currentPriceString, String daysLowString, String daysMaxString){
		Label lbl1 = new Label("Aktsia nimetus: ");
		Label lbl2 = new Label("Hetke hind: ");
		Label lbl3 = new Label("Päeva madalaim hind: ");
		Label lbl4 = new Label("Päeva kõrgeim hind: ");
		
		
		stockName = new Label(stockString);
		currentPrice = new Label(currentPriceString);
		daysLow = new Label(daysLowString);
		daysMax = new Label(daysMaxString);
		
		HBox row1 = new HBox(10);
		HBox row2 = new HBox(10);
		HBox row3 = new HBox(10);
		HBox row4 = new HBox(10);
		
		row1.getChildren().addAll(lbl1, stockName);
		row2.getChildren().addAll(lbl2, currentPrice);
		row3.getChildren().addAll(lbl3, daysLow);
		row4.getChildren().addAll(lbl4, daysMax);
		
		viewGraphButton.setPrefSize(300, 50);
		viewGraphButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// TODO create new window
				// TODO Get graph from internet
			}
			
		});
		
		this.getChildren().addAll(row1, row2, row3, row4, viewGraphButton);
	}

	public String getStockName() {
		return stockName.getText();
	}

	public void setStockName(String stockName) {
		this.stockName.setText(stockName);
	}

	public double getCurrentPrice() {
		return Double.parseDouble(currentPrice.getText());
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice.setText(String.valueOf(currentPrice));
	}


	public void setDaysLow(double daysLow) {
		this.daysLow.setText(String.valueOf(daysLow));
	}

	public void setDaysMax(double daysMax) {
		this.daysMax.setText(String.valueOf(daysMax));
	}
	
}
