/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {
    	this.btnSimulazione.setDisable(true);
    	txtResult.clear();
    	Actor att= this.boxAttore.getValue();
    	if(att==null) {
    		txtResult.appendText("Seleziona un attore");
    		return;
    	}
    	List<Actor> simili= model.getAttSimili(att);
    	txtResult.appendText("Calcolo attori simili...\n\n");
    	if(simili.isEmpty()) {
    		txtResult.appendText("Non ci sono attori simili a "+att);
    	}
    	for(Actor a: simili) {
    		txtResult.appendText(a+"\n");
    	}
    	this.btnSimulazione.setDisable(false);
    	this.txtGiorni.setDisable(false);

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String genere=this.boxGenere.getValue();
    	if(genere==null) {
    		txtResult.appendText("Seleziona un genere");
    		return;
    	}
    	txtResult.appendText("Creazione Grafo...\n");
    	model.creaGrafo(genere);
    	
    	txtResult.appendText("#VERTICI: "+model.getNVertici()+"\n#ARCHI: "+model.getNArchi()+"\n");
    	this.boxAttore.setDisable(false);
    	this.boxAttore.getItems().clear();
    	this.boxAttore.getItems().addAll(model.getVertici());
    	this.btnSimili.setDisable(false);

    }

    @FXML
    void doSimulazione(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.btnSimili.setDisable(true);
    	this.btnSimili.setDisable(true);
    	this.txtGiorni.setDisable(true);
    	this.boxAttore.setDisable(true);
    	this.boxGenere.getItems().addAll(model.getGeneri());
    }
}
