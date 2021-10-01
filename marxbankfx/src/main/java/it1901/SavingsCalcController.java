package it1901;

import it1901.SavingsCalc;

import it1901.util.TextFieldFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SavingsCalcController {
    
    @FXML private TextField monthlyAmount;
    @FXML private TextField lumpAmount;
    @FXML private TextField period;
    @FXML private TextField interestRate;

    @FXML private Button findTotalAmount;

    @FXML private Label totalAmountText;
    @FXML private Label totalAmount;

    @FXML 
    private void initialize() {
        setNumericOnlyTextFields();
        setText();
    }

    private void setText() {
        period.setText("1");
        period.setText("");
    }

    
    private void setNumericOnlyTextFields() {     
        monthlyAmount.setTextFormatter(TextFieldFormatter.getNumberFormatter());
        lumpAmount.setTextFormatter(TextFieldFormatter.getNumberFormatter());
        period.setTextFormatter(TextFieldFormatter.getNumberFormatter());        // Denne må også forbedres (kun to siffer)
        interestRate.setTextFormatter(TextFieldFormatter.getNumberFormatter());  // Denne må forbedres senere
    }

    @FXML
    private void findTotalAmountAble() {
        findTotalAmount.setDisable(monthlyAmount.getText().isEmpty() || lumpAmount.getText().isEmpty() || period.getText().isEmpty() || interestRate.getText().isEmpty());
    }
    
    
    @FXML
    private void handleFindTotalAmount(ActionEvent ev) {
        int ma = Integer.parseInt(monthlyAmount.getText());
        System.out.println(ma);
        int la = Integer.parseInt(lumpAmount.getText());
        System.out.println(la);
        double ir = Double.parseDouble(interestRate.getText());
        System.out.println(ir);
        int p = Integer.parseInt(period.getText());
        System.out.println(p);

        //double calc = SavingsCalc.calculation(ma, la, ir, p);
        //totalAmountText.setText(Double.toString(calc));

        totalAmountText.setText("Hei");
        System.out.println("6");
    }
    
}
