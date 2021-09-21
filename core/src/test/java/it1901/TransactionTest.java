package it1901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TransactionTest {

    private Transaction transaction;  
    
    private DataManager dm;
    private User user1;
    private User user2;
    private Account a1;
    private Account a2;

    @TempDir
    static Path tempDir;

    @BeforeAll
    public static void init() throws IOException {
        Files.createDirectory(tempDir.resolve("data"));
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        String path = tempDir.toFile().getCanonicalPath();
        dm = new DataManager(path);
        user1 = new User("id1", "username1", "email1@email.com", "password1", dm);
        user2 = new User("id2", "username2", "email2@email.com", "password2", dm);
        a1 = new SavingsAccount("id1", user1, 2, dm);
        a2 = new SavingsAccount("id4", user2, 2, dm);
        a1.deposit(100);
        a2.deposit(100);
    }
    
    @Test
    @DisplayName("test commitTransaction with a1 and a2 as param")
    public void testCommitTransaction1() {
        transaction = new Transaction("id1000", a1, a2, 50, true, dm);

        assertEquals(transaction.getFrom(), a1);
        assertEquals(transaction.getReciever(), a2);
        assertEquals(transaction.getAmount(), 50);

        assertEquals(a1.getBalance(), 50);
        assertEquals(a2.getBalance(), 150);
        assertEquals(a1.getTransactions().size(), 1);
        assertEquals(a2.getTransactions().size(), 1);

    }

    @Test
    @DisplayName("test commitTransaction with a1 and null as param")
    public void testCommitTransaction2() {
        assertThrows(IllegalStateException.class, () -> {
            transaction = new Transaction("id1001", a1, null, 50, true, dm);;
        });

        assertEquals(a1.getBalance(), 100);
        assertEquals(a1.getTransactions().size(), 0);

    }
}

