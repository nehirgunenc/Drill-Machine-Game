import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * The GameBoard class represents the grid layout of the game board in the HU-Load Game.
 */
public class GameBoard extends GridPane {
    List<Image> gameBoard = new ArrayList<>();
    private static final int ROWS = 15;
    private static final int COLS = 15;
    private static final int SQUARE_SIZE = 40;
    private static final int TOP_ROWS = 3;


    /**
     * Constructs the GameBoard and initializes the game board layout.
     */
    public GameBoard() {


        ArrayList<Image> images = new ArrayList<>();

        Image lava = new Image("assets/underground/lava_01.png");
        images.add(lava);
        Image amazonite = new Image("assets/underground/valuable_amazonite.png");
        images.add(amazonite);
        Image diamond = new Image("assets/underground/valuable_diamond.png");
        images.add(diamond);
        Image emerald = new Image("assets/underground/valuable_emerald.png");
        images.add(emerald);
        Image goldium = new Image("assets/underground/valuable_goldium.png");
        images.add(goldium);
        Image platinium = new Image("assets/underground/valuable_platinium.png");
        images.add(platinium);
        Image ruby = new Image("assets/underground/valuable_ruby.png");
        images.add(ruby);
        Image silverium = new Image("assets/underground/valuable_silverium.png");
        images.add(silverium);
        Image soil = new Image("/assets/underground/soil_04.png");
        images.add(soil);



        Random random = new Random();


        for (int i = 0; i < 75; i++) {
            gameBoard.add(images.get(8));
        }
        for (int i = 75; i < 77; i++) {
            gameBoard.add(images.get(0));
        }
        for (int i = 77; i < 80; i++) {
            gameBoard.add(images.get(1));
        }
        for (int i = 80; i < 82; i++) {
            gameBoard.add(images.get(2));
        }
        for (int i = 82; i < 84; i++) {
            gameBoard.add(images.get(3));
        }
        for (int i = 84; i < 130; i++) {
            int randomIndex = random.nextInt(images.size());
            gameBoard.add(images.get(randomIndex));
        }


        Collections.shuffle(gameBoard);


        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefSize(SQUARE_SIZE, SQUARE_SIZE);

                if (row < TOP_ROWS) {
                    stackPane.setStyle("-fx-background-color: darkblue;");
                } else if (row == TOP_ROWS) {
                    stackPane.setStyle("-fx-background-color: saddlebrown;");
                    Image image2 = new Image("/assets/underground/top_01.png");
                    ImageView imageView2 = new ImageView(image2);
                    imageView2.setFitWidth(SQUARE_SIZE);
                    imageView2.setFitHeight(SQUARE_SIZE);
                    stackPane.getChildren().add(imageView2);
                } else if (row == ROWS - 1) {
                    Image image3 = new Image("/assets/underground/obstacle_01.png");
                    ImageView imageView3 = new ImageView(image3);
                    imageView3.setFitWidth(SQUARE_SIZE);
                    imageView3.setFitHeight(SQUARE_SIZE);
                    stackPane.getChildren().add(imageView3);
                } else {

                    if (col == 0) {
                        Image image3 = new Image("/assets/underground/obstacle_01.png");
                        ImageView imageView3 = new ImageView(image3);
                        imageView3.setFitWidth(SQUARE_SIZE);
                        imageView3.setFitHeight(SQUARE_SIZE);
                        stackPane.getChildren().add(imageView3);
                    } else if (col == COLS - 1) {
                        Image image3 = new Image("/assets/underground/obstacle_01.png");
                        ImageView imageView3 = new ImageView(image3);
                        imageView3.setFitWidth(SQUARE_SIZE);
                        imageView3.setFitHeight(SQUARE_SIZE);
                        stackPane.getChildren().add(imageView3);
                    } else {
                        stackPane.setStyle("-fx-background-color: saddlebrown;");
                        int randomIndex = random.nextInt(gameBoard.size());
                        Image imagePath = gameBoard.get(randomIndex);


                        ImageView imageView4 = new ImageView(imagePath);


                        imageView4.setFitWidth(SQUARE_SIZE);
                        imageView4.setFitHeight(SQUARE_SIZE);


                        stackPane.getChildren().add(imageView4);


                    }
                }

                add(stackPane, col, row);
            }
        }


    }


    /**
     * Removes a tile from the game board at the specified position.
     *
     * @param x The x-coordinate of the tile to dig.
     * @param y The y-coordinate of the tile to dig.
     */
    public void digTile(double x, double y) {
        for (Node node : getChildren()) {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                double posX = GridPane.getColumnIndex(stackPane) * SQUARE_SIZE;
                double posY = GridPane.getRowIndex(stackPane) * SQUARE_SIZE;
                if (posX == x && posY == y) {
                    stackPane.getChildren().clear();
                    break;
                }
            }
        }
    }



    /**
     * Retrieves the type of tile at the specified row and column.
     *
     * @param row The row index of the tile.
     * @param col The column index of the tile.
     * @return The type of tile at the specified position.
     */
    public String getTileType(int row, int col) {
        for (Node node : getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                if (node instanceof StackPane) {
                    StackPane stackPane = (StackPane) node;
                    if (stackPane.getChildren().size() > 0) {
                        Node imageNode = stackPane.getChildren().get(0);
                        if (imageNode instanceof ImageView) {
                            ImageView imageView = (ImageView) imageNode;
                            Image image = imageView.getImage();
                            //String imageUrl = image.getUrl();
                            String imageUrl = String.valueOf(gameBoard.get(row * COLS + col));
                            String[] parts = imageUrl.split("/");
                            String imageName = parts[parts.length - 1];
                            return imageName;
                        }
                    }
                }
            }
        }
        return "";
    }


}


