package marxbank.marxbankfx;

import java.util.ArrayList;
import java.util.List;
import marxbank.marxbankfx.components.TransactionComponent;
import marxbank.core.model.Account;
import marxbank.core.model.Transaction;
import marxbank.core.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MyTransactionsController {

  @FXML
  private ListView<TransactionComponent> transactionsContainer;

  public void initData(User user) {

    for (Account a : user.getAccounts()) {
      System.out.println(a.getTransactions());
    }

    if (user.getAccounts().size() == 0) {
      return;
    }

    List<Transaction> ml = new ArrayList<Transaction>();
    List<TransactionComponent> l = new ArrayList<TransactionComponent>();
    user.getAccounts().stream().forEach(e -> {
      e.getTransactions().stream().forEach(t -> {
        if (!ml.contains(t))
          ml.add(t);
      });
    });

    ml.stream().forEach(t -> l.add(new TransactionComponent(t)));

    ObservableList<TransactionComponent> ol = FXCollections.observableList(l);
    transactionsContainer.setItems(ol);
  }

}
