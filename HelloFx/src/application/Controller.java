package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.text.DecimalFormat;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField myTextField1;  
    @FXML private TextField myTextField2;  
    @FXML private Label ResultadoComputador;

    @FXML private TextField myTextField11; 
    @FXML private TextField myTextField21; 
    @FXML private Label ResultadoWifi;

    @FXML private TextField myTextField12; 
    @FXML private CheckBox checkArroz, checkBatata, checkLeite, checkPeixe, checkOvos, checkCarneBovina;
    @FXML private Label ResultadoWifi1;
    @FXML private Label resultadoEmergiaPC;
    @FXML private Label resultadoEmergiaWifi;
    @FXML private Label resultadoEmergiaEstudante;  
    @FXML private Label resultadoAlimentos;  
    @FXML private Label resultadoEmergiaTotal;

    // As Transformidades fixas dos alimentos
    private final double arroz = 3.95e4;
    private final double batata = 2.70e4;
    private final double leite = 1.71e6;
    private final double peixe = 2.00e6;
    private final double ovos = 2.00e6;
    private final double carne = 3.17e6;

    private double resultadoComputador = 0.0;
    private double resultadoWifi = 0.0;
    private double resultadoEstudante = 0.0;

    private String formatarCientifico(double valor) {
        DecimalFormat df = new DecimalFormat("0.00E0");
        String formatado = df.format(valor);
        return formatado.replace("E+", "e").replace("E", "e");
    }


    @FXML
    public void calcularEmergiaComputador() {
        try {
            double energia = parseValor(myTextField1.getText());
            double transformidade = parseValor(myTextField2.getText());
            resultadoComputador = energia * transformidade;
            ResultadoComputador.setText("Emergia: " + formatarCientifico(resultadoComputador) + " sej");
            atualizarResultadoTotal(); 
        } catch (NumberFormatException e) {
            ResultadoComputador.setText("Entrada inválida.");
        }
    }

    @FXML
    public void calcularEmergiaWifi() {
        try {
            double energia = parseValor(myTextField11.getText());
            double transformidade = parseValor(myTextField21.getText());
            resultadoWifi = energia * transformidade;
            ResultadoWifi.setText("Emergia: " + formatarCientifico(resultadoWifi) + " sej");
            atualizarResultadoTotal();  
        } catch (NumberFormatException e) {
            ResultadoWifi.setText("Entrada inválida.");
        }
    }

    @FXML
    public void calcularEmergiaEstudante() {
        try {
            double energia = parseValor(myTextField12.getText());
            List<Double> transformidades = new ArrayList<>();
            List<String> alimentosSelecionados = new ArrayList<>();

            if (checkArroz.isSelected()) {
                transformidades.add(arroz);
                alimentosSelecionados.add("Arroz");
            }
            if (checkBatata.isSelected()) {
                transformidades.add(batata);
                alimentosSelecionados.add("Batata");
            }
            if (checkLeite.isSelected()) {
                transformidades.add(leite);
                alimentosSelecionados.add("Leite");
            }
            if (checkPeixe.isSelected()) {
                transformidades.add(peixe);
                alimentosSelecionados.add("Peixe");
            }
            if (checkOvos.isSelected()) {
                transformidades.add(ovos);
                alimentosSelecionados.add("Ovos");
            }
            if (checkCarneBovina.isSelected()) {
                transformidades.add(carne);
                alimentosSelecionados.add("Carne bovina");
            }

            if (transformidades.size() != 2) {
                ResultadoWifi1.setText("Escolha exatamente 2 alimentos.");
                resultadoAlimentos.setText("");
                return;
            }

            resultadoEstudante = energia * transformidades.get(0) + energia * transformidades.get(1);
            ResultadoWifi1.setText("Emergia: " + formatarCientifico(resultadoEstudante) + " sej");
            resultadoAlimentos.setText(String.join(" e ", alimentosSelecionados));

            atualizarResultadoTotal();
        } catch (NumberFormatException e) {
            ResultadoWifi1.setText("Entrada inválida.");
            resultadoAlimentos.setText("");
        }
    }

    private double parseValor(String input) throws NumberFormatException {
        input = input.trim().toLowerCase().replace("x10^", "e");
        return Double.parseDouble(input);
    }

    private void atualizarResultadoTotal() {
        double resultadoTotal = resultadoComputador + resultadoWifi + resultadoEstudante;
        resultadoEmergiaPC.setText("Emergia: " + formatarCientifico(resultadoComputador) + " sej");
        resultadoEmergiaWifi.setText("Emergia: " + formatarCientifico(resultadoWifi) + " sej");
        resultadoEmergiaEstudante.setText("Emergia: " + formatarCientifico(resultadoEstudante) + " sej");
        resultadoEmergiaTotal.setText("Emergia total: " + formatarCientifico(resultadoTotal) + " sej");
    }
}
