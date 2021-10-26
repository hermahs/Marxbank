package marxbank;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import marxbank.model.Account;
import marxbank.model.SavingsAccount;
import marxbank.model.Transaction;
import marxbank.model.User;

public class DataManagerTest {
    
    private User user;
    private Account account;
    private Account account2;
    private Transaction transaction;

    @TempDir
    static Path tempDir;

    @BeforeAll
    static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    @BeforeEach
    public void setup() {
        resetAll();
        user = DataManagerLocal.manager().createUser("yeetman", "email@email.com", "password");
        account = DataManagerLocal.manager().createAccount("Sparekonto", user, "name");
        account2 = DataManagerLocal.manager().createAccount("Sparekonto", user, "name2");
        account.deposit(500.0);
        transaction = DataManagerLocal.manager().createTransaction(account, account2, 50.0);
    }

    @Test
    @DisplayName("Test Create User, Account and Transaction, Adders and deleters")
    public void testCreatorsAddersDeleters() {
        // test creators and adders
        assertTrue(DataManagerLocal.manager().getUsers().get(0).equals(user));
        assertTrue(DataManagerLocal.manager().getAccounts().get(0).equals(account));
        assertTrue(DataManagerLocal.manager().getAccounts().get(1).equals(account2));
        assertTrue(DataManagerLocal.manager().getTransactions().get(0).equals(transaction));

        // test adders throw
        assertThrows(IllegalArgumentException.class, () -> {
            DataManagerLocal.manager().addUser(user);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            DataManagerLocal.manager().addAccount(account);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            DataManagerLocal.manager().addTransaction(transaction);
        });

        // test deleters
        DataManagerLocal.manager().deleteTransaction(transaction);
        DataManagerLocal.manager().deleteAccount(account);
        DataManagerLocal.manager().deleteUser(user);

        assertTrue(DataManagerLocal.manager().getUsers().size() == 0);
        assertTrue(DataManagerLocal.manager().getAccounts().size() == 1);
        assertTrue(DataManagerLocal.manager().getTransactions().size() == 0);

        assertThrows(IllegalArgumentException.class, () -> {
            DataManagerLocal.manager().deleteUser(user);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            DataManagerLocal.manager().deleteAccount(account);
        });
   
        assertThrows(IllegalArgumentException.class, () -> {
            DataManagerLocal.manager().deleteTransaction(transaction);
        });
    }

    @Test
    @DisplayName("test different checkers")
    public void testDifferenctCheckers() {
        assertTrue(DataManagerLocal.manager().checkIfUserExists(user.getId()));
        assertTrue(DataManagerLocal.manager().checkIfAccountExists(account));
        assertTrue(DataManagerLocal.manager().checkIfAccountExists(account2.getId()));
        assertTrue(DataManagerLocal.manager().checkIfTransactionExists(transaction));
        assertTrue(DataManagerLocal.manager().checkIfTransactionExists(transaction.getId()));

        assertFalse(DataManagerLocal.manager().checkIfUserExists(99999));
        assertFalse(DataManagerLocal.manager().checkIfAccountExists(99999));
        assertFalse(DataManagerLocal.manager().checkIfAccountExists(new SavingsAccount(user, "yeet")));
        assertFalse(DataManagerLocal.manager().checkIfTransactionExists(999999999));
        assertFalse(DataManagerLocal.manager().checkIfTransactionExists(new Transaction((long) 1, account, account2, 50.0, false)));
        
    }

    @Test
    @DisplayName("Test differnet getters")
    public void testDifferentGetters() {
        assertTrue(DataManagerLocal.manager().getUserByUsername("yeetman").equals(user));
        assertTrue(DataManagerLocal.manager().getUser(user.getId()).equals(user));
        assertNull(DataManagerLocal.manager().getUser((long) -50.0));
        assertTrue(DataManagerLocal.manager().getAccount(account.getId()).equals(account));
        assertNull(DataManagerLocal.manager().getAccount((long) -50.0));
        assertTrue(DataManagerLocal.manager().getTransaction(transaction.getId()).equals(transaction));
        assertNull(DataManagerLocal.manager().getTransaction((long) -50.0));
    }
    
    @Test
    @DisplayName("Test save and parse")
    public void testSaveAndParse() throws IOException {
        DataManagerLocal.manager().setPath(tempDir.resolve("data").toFile().getCanonicalPath());
        DataManagerLocal.manager().save();
        assertTrue(tempDir.resolve("data").toFile().exists());

        DataManagerLocal.manager().resetData();
        DataManagerLocal.manager().parse();
        assertTrue(DataManagerLocal.manager().getUsers().get(0).equals(user));
        assertTrue(DataManagerLocal.manager().getAccounts().size() == 2);
        assertTrue(DataManagerLocal.manager().getAccount(account.getId()).getId().equals(account.getId()));
        assertTrue(DataManagerLocal.manager().getTransactions().get(0).equals(transaction));
    }

    private void resetAll() {
        Bank.getInstanceBank().clearAccounts();
        DataManagerLocal.manager().resetData();
    }


}
