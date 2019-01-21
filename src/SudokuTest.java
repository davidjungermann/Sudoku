import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SudokuTest {
	SudokuSolver sudoku;

	@Before
	public void setUp() throws Exception { // Initierar det tillstånd vi vill utgå ifrån och testa
		sudoku = new SudokuSolver();
	}

	@After
	public void tearDown() throws Exception { // Städar upp efter varje test genom att frigöra resurser
		sudoku = null;
	}

	@Test
	public void testEmptySudoku() {
		assertTrue("Can't solve empty sudoku board", sudoku.solveSudoku()); 
	}

	@Test
	public void testSpecifiedSudoku() { // Testar figur 1 ur instruktionerna
		sudoku.setValue(0, 2, 8);
		sudoku.setValue(0, 5, 9);
		sudoku.setValue(0, 7, 6);
		sudoku.setValue(0, 8, 2);

		sudoku.setValue(1, 8, 5);

		sudoku.setValue(2, 0, 1);
		sudoku.setValue(2, 2, 2);
		sudoku.setValue(2, 3, 5);

		sudoku.setValue(3, 3, 2);
		sudoku.setValue(3, 4, 1);
		sudoku.setValue(3, 7, 9);

		sudoku.setValue(4, 1, 5);
		sudoku.setValue(4, 6, 6);

		sudoku.setValue(5, 0, 6);
		sudoku.setValue(5, 7, 2);
		sudoku.setValue(5, 8, 8);

		sudoku.setValue(6, 0, 4);
		sudoku.setValue(6, 1, 1);
		sudoku.setValue(6, 3, 6);
		sudoku.setValue(6, 5, 8);

		sudoku.setValue(7, 0, 8);
		sudoku.setValue(7, 1, 6);
		sudoku.setValue(7, 4, 3);
		sudoku.setValue(7, 6, 1);

		sudoku.setValue(8, 6, 4);
		assertTrue("Kunde inte lösa exempelfallet", sudoku.solveSudoku());

	}
	
	@Test
	public void testTwoSameRow() {
		sudoku.setValue(0, 2, 1);
		sudoku.setValue(0, 0, 1);
		assertFalse("Can solve two equal numbers in same row", sudoku.solveSudoku());
	}
	
	@Test
	public void testTwoSameCol() {
		sudoku.setValue(0, 0, 1);
		sudoku.setValue(2, 0, 1);
		assertFalse("Can solve two equal numbers in same column", sudoku.solveSudoku());
	}
	
	@Test
	public void testTwoSameBox() {
		sudoku.setValue(0, 0, 1);
		sudoku.setValue(2, 2, 1);
		assertFalse("Can solve two equal numbers in same box", sudoku.solveSudoku());
	}
}
