package sudoku;

import org.junit.Assert;
import org.junit.Test;
import sudoku.sudoku;
import sudoku.checker;

public class CheckerTest {

    @Test
    public void correctSudokuExpectsAllFalse() throws InterruptedException {
        Integer[][] correctSudoku = new Integer[][]{
                {4, 3, 5, 2, 6, 9, 7, 8, 1},
                {6, 8, 2, 5, 7, 1, 4, 9, 3},
                {1, 9, 7, 8, 3, 4, 5, 6, 2},
                {8, 2, 6, 1, 9, 5, 3, 4, 7},
                {3, 7, 4, 6, 8, 2, 9, 1, 5},
                {9, 5, 1, 7, 4, 3, 6, 2, 8},
                {5, 1, 9, 3, 2, 6, 8, 7, 4},
                {2, 4, 8, 9, 5, 7, 1, 3, 6},
                {7, 6, 3, 4, 1, 8, 2, 5, 9}};
        sudoku su = new sudoku();
        su.initSudokuWrongCells();
        su.setUserSudoku(correctSudoku);
        checker chek = new checker();
        chek.check();
        Boolean[][] wrongCells = su.getsudokuWrongCells();
        Boolean[][] expectedWrongCells = new Boolean[9][9];
        for(int i=0; i<correctSudoku.length;i++){
			for(int j=0; j<correctSudoku[i].length;j++){
				expectedWrongCells[i][j]=false;
            }
        }
        Assert.assertArrayEquals(expectedWrongCells,wrongCells);
    }

    @Test
    public void wrongSudokuExpectsSomeTrue() throws InterruptedException {
        Integer[][] wrongSudoku = new Integer[][]{
                {4, 3, 5, 2, 6, 9, 7, 8, 1},
                {6, 8, 2, 5, 7, 1, 4, 9, 3},
                {1, 9, 7, 8, 3, 4, 5, 6, 2},
                {8, 2, 6, 1, 9, 5, 3, 4, 7},
                {3, 7, 4, 6, 8, 2, 9, 1, 5},
                {9, 5, 1, 7, 4, 3, 6, 2, 8},
                {5, 1, 9, 3, 2, 6, 8, 7, 4},
                {2, 6, 8, 9, 5, 7, 1, 3, 6},
                {7, 6, 3, 4, 1, 8, 2, 5, 9}};
        sudoku su = new sudoku();
        su.initSudokuWrongCells();
        su.setUserSudoku(wrongSudoku);
        checker chek = new checker();
        chek.check();
        Boolean[][] wrongCells = su.getsudokuWrongCells();
        Boolean[][] expectedWrongCells = new Boolean[9][9];
        for(int i=0; i<wrongSudoku.length;i++){
			for(int j=0; j<wrongSudoku[i].length;j++){
				expectedWrongCells[i][j]=false;
            }
        }
        expectedWrongCells[7][1] = expectedWrongCells[8][1] = expectedWrongCells[7][8] = true;
        Assert.assertArrayEquals(expectedWrongCells,wrongCells);
    }
}

