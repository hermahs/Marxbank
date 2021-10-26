package marxbank;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

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

    public void loginPost(LogInRequest request) {
        // logs you in and sets userId and userToken
    }

    public void signup(SignUpRequest request) {
        // runs signup and logs you in
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

}
