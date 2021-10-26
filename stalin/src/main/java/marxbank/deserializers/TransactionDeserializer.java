package marxbank.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import marxbank.DataManagerLocal;
import marxbank.model.Account;
import marxbank.model.Transaction;

public class TransactionDeserializer extends StdDeserializer<Transaction> {

    private Account from;
    private Account reciever;

    public TransactionDeserializer() {
        this(null);
    }

    public TransactionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Transaction deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        try {
            if(!DataManagerLocal.manager().checkIfAccountExists(node.get("from").get("id").asLong()) || !DataManagerLocal.manager().checkIfAccountExists(node.get("reciever").get("id").asLong())) throw new IllegalStateException("Accounts doesn't exist");
            from = DataManagerLocal.manager().getAccount(node.get("from").get("id").asLong());
            reciever = DataManagerLocal.manager().getAccount(node.get("reciever").get("id").asLong());
        } catch (NullPointerException e) {
            if(!DataManagerLocal.manager().checkIfAccountExists(node.get("from").asLong()) || !DataManagerLocal.manager().checkIfAccountExists(node.get("reciever").asLong())) throw new IllegalStateException("Accounts doesn't exist");
            from = DataManagerLocal.manager().getAccount(node.get("from").asLong());
            reciever = DataManagerLocal.manager().getAccount(node.get("reciever").asLong());
        }
        return new Transaction(node.get("id").asLong(), from, reciever, node.get("amount").asDouble(), node.get("dateString").asText(), false, true);
        
    }
    
}
