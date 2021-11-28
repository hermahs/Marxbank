module marxbank.core {
    requires java.base;
    
    exports marxbank.core.model;
    exports marxbank.core.util;

    opens marxbank.core.model;
}
