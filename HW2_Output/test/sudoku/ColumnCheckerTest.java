package sudoku;

import org.junit.Assert;
import org.junit.Test;
import sudoku.columnChecker;

public class ColumnCheckerTest {

    @Test
    public void correctColumnExpectsAllFalseInWrongCells() {
        Integer[][] correctSudoku = new Integer[][]{
                {4,3,5,2,6,9,7,8,1},
                {6,8,2,5,7,1,4,9,3},
                {1,9,7,8,3,4,5,6,2},
                {8,2,6,1,9,5,3,4,7},
                {3,7,4,6,8,2,9,1,5},
                {9,5,1,7,4,3,6,2,8},
                {5,1,9,3,2,6,8,7,4},
                {2,4,8,9,5,7,1,3,6},
                {7,6,3,4,1,8,2,5,9}};
        columnChecker cc = new columnChecker(0);
        cc.initSudokuWrongCells();
        cc.setUserSudoku(correctSudoku);
        cc.run();
        Boolean[][] wrongCells = cc.getsudokuWrongCells();
        Boolean[][] expectedWrongCells = new Boolean[9][9];
        for(int i=0; i<correctSudoku.length; i++) {
			for(int j=0; j<correctSudoku[i].length; j++) {
				expectedWrongCells[i][j] = false;
            }
        }
        Assert.assertArrayEquals(expectedWrongCells, wrongCells);
    }

    @Test
    public void wrongColumnExpectsSomeTrueInWrongCells() {
        Integer[][] correctSudoku = new Integer[][]{
                {4,3,5,2,6,9,7,8,1},
                {6,8,2,5,7,1,4,9,3},
                {1,9,7,8,3,4,5,6,2},
                {8,2,6,1,9,5,3,4,7},
                {3,7,4,6,8,2,9,1,5},
                {9,5,1,7,4,3,6,2,8},
                {5,1,9,3,2,6,8,7,4},
                {2,4,8,9,6,7,1,3,6},
                {7,6,3,4,1,8,2,5,9}};
        columnChecker cc = new columnChecker(4);
        cc.initSudokuWrongCells();
        cc.setUserSudoku(correctSudoku);
        cc.run();
        Boolean[][] wrongCells = cc.getsudokuWrongCells();
        Boolean[][] expectedWrongCells = new Boolean[9][9];
        for(int i=0; i<correctSudoku.length; i++) {
			for(int j=0; j<correctSudoku[i].length; j++) {
				expectedWrongCells[i][j] = false;
            }
        }
        expectedWrongCells[0][4] = expectedWrongCells[7][4] = true;
        Assert.assertArrayEquals(expectedWrongCells, wrongCells);
    }
}
