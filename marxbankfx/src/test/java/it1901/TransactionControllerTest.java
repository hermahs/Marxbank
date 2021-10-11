package it1901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class TransactionControllerTest extends ApplicationTest{
    
    private DataManager dm;
    private TransactionController controller;
    private Account a;
    private Account b;
    private User user;

    @TempDir
    static Path tempDir;

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Transaction.fxml"));
        final Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

     /**
     * sets up tempDir for DataMananger
     * @throws IOException
     */
    @BeforeAll
    static void setup() throws IOException {
        Files.createDirectories(tempDir.resolve("data"));
    }

    @BeforeEach
    private void beforeEach() throws IOException {
        resetSingleton();
        this.dm = new DataManager(tempDir.toFile().getCanonicalPath());
        user = new User("username", "email@email.com", "password", dm);
        a = new CheckingAccount(user, dm, "test1");
        b = new CheckingAccount(user, dm, "test2");
        controller.initData(user,dm);
    }

    @Test
    public void testController() {
        assertNotNull(controller);
    }

    @Test
    public void testValidTransaction() {
        a.deposit(100);
        clickOn("#myAccountsList").clickOn("#" + a.getAccountNumber());
        clickOn("#recieverText").write("#" + b.getAccountNumber());
        clickOn("#amountText").write("100");
        clickOn("#transactionBtn");
        assertEquals(0, a.getBalance());
        assertEquals(100, b.getBalance());
        assertEquals("Overføringen var vellykket", ((Label) lookup("#transactionCompleteMsg").query()).getText());
    }

    @Test
    public void testEmptyAccountNum() {
        a.deposit(100);
        clickOn("#amountText").write("100");
        clickOn("#transactionBtn");
        assertEquals(100, a.getBalance());
        assertEquals(0, b.getBalance());
        assertEquals("Noe gikk galt.", ((Label) lookup("#transactionFailedMsg").query()).getText());

        clickOn("#myAccountsList").clickOn("#" + a.getAccountNumber());
        assertEquals(100, a.getBalance());
        assertEquals(0, b.getBalance());
        assertEquals("Noe gikk galt.", ((Label) lookup("#transactionFailedMsg").query()).getText());
    }

    @Test
    public void testEmptyAmount() {
        a.deposit(100);
        clickOn("#myAccountsList").clickOn("#" + a.getAccountNumber());
        clickOn("#recieverText").write("#" + b.getAccountNumber());
        clickOn("#transactionBtn");
        assertEquals(100, a.getBalance());
        assertEquals(0, b.getBalance());
        assertEquals("Noe gikk galt.", ((Label) lookup("#transactionFailedMsg").query()).getText());
    }

    @Test
    public void testInvalidAmount() {
        a.deposit(100);
        a.setName("name");
        clickOn("#myAccountsList").clickOn("#" + a.getAccountNumber());
        clickOn("#recieverText").write("#" + b.getAccountNumber());
        clickOn("#amountText").write("101");
        clickOn("#transactionBtn");
        assertEquals(100, a.getBalance());
        assertEquals(0, b.getBalance());
        assertEquals("Ikke nok disponibelt beløp på konto: name. Tilgjengelig beløp: 100.0", ((Label) lookup("#transactionFailedMsg").query()).getText());
    }

    private void resetSingleton() {
        Bank.getInstanceBank().clearAccounts();
    }
}
