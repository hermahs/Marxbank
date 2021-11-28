module marxbank.backend {
    requires transitive marxbank.core;
    requires marxbank.api;

    requires spring.boot;
    requires spring.web;
    requires spring.context;
    requires spring.beans;
    requires spring.data.commons;
    requires spring.security.config;
    requires spring.security.core;
    requires java.persistence;
    requires spring.core;
    requires spring.boot.autoconfigure;
    requires java.transaction;

}
