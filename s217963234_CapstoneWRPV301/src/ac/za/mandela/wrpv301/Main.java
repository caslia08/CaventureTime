package ac.za.mandela.wrpv301;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        primaryStage.setTitle("Caventure Time");
        primaryStage.setScene(new Scene(root, 1200, 900));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/jungle.png")));
        primaryStage.show();

    }
}



