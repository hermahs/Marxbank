package it1901;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The purpose of the transaction class is to store information about a transaction between
    two accounts. The information stored should never be changed, so it essentially functions
    as a record, but it is also responsible for withdrawing and depositing the correct amount
    of balance between the accounts.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Transaction {

    private final String id;
    @JsonIgnoreProperties({"user", "transactions", "balance", "interestRate", "type", "dm", "accountNumber", "name"})
    private final Account from;
    @JsonIgnoreProperties({"user", "transactions", "balance", "interestRate", "type", "dm", "accountNumber", "name"})
    private final Account reciever;
    private final double amount;
    @JsonIgnore
    private final LocalDateTime transactionDate;
    private final String dateString;
    @JsonIgnore
    private DataManager dm;

    //autoformats the date text-string 
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Initializes transaction object and runs the commitTransaction method.
     * @param id - id of transaction
     * @param from - Account that money is transfered from
     * @param reciever - Account that recieves money
     * @param amount - Amount of money in transaction
     * @param dm - datamanager object for local storage
     * @param commit - commits the transaction of money between accounts if true
     */
    public Transaction(String id, Account from, Account reciever, double amount, DataManager dm, boolean commit) {
        this.id = id;
        this.from = from;
        this.reciever = reciever;
        this.amount = validateAmount(amount);
        transactionDate = LocalDateTime.now();
        dateString = DATE_TIME_FORMATTER.format(transactionDate);
        this.dm = dm;
        if(commit) {
            commitTransaction();
        } else {
            this.from.addTransaction(this);
            this.reciever.addTransaction(this);
        }
        this.dm.addTransaction(this);
    }

    public Transaction(Account from, Account reciever, double amount, DataManager dm) {
        this(UUID.randomUUID().toString(), from, reciever, amount, dm, true);
    }
    /**
     * Transaction constructor with transaction date as input
     * @param from
     * @param reciever
     * @param amount
     * @param dateString
     * @param dm
     */
    public Transaction(Account from, Account reciever, double amount, String dateString, DataManager dm) {
        this.id = UUID.randomUUID().toString();
        this.from = from;
        this.reciever = reciever;
        this.amount = validateAmount(amount);
        if (!isValidDate(dateString)) {
            throw new IllegalArgumentException("Invalid date format");
        }
        String date[] = dateString.split("-");
        LocalDateTime newDate = LocalDateTime.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), 0,0);
        if(newDate.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Invalid transaction date");
        }
        transactionDate = newDate;
        this.dateString = DATE_TIME_FORMATTER.format(transactionDate);
        this.dm=dm;        
    }

    public String getId() {
        return this.id;
    }

    public String getDateString() {
        return this.dateString;
    }

    public LocalDateTime getTransactionDate() {
        return this.transactionDate;
    }

    public Account getFrom() {
        return from;
    }

    public Account getReciever() {
        return reciever;
    }

    public double getAmount() {
        return this.amount;
    }

    public double validateAmount(double amount) {
        if(amount < 1) throw new IllegalArgumentException("Amount cannot be negative");
        return amount;
    }

    /**
     * Checks if a date input is valid according to the formatter of this class
     * @param dateString input date as string
     * @return true if valid, else false.
     */
    public boolean isValidDate(String dateString) {
        try {
            DATE_TIME_FORMATTER.parse(dateString);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Commits transaction by withdrawing from and depositing to the respective
     * accounts, and adding this transaction object to their history.
     * 
     * @exception - Throws IllegalStateException if either of the accounts are null 
     */    
    public void commitTransaction() {
        if (from == null || reciever == null) {
            throw new IllegalStateException("Cannot commit transaction");
        }
        from.withdraw(this.amount);
        reciever.deposit(this.amount);
        from.addTransaction(this);
        reciever.addTransaction(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(id, transaction.getId());
    }

}
