
public class SudokuSolver {

	private int[][] sudoku; // Heltalsmatris, Sudokuns rutnät som attribut
	private static final int UNASSIGNED = 0; // Int för att indikera om element är tomma eller ej.

	/**
	 * Creates a Sudoku board.
	 */
	public SudokuSolver() {
		sudoku = new int[9][9]; // Skapar en bräda, 9x9 rutor [r] [c]
	}

	/**
	 * Sets a value in the designated cell of the board.
	 * 
	 * @param value puts the value in the cell.
	 * @param row is the row of the cell.
	 * @param col is the column of the cell.
	 */
	public void setValue(int row, int col, int value) { // Sätter värdet på brädan [r] [c]
		sudoku[row][col] = value;
	}

	/**
	 * Fetches and returns the value from the designated cell of the board.
	 * 
	 * @param row is the row of the cell.
	 * @param col is the column of the cell.
	 * @return value returns value from the cell.
	 */
	public int getValue(int row, int col) { // Hämtar värde
		return sudoku[row][col];
	}

	/**
	 * Transfers the values of the board in the GUI to the model representation.
	 * @param fields is a matrix of SingleSymbolField from GUI. 
	 */
	public void scanTextField(SingleSymbolField[][] fields) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (fields[row][col].getText().isEmpty()) {
					sudoku[row][col] = 0;
				} else {
					int fieldValue = Integer.parseInt(fields[row][col].getText());
					sudoku[row][col] = fieldValue;
				}
			}
		}
	}

	/**
	 * Tries to solve the suduko.
	 * 
	 * @return true if the attempt was successful.
	 */
	public boolean solveSudoku() {
		return solve(0, 0);
	}

	private boolean solve(int row, int col) { // rekursiv metod för att lösa sudoku
		if (sudoku[row][col] == UNASSIGNED) { // Om rutan inte har ett värde givet av användaren i sudoku

			if (row == 8 && col == 8) { // Om vi kan lösa den sista rutan är sudukot löst
				for (int nbr = 1; nbr <= 9; nbr++) { // provar alla siffror mellan 1 och 9.
					sudoku[row][col] = nbr;
					if (allowed(row, col)) { // Om numret är tillåtet enligt reglerna har vi en lösning till rutan
						return true;
					}
				}
				return false;
			}

			for (int nbr = 1; nbr <= 9; nbr++) { // Kollar varje siffra mellan 1 och 9
				sudoku[row][col] = nbr; // Platsen sätts tillfälligt till den gällande siffran
				if (allowed(row, col)) {
					if (col < 8) { // Om vi inte befinner oss i sistan kolumnen
						if (solve(row, col + 1)) { // Backtracking för kolumnen
							return true;
						}
					} else if (solve(row + 1, 0)) { // Backtracking för raden
						return true;
					}

				}
			}
			sudoku[row][col] = UNASSIGNED; // Om inga lösningar finns sätts rutan till tom igen.
			return false;

		} else { // Om rutan är fylld av användaren behöver vi inte testa alla möjligheter

			if (row == 8 && col == 8) { // Om sista rutan är fylld är sudukot löst
				return allowed(row, col);
			}
			if (allowed(row, col)) { // Annars går vi genom med hjälp av backtracking igen.
				if (col < 8) {
					return solve(row, col + 1);
				} else {
					return solve(row + 1, 0);
				}
			}
		}

		return false;
	}

	private boolean allowed(int row, int col) { // Testar reglerna för rad, kolumn och sub-ruta.
		int number = sudoku[row][col];
		sudoku[row][col] = 0;
		if (checkRow(row, number) && checkCol(col, number) && checkBox(row, col, number)) {
			sudoku[row][col] = number;
			return true; // Returnerar true om numret inte finns i något av fallen.
		} else {
			sudoku[row][col] = number;
			return false;
		}
	}

	private boolean checkCol(int col, int number) { // Kollar om ett givet nummer finns i en kolumn
		for (int i = 0; i < 9; i++) {
			if (sudoku[i][col] == number) {
				return false;
			}
		}
		return true;
	}

	private boolean checkRow(int row, int number) { // Kollar om ett givet nummer finns i en rad
		for (int i = 0; i < 9; i++) {
			if (sudoku[row][i] == number) {
				return false;
			}
		}
		return true;
	}

	private boolean checkBox(int row, int col, int number) { // Kollar om ett givet nummer finns i en 3x3-ruta
		int r = (row / 3) * 3;
		int c = (col / 3) * 3;

		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				if (sudoku[i][j] == number) {
					return false;
				}
			}
		}
		return true;
	}

}
