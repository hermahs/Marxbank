package marxbank;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import marxbank.API.LogInRequest;
import marxbank.API.SignUpRequest;
import marxbank.model.Account;
import marxbank.model.Transaction;

public class DataManagerOnline {

    private static DataManagerOnline dataInstance = null;

    private Long userId = null;
    private String userToken = null;

    private DataManagerOnline() {}

    public static DataManagerOnline manager() {
        if (dataInstance == null) {
            synchronized (DataManagerOnline.class) {
                if (dataInstance == null) {
                    dataInstance = new DataManagerOnline();
                }
            }
        }

        return dataInstance;
    }

    public Boolean checkIfOnline() {
        try {
            Socket s = new Socket(InetAddress.getLocalHost(), 8080);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void loginPost(LogInRequest request) throws Exception {
        // sjekke mot krypterte passord her etter eller annet sted
        String requestString = String.format("{\"username\": \"%s\", \"password\":\"%s\"}", request.getUsername(), request.getPassword());
        String result = UrlHandler.handlePost("/auth/login", requestString);
        if (result == null) throw new Exception("Yeet yeet");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(result);

        if (node.has("error")) throw new Exception("User does not exist");

        this.userToken = node.get("token").asText();
        this.userId = objectMapper.valueToTree(node.get("userResponse")).get("id").asLong();

        System.out.println(this.userToken);
        System.out.println(this.userId);
    }

    public void signup(SignUpRequest request) {
        // kryptere passord her et eller annet sted
        String requestString = String.format("{\"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}", request.getUsername(), request.getEmail(), request.getPassword());
        String result = UrlHandler.handlePost("/auth/signup", requestString);
    }

    public void logout() {
        // sets userId to null
        // sets token to null
    }

    public void getAccounts() {
        // gets accounts as json
        // Deserialize into list
        // return list
    }

    public void getTransaction() {
        // gets transactions as json
        // Deserialize into list
        // return list
    }

    public void createAccount(Account account) {
        // Serialize account
        // send account data to database
        // return true or something if it works
    }

    public void createTransaction(Transaction transaction) {
        // serialize transaction
        // say transfer from this account to that account
        // hippity hoppety this is now my property
    }

    public static void main(String... args) throws Exception {
        LogInRequest yeet = new LogInRequest("yeet", "yeet");

        DataManagerOnline.manager().loginPost(yeet);

    }

}
