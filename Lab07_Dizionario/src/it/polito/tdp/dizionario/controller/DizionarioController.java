package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {

	Model m;
	
	public void setModel(Model m) {
		this.m = m;
	}
	
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextArea txtResult;
	@FXML
	private TextField inputNumeroLettere;
	@FXML 
	private Button btnIteriamo; 
	@FXML
	private TextField inputParola;
	@FXML
	private Button btnGeneraGrafo;
	@FXML
	private Button btnTrovaVicini;
	@FXML
	private Button btnTrovaGradoMax;

	@FXML
	void doReset(ActionEvent event) {
		txtResult.clear();
		inputParola.clear();
		inputNumeroLettere.clear();
	}

	int i = 0;
	
	@FXML
	void doGeneraGrafo(ActionEvent event) {

		txtResult.clear();
		
		try {
			if(inputNumeroLettere.getText().compareTo("")!=0){
				
				if(inputNumeroLettere.getText().matches("[0-9]*")){
				int numero = Integer.parseInt(inputNumeroLettere.getText());
				m.createGraph(numero);
				txtResult.setText("Grafo generato con successo");
				i=numero;
				inputNumeroLettere.clear();
				
				}else {
					txtResult.setText("Inserire solo caratteri numerici");
					return;
				}	
			} else {
				txtResult.setText("Inserire un numero");
				return;
			}
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaGradoMax(ActionEvent event) {
		txtResult.clear();
		
		try {
			if(i!=0){
				txtResult.setText(m.findMaxDegree());
			} else {
				txtResult.setText("Grafo inesistente");
			}

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaVicini(ActionEvent event) {
		
		txtResult.clear();
		
		try {
			
			if(inputParola.getText().compareTo("")!=0 || inputParola.getText().matches("[a-z]*")){
				
				if(i==0){
					int numero = inputParola.getText().length();
					m.createGraph(numero);
					i=numero;
				} else {
					if(inputParola.getText().length()!=i){
						txtResult.setText("Lunghezza della parola errata");
						return;
					}		
				}
				
				txtResult.appendText("Vicini di "+inputParola.getText()+":");
				for(String p : m.displayNeighbours(inputParola.getText())){
					txtResult.appendText("\n"+p);
				}
				
				inputParola.clear();
				
			} else {
				txtResult.setText("Inserire una parola");
			}
			
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	 @FXML
	    void doIteriamo(ActionEvent event) {
		 
		 this.txtResult.setText(this.m.itera(this.inputParola.getText()));

	    }
	@FXML
	  void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert inputNumeroLettere != null : "fx:id=\"inputNumeroLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert inputParola != null : "fx:id=\"inputParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnTrovaGradoMax != null : "fx:id=\"btnTrovaGradoMax\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnIteriamo != null : "fx:id=\"btnIteriamo\" was not injected: check your FXML file 'Dizionario.fxml'.";

    }
}