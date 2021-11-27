package marxbank.marxbankfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marxbank.storage.DataManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JavaFX App
 */
public class App extends Application {

  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
    scene = new Scene(loadFxml("LogIn"), 640, 480);
    scene.getStylesheets().add(getClass().getResource("style/MainStyle.css").toExternalForm());
    stage.setScene(scene);
    stage.show();

    if (!Files.exists(Paths.get(System.getProperty("user.home"), "data"))) {
      Files.createDirectories(Paths.get(System.getProperty("user.home"), "data"));
    }

    DataManager.setPath(Paths.get(System.getProperty("user.home"), "data").toString());
    try {
      DataManager.parse();
    } catch (Exception e) {
      System.out.println("yeet");
    }
  }

  static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFxml(fxml));
  }

  private static Parent loadFxml(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }

}
