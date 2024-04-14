package peggame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class PegGameGUI extends Application
{
    private SqaurePeggame game;  //declaring variables
    private GridPane boardgrid;
    private Label label1;
    private Button[][] pegbuttons;
    private Location presentloca;

    @Override
    public void start(Stage stage)
    {
        BorderPane bp = new BorderPane();  //settingup the borderpane and its insets
        bp.setPadding(new Insets(20));
        /*creating the game board for the 
        * user to make the move using gui*/
        boardgrid = new GridPane();  //creating gridpane
        boardgrid.setHgap(10);
        boardgrid.setVgap(10);
        bp.setCenter(boardgrid);
        GridPane gpane = new GridPane(); // control panel gridpane creation
        gpane.setHgap(10);
        gpane.setVgap(10);
        bp.setTop(gpane);
        Button loadbutton = new Button("Load Board"); //button which is using to load the board
        loadbutton.setOnAction(event -> loadBoard(stage));
        gpane.add(loadbutton, 0, 0);
        Button savebutton = new Button("Save Game");// using the button to save the game
        savebutton.setOnAction(event -> saveGame(stage));
        gpane.add(savebutton, 1, 0);
        Button closebutton = new Button("Close Game");// close game button
        closebutton.setOnAction(event -> closeGame());
        gpane.add(closebutton, 2, 0);
        label1 = new Label("Game State: Not Started"); // displaying the gamestate
        gpane.add(label1, 0, 1, 3, 1);
        Scene scene = new Scene(bp, 600, 600); // scene and showing stage
        stage.setScene(scene);
        stage.setTitle("Peg Game");
        stage.show();
    }
    private void loadBoard(Stage stage)
    { /* initially choosing the file from the desktop
        * and displaying the selected board */
        // loading the board
        FileChooser fileChooser = new FileChooser(); //choosing file from desktop
        fileChooser.setTitle("Open Board File");  //Title
        File file = fileChooser.showOpenDialog(stage);  //displaying on the stage
        if (file != null)  //checking if file not present
        {
            try
            {
                game = new SqaurePeggame(file.getAbsolutePath());
                if (game != null)
                {
                    displayBoard();  //game has started and the respective board selected is displayed
                    GameState gameState = game.getGameState();  //display NOT_STARTED
                    updateGameStateLabel(gameState);
                }
                else
                    displayErrorMessage("Error: Game state is null");  //displays error if theuser chose wrong file
            }
            catch (Exception e)
            {
                displayErrorMessage("Error loading board: " + e.getMessage());  //displays error if the file doesnt exist or file is not in txt format
            }
        }
    }
    private void saveGame(Stage stage) 
    {
        /* this method will save the 
        * save the game to the file */
        if (game != null) 
        {
            FileChooser fileChooser = new FileChooser();  //FileChooser gui control
            fileChooser.setTitle("Save Game");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedfile = fileChooser.showSaveDialog(stage);
            if (selectedfile != null) 
            {
                try 
                {
                    game.saveGame(selectedfile.getAbsolutePath());  //extracying path of the file
                    displayMessage("Game saved successfully.");
                }
                catch (Exception e) 
                {
                    displayErrorMessage("Error saving game: " + e.getMessage());
                }
            }
        }
        else 
            displayMessage("No game in progress.");  //error message
    }

    private void closeGame() 
    {
        /* this method closes 
        * the game at the current state */
        if (game != null) 
        {
            game = null;  //initializing to null
            presentloca = null;  //no peg is selected on the board
            displayBoard(); //displays the empty board afteinitializing as null;
            updateGameStateLabel(GameState.NOT_STARTED); //now the game state is NOT_STARTED
            displayMessage("Game closed.");
        } 
        else 
            displayMessage("No game in progress.");
    }
    private void displayBoard() 
    {
        /* to display the present board */
        boardgrid.getChildren().clear();
        if (game != null) 
        {
            int size = game.getSize();  //extracting size
            pegbuttons = new Button[size][size];
            for (int row=0;row <size;row++) 
            {
                for (int col=0; col<size; col++)
                 {
                    Location location = new Location(row, col);
                    Button button = createPegButton(location);  //calling method that creates the button for the present location on the board
                    pegbuttons[row][col] = button;  //extracting that particular row and column
                    boardgrid.add(button, col, row); //adding to the grid pane
                }
            }
        }
    }
    private Button createPegButton(Location location)
    {
        /* creating the peg button for the
        * user to make a move using the GUI */
        Button button = new Button(); 
        button.setMinSize(50, 50); //setting the size of the peg/buttpon
        updatePegButton(button, location);
        button.setOnAction(event -> handlePegClick(location)); // creating new button at thhe present location of the peg
        return button;
    }
    private void updatePegButton(Button button, Location location) 
    {
        /* updating the peg button to 
        provide the current state of the game */
        boolean ifpeg = game.getPegboardValue(location);
        button.setBackground(new Background(new BackgroundFill(ifpeg ? Color.BLACK : Color.GRAY, null, null)));
    }
    private void handlePegClick(Location clickedloca) 
    {
        /* when the peg is selected the paticular move 
        * is based on the click location*/
        if (presentloca == null) 
        {
            if (game.getPegboardValue(clickedloca)) //
             {
                presentloca = clickedloca;
                /*setting an array pegbuttons to represent the 
                *board and get clicked location and setting its color always to green*/
                pegbuttons[clickedloca.getRow()][clickedloca.getCol()].setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
            }
        }
        else 
        {
            Move move = new Move(presentloca, clickedloca);
            try 
            {
                if (game.isValidMove(move)) 
                {
                    game.makeMove(move);  //making the respective move on gui
                    displayBoard();
                    updateGameStateLabel(game.getGameState());  //presenting the current game sate
                } else 
                {
                    displayErrorMessage("Invalid move");  //displayed when invalid
                }
            }
            catch (PegGameException e) 
            {
                displayErrorMessage("Invalid move: " + e.getMessage());
            }
            presentloca = null;
            
        }
    }

    private void updateGameStateLabel(GameState gameState) {
        // gamestate update
        if (gameState != null) {
            label1.setText("Game State: " + gameState.toString());
        } else {
            label1.setText("Game State: Unknown");
        }
    }

    private void displayErrorMessage(String message) 
    {
        System.out.println("Error: " + message);}

    private void displayMessage(String message)
     {
        System.out.println(message);}

        // launching the game
    public static void main(String[] args) 
    {
        launch(args);
    }
}