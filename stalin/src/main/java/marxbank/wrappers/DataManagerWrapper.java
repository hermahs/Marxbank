package marxbank.wrappers;

import java.util.List;

import marxbank.DataManagerLocal;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;

public class DataManagerWrapper {
    
    public List<User> users;
    public List<Account> accounts;
    public List<Transaction> transactions;

    public DataManagerWrapper(DataManagerLocal dm) {
        this.users = dm.getUsers();
        this.accounts = dm.getAccounts();
        this.transactions = dm.getTransactions();
    }



}
