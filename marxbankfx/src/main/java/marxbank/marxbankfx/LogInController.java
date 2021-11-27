package marxbank.marxbankfx;

import java.io.IOException;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import marxbank.core.model.User;
import marxbank.storage.DataManager;
import javafx.scene.Node;

public class LogInController {
  @FXML
  private TextField typeUsername;
  @FXML
  private PasswordField typePassword;
  @FXML
  private Label usernameError;
  @FXML
  private Label passwordError;
  @FXML
  private Button registerButton;
  @FXML
  private Button logInButton;
  @FXML
  private Pane newPane;
  @FXML
  private Parent root;


  public LogInController() {}

  @FXML
  public void initialize() {
    typePassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
          try {
            login(e);
          } catch (IOException e1) {
            System.out.println("failed");
          }
        }
      }
    });

    typeUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
          try {
            login(e);
          } catch (IOException e1) {
            System.out.println("failed");
          }
        }
      }
    });
  }

  @FXML
  private void handleLogInButton(MouseEvent e) throws IOException {
    login(e);
  }

  private void login(Event e) throws IOException {
    usernameError.setText("");
    passwordError.setText("");

    String username = typeUsername.getText();

    if (username == null || username.equals("") || username.trim().equals("")) {
      usernameError.setText("Username cannot be blank");
      return;
    }

    if (!username.trim().equals(username)) {
      usernameError.setText("Username cannot contain spaces");
      return;
    }

    User u = DataManager.getUserByUsername(username);

    if (u == null) {
      usernameError.setText("Username is wrong");
      return;
    }

    String password = typePassword.getText();

    if (password == null || password.equals("") || password.trim().equals("")) {
      passwordError.setText("Password cannot be empty");
      return;
    }

    if (!u.getPassword().equals(password)) {
      passwordError.setText("Password is wrong");
      return;
    }

    DataManager.setLoggedInUser(u);

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("Main.fxml"));
    Parent tableViewParent = loader.load();

    Scene tableViewScene = new Scene(tableViewParent);

    // Access the controller and call a method
    MainController controller = loader.getController();
    controller.initData();

    // Get stage information
    Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

    window.setScene(tableViewScene);
    window.show();
  }

  @FXML
  private void handleRegisterButton() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("Register.fxml"));
    AnchorPane pane = loader.load();

    ((AnchorPane) root).getChildren().setAll(pane);
  }
}
