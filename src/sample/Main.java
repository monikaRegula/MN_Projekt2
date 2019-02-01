package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class Main creates Stage with Scene and launchces application window
 * @uuthor Monika Regula and Katarzyna Jurkowska
 * @version  1.0
 */
public class Main extends Application {
    /**
     * Method sets Scene sizes and shows it.
     * @param primaryStage Stage
     * @throws Exception Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Model Hodgkina-Huxleya");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();
    }

    /**
     * Method launches the app
     * @param args initial arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
