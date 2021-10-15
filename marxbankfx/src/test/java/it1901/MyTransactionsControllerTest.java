package it1901;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import it1901.model.Account;
import it1901.model.SavingsAccount;
import it1901.model.Transaction;
import it1901.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyTransactionsControllerTest extends ApplicationTest {
    private MyTransactionsController controller;
    private User user;
    private User user2;
    private Account account1;
    private Account account2;
    private Account account3;
    private Transaction transaction;
    private Transaction transaction2;

    @TempDir
    static Path tempDir;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("MyTransactions.fxml"));
        Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Sets up tempdir for Datamananger
     */
    @BeforeAll
    static void setup() throws IOException {
        Files.createDirectories(tempDir.resolve("data"));
    }

    @BeforeEach
    public void beforeEachSetup() throws IOException, InterruptedException {
        resetSingleton();
        DataManager.manager().setPath(tempDir.toFile().getCanonicalPath());
        this.user = new User("56789", "annaost", "anna.ostmo@gmail.com", "passord");
        this.user2 = new User("555", "erik", "erik@erik.com", "passord");
        this.account1 = new SavingsAccount(user, "Annas brukskonto");
        this.account1.deposit(500);
        this.account2 = new SavingsAccount("12345", user);
        this.account3 = new SavingsAccount("69420", user2);
        this.transaction = new Transaction("4040", account1, account2, 20.0, true);
        this.transaction2 = new Transaction(account1, account3, 100);
        controller.initData(user);
    }

    @Test
    public void testController() {
        assertNotNull(controller);
        //TODO: teste mer
    }

    private void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }
}
