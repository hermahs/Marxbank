package it1901.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import it1901.Account;
import it1901.DataManager;
import it1901.Transaction;

public class TransactionDeserializer extends StdDeserializer<Transaction> {

    private DataManager dm;
    private Account from;
    private Account reciever;

    public TransactionDeserializer(DataManager dm) {
        this(null, dm);
    }

    public TransactionDeserializer(Class<?> vc, DataManager dm) {
        super(vc);
        this.dm = dm;
    }

    @Override
    public Transaction deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        try {
            if(!dm.checkIfAccountExists(node.get("from").get("id").asText()) || !dm.checkIfAccountExists(node.get("reciever").get("id").asText())) throw new IllegalStateException("Accounts doesn't exist");
            from = dm.getAccount(node.get("from").get("id").asText());
            reciever = dm.getAccount(node.get("reciever").get("id").asText());
        } catch (NullPointerException e) {
            if(!dm.checkIfAccountExists(node.get("from").asText()) || !dm.checkIfAccountExists(node.get("reciever").asText())) throw new IllegalStateException("Accounts doesn't exist");
            from = dm.getAccount(node.get("from").asText());
            reciever = dm.getAccount(node.get("reciever").asText());
        }
        if (Boolean.parseBoolean(node.get("commited").asText())) {
            return new Transaction(node.get("id").asText(), from, reciever, node.get("amount").asDouble(), dm, false);
        }
        return new Transaction(node.get("id").asText(), from, reciever, node.get("amount").asDouble(), node.get("dateString").asText(), dm);
    }
    
}
