module marxbank.storage {
    requires transitive marxbank.core;
    requires com.fasterxml.jackson.databind;

    exports marxbank.storage;
    opens marxbank.storage;
}
