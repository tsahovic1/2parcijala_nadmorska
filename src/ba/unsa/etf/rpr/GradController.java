package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GradController {
    public TextField fieldNaziv;
    public TextField fieldBrojStanovnika;
    public TextField fieldNadmorskaVisina;
    public ChoiceBox<Drzava> choiceDrzava;
    public ObservableList<Drzava> listDrzave;
    private Grad grad;

    public GradController(Grad grad, ArrayList<Drzava> drzave) {
        this.grad = grad;
        listDrzave = FXCollections.observableArrayList(drzave);
    }
    public boolean provjeriZaNadmorsku(String nadmorska){
        int nadm= Integer.parseInt(nadmorska);
//        for(char c : nadmorska.toCharArray()){
//            if(!Character.isDigit(c)) return false;
//        }


        if(nadm<-400 || nadm>8000) return false;
        return true;
    }

    @FXML
    public void initialize() {
        choiceDrzava.setItems(listDrzave);
        if (grad != null) {
            fieldNaziv.setText(grad.getNaziv());
            fieldBrojStanovnika.setText(Integer.toString(grad.getBrojStanovnika()));
            fieldNadmorskaVisina.setText(Integer.toString(grad.getNadmorskaVisina()));
            // choiceDrzava.getSelectionModel().select(grad.getDrzava());
            // ovo ne radi jer grad.getDrzava() nije identički jednak objekat kao član listDrzave
            for (Drzava drzava : listDrzave)
                if (drzava.getId() == grad.getDrzava().getId())
                    choiceDrzava.getSelectionModel().select(drzava);
        } else {
            choiceDrzava.getSelectionModel().selectFirst();
        }
    }

    public Grad getGrad() {
        return grad;
    }

    public void clickCancel(ActionEvent actionEvent) {
        grad = null;
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }


        int brojStanovnika = 0;
        try {
            brojStanovnika = Integer.parseInt(fieldBrojStanovnika.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (brojStanovnika <= 0) {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeNijeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
        }
//        int nadmorska=8001;
//        try {
//            nadmorska = Integer.parseInt(fieldNadmorskaVisina.getText());
//        } catch (NumberFormatException e) {
//            // ...
//        }
        if(fieldNadmorskaVisina.getText().equals("")) fieldNadmorskaVisina.setText("0");
        if (!provjeriZaNadmorsku(fieldNadmorskaVisina.getText())) {
            fieldNadmorskaVisina.getStyleClass().removeAll("poljeIspravno");
            fieldNadmorskaVisina.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNadmorskaVisina.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNadmorskaVisina.getStyleClass().add("poljeIspravno");
        }
        if (!sveOk) return;

        if (grad == null) grad = new Grad();
        grad.setNaziv(fieldNaziv.getText());
        grad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
        grad.setDrzava(choiceDrzava.getValue());
        grad.setNadmorskaVisina( Integer.parseInt(fieldNadmorskaVisina.getText()));
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }
}
