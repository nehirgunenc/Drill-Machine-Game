import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 * The Main class represents the entry point of the HU-Load Game application.
 */
public class Main extends Application {

    // Constants for game parameters
    private static final int ROWS = 15;
    private static final int COLS = 15;
    private static final int SQUARE_SIZE = 40;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;
    private static final double MOVEMENT_DELTA = 40.0;



    // ImageViews for drill machine in different direction
    private ImageView drillMachineRight;
    private ImageView drillMachineLeft;
    private ImageView drillMachineUp;
    private ImageView drillMachineDown;
    private ImageView drillMachineInitial;




    GameBoard gameBoard = new GameBoard();
    Scene scene = new Scene(gameBoard, WINDOW_WIDTH, WINDOW_HEIGHT);



    /**
     * The entry point of the JavaFX application.
     *
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     * @throws Exception If something goes wrong during application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FuelCountdownApp fuelCountdownApp = new FuelCountdownApp();



        primaryStage.setTitle("HU-Load Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start fuel countdown
        fuelCountdownApp.start(primaryStage);
        fuelCountdownApp.formatFuel(fuelCountdownApp.getCurrentFuel());

        Image imageRight = new Image("assets/drill/drill_55.png");
        Image imageLeft = new Image("assets/drill/drill_01.png");
        Image imageUp = new Image("assets/drill/drill_23.png");
        Image imageDown = new Image("assets/drill/drill_40.png");
        Image imageInitial = new Image("assets/drill/drill_37.png");

        drillMachineRight = new ImageView(imageRight);
        drillMachineLeft = new ImageView(imageLeft);
        drillMachineUp = new ImageView(imageUp);
        drillMachineDown = new ImageView(imageDown);
        drillMachineInitial = new ImageView(imageInitial);

        drillMachineRight.setPreserveRatio(true);
        drillMachineRight.setFitWidth(40);
        drillMachineLeft.setPreserveRatio(true);
        drillMachineLeft.setFitWidth(40);
        drillMachineUp.setPreserveRatio(true);
        drillMachineUp.setFitWidth(40);
        drillMachineDown.setPreserveRatio(true);
        drillMachineDown.setFitWidth(40);
        drillMachineInitial.setPreserveRatio(true);
        drillMachineInitial.setFitWidth(40);


        // Set initial position for drill machine
        drillMachineInitial.setTranslateX(7 * 40);
        drillMachineInitial.setTranslateY(2 * 40);
        gameBoard.getChildren().add(drillMachineInitial);


        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            switch (key) {
                case UP:
                    moveDrillMachine(0, 0, "UP");
                    updateDirection("UP");
                    break;
                case DOWN:
                    moveDrillMachine(0, MOVEMENT_DELTA, "DOWN");
                    updateDirection("DOWN");
                    break;
                case LEFT:
                    moveDrillMachine(-MOVEMENT_DELTA, 0, "LEFT");
                    updateDirection("LEFT");
                    break;
                case RIGHT:
                    moveDrillMachine(MOVEMENT_DELTA, 0, "RIGHT");
                    updateDirection("RIGHT");
                    break;
                default:
                    break;
            }
        });

    }


    /**
     * Updates the direction of the drill machine based on the input.
     *
     * @param direction The direction to update the drill machine to (UP, DOWN, LEFT, RIGHT).
     */
    private void updateDirection(String direction) {
        switch (direction) {
            case "UP":
                gameBoard.getChildren().removeAll(drillMachineRight, drillMachineLeft, drillMachineDown, drillMachineInitial);
                gameBoard.getChildren().add(drillMachineUp);
                break;
            case "DOWN":
                gameBoard.getChildren().removeAll(drillMachineRight, drillMachineLeft, drillMachineUp, drillMachineInitial);
                gameBoard.getChildren().add(drillMachineDown);
                break;
            case "LEFT":
                gameBoard.getChildren().removeAll(drillMachineRight, drillMachineUp, drillMachineDown, drillMachineInitial);
                gameBoard.getChildren().add(drillMachineLeft);
                break;
            case "RIGHT":
                gameBoard.getChildren().removeAll(drillMachineLeft, drillMachineUp, drillMachineDown, drillMachineInitial);
                gameBoard.getChildren().add(drillMachineRight);
                break;
            default:
                break;
        }
    }


    // Variables to track current position of the drill machine
    double currentX = 7 * 40;
    double currentY = 2 * 40;


    /**
     * Moves the drill machine based on the specified delta values and direction.
     *
     * @param dx        The change in X coordinate.
     * @param dy        The change in Y coordinate.
     * @param direction The direction in which the drill machine is moving (UP, DOWN, LEFT, RIGHT).
     */
    private void moveDrillMachine(double dx, double dy, String direction) {

        currentX += dx;
        currentY += dy;

        if ((currentY < (ROWS - 1)*SQUARE_SIZE)&&(currentX>0*SQUARE_SIZE)&&(currentX<(COLS-1)*SQUARE_SIZE)) {
            switch (direction) {
                case "UP":
                    drillMachineUp.setTranslateX(currentX);
                    drillMachineUp.setTranslateY(currentY);
                    break;
                case "DOWN":
                    drillMachineDown.setTranslateX(currentX);
                    drillMachineDown.setTranslateY(currentY);
                    gameBoard.digTile(currentX, currentY);
                    break;
                case "LEFT":
                    drillMachineLeft.setTranslateX(currentX);
                    drillMachineLeft.setTranslateY(currentY);
                    gameBoard.digTile(currentX, currentY);
                    break;
                case "RIGHT":
                    drillMachineRight.setTranslateX(currentX);
                    drillMachineRight.setTranslateY(currentY);
                    gameBoard.digTile(currentX, currentY);
                    break;
                default:
                    break;
            }

        }

    }


    /**
     * Updates the game state based on the current position of the drill machine.
     */
    private void update() {

        String tileType = gameBoard.getTileType((int) (currentX/SQUARE_SIZE), (int) (currentY/SQUARE_SIZE));

        if ("lava_01.png".equals(tileType)) {
            gameOver();
        }
    }

    private void gameOver() {
        Stage stage = null;
        StackPane root = new StackPane();


        root.setStyle("-fx-background-color: red;");

        Text gameOverText = new Text("GAME OVER!");
        gameOverText.setFill(Color.WHITE);
        gameOverText.setFont(Font.font("Arial", 48));

        root.getChildren().add(gameOverText);
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

