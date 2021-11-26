module marxbank.marxbankfx {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires marxbank.core;
    requires marxbank.storage;

    opens marxbank.marxbankfx to javafx.base, javafx.controls, javafx.fxml, javafx.graphics, marxbank.core, marxbank.storage;

}
