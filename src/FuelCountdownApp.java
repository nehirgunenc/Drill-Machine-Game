import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The FuelCountdownApp class represents an application that displays fuel countdown.
 */
public class FuelCountdownApp extends Application {

    private static final int INITIAL_FUEL = 10000;
    private int currentFuel = INITIAL_FUEL;
    private Label fuelLabel;


    /**
     * The entry point of the FuelCountdownApp application.
     *
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        fuelLabel = new Label("Fuel: " + formatFuel(currentFuel));
        root.setTop(fuelLabel);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.03), event -> {
            if (currentFuel > 0) {
                currentFuel -= 10;
                fuelLabel.setText("Fuel: " + formatFuel(currentFuel));
            }
        }));
        timeline.setCycleCount(INITIAL_FUEL / 10);
        timeline.play();


    }


    /**
     * Formats the fuel value into a readable string format.
     *
     * @param fuel The fuel value to format.
     * @return The formatted fuel value string.
     */
    public String formatFuel(int fuel) {
        int thousands = fuel / 1000;
        int hundreds = (fuel % 1000) / 100;
        int tens = (fuel % 100) / 10;
        int ones = fuel % 10;
        return String.format("%d%d%d,%d%d", thousands, hundreds, tens, ones, fuel % 10);
    }

    public int getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(int currentFuel) {
        this.currentFuel = currentFuel;
    }


}
