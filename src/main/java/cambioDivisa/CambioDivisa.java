package cambioDivisa;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisa extends Application {

	private Divisa euro = new Divisa("Euro", 1.0);
	private Divisa libra = new Divisa("Libra", 0.8873);
	private Divisa dolar = new Divisa("Dolar", 1.2007);
	private Divisa yen = new Divisa("Yen", 133.59);
	private Divisa[] divisas = { euro, libra, dolar, yen };
	private TextField origenTextField,destinoTextField;
	private ComboBox<Divisa> origenComboBox,destinoComboBox;

	@Override
	public void start(Stage primaryStage) throws Exception {

		origenTextField = new TextField();
		origenTextField.setPrefColumnCount(7);

		destinoTextField = new TextField();
		destinoTextField.setEditable(false);
		destinoTextField.setPrefColumnCount(7);

		origenComboBox = new ComboBox<Divisa>();
		origenComboBox.getItems().addAll(divisas);
		origenComboBox.getSelectionModel().selectFirst();

		destinoComboBox = new ComboBox<Divisa>();
		destinoComboBox.getItems().addAll(divisas);
		destinoComboBox.getSelectionModel().select(yen);

		Button cambiarButton = new Button("Cambiar");
		cambiarButton.setOnAction(e -> onCambiarDivisa(e));

		HBox origenHBox = new HBox();
		origenHBox.setSpacing(7);
		origenHBox.setAlignment(Pos.BASELINE_CENTER);
		origenHBox.getChildren().addAll(origenTextField, origenComboBox);

		HBox destinoHBox = new HBox();
		destinoHBox.setSpacing(7);
		destinoHBox.setAlignment(Pos.BASELINE_CENTER);
		destinoHBox.getChildren().addAll(destinoTextField, destinoComboBox);

		VBox panelVBox = new VBox();
		panelVBox.setSpacing(7);
		panelVBox.setAlignment(Pos.CENTER);
		panelVBox.getChildren().addAll(origenHBox, destinoHBox, cambiarButton);

		Scene scene = new Scene(panelVBox, 320, 200);

		primaryStage.setTitle("Cambio de Divisa");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void onCambiarDivisa(ActionEvent e) {
		try {
		Double origenDouble = Double.parseDouble(origenTextField.getText());
		Divisa origenDivisa = origenComboBox.getSelectionModel().getSelectedItem();
		Divisa destinoDivisa = destinoComboBox.getSelectionModel().getSelectedItem();
		Double enEuros = origenDivisa.toEuro(origenDouble);
		Double cambioDivisaResult = destinoDivisa.fromEuro(enEuros);
		
		String resultado = String.format("%.4f",cambioDivisaResult);
		destinoTextField.setText("" + resultado);
		}catch(NumberFormatException ex){
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error Cambio de Divisa");
			error.setHeaderText("Error!!");
			error.setContentText("La cantidad especificada no es valida");
			error.show();
				
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
