import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SudokuController extends Application {
	private TilePane tilepane;
	private SingleSymbolField[][] fields;
	private HBox hbox;
	private BorderPane borderpane;
	private Scene scene;
	private Button b1;
	private Button b2;
	private SudokuSolver sudoku;
	private Alert alert;

	@Override
	public void start(Stage primaryStage) throws Exception {
		tilepane = new TilePane(); // Tilepane används för SingleSymbolFields
		borderpane = new BorderPane(); // Tilepane läggs sedan i ett borderpane
		hbox = new HBox(); // HBox används för knapparna
		scene = new Scene(borderpane);
		sudoku = new SudokuSolver();
		alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("The sudoku entered has no valid solutions");
		alert.setTitle("No solutions");

		tilepane.setHgap(3);
		tilepane.setVgap(3);
		tilepane.setAlignment(Pos.CENTER);
		tilepane.setPrefColumns(9);
		tilepane.setPrefRows(9);
		tilepane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE); // Definerar storleken på tilepane så att
																			// storleken är 9x9.
		tilepane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

		borderpane.setCenter(tilepane);
		borderpane.setBottom(hbox);

		createBoard();
		buttonHandler();

		primaryStage.setTitle("Sudoku solver");
		primaryStage.setScene(scene);
		primaryStage.setHeight(400);
		primaryStage.setWidth(400);
		primaryStage.show();
	}

	private void clear() { // Tömmer alla värden i alla textrutor
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				fields[r][c].clear();
			}
		}
	}

	private void buttonHandler() { // Hanterar knapparna
		b1 = new Button("Solve");
		b2 = new Button("Clear");
		hbox.getChildren().addAll(b1, b2);

		b1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				sudoku.scanTextField(fields); // Använder metoden i SudokuSolver för att överföra värden
												// från GUI till modell
				if (sudoku.solveSudoku()) { // Om sudokut går att lösa sätts alla värden i GUIt till de lösta värdena i
											// modellen
					for (int r = 0; r < 9; r++) {
						for (int c = 0; c < 9; c++) {
							fields[r][c].setText(String.valueOf(sudoku.getValue(r, c)));
						}
					}
				} else {
					alert.showAndWait(); // Dialogruta visas om det inte går att lösa. 
					clear(); // Rensar brädet automatiskt om inga lösningar finns. 
				}
			}
		});

		b2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				clear(); // Rensar brädet då knappen trycks ned
			}
		});
	}

	private void createBoard() { // Bygger upp brädet av SingleSymbolFields 
		fields = new SingleSymbolField[9][9];

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				SingleSymbolField field = new SingleSymbolField();
				field.setStyle("-fx-text-box-border: black;");
				int num = row / 3 + col / 3;
				boolean filled = (num % 2) == 0;

				if (filled == true) {
					field.setStyle("-fx-background-color: lightblue;");
				}
				fields[row][col] = field;
				field.setPrefColumnCount(1);
				field.setPrefHeight(1);
				tilepane.getChildren().add(field);
			}
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
