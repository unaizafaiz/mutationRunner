package sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class sudoku {

    protected static Integer[][] sudoku;
    protected static Integer[][] solvedSudoku;
    protected static Boolean[][] sudokuWrongCells;
    private long startTime;

    public void initSudokuWrongCells() {
        sudokuWrongCells = new Boolean[9][9];

        for (Boolean row[] : sudokuWrongCells) {
            Arrays.fill(row, Boolean.FALSE);
        }
    }

    public Boolean[][] getsudokuWrongCells() {
        return sudokuWrongCells;
    }

    public Integer[][] getSudoku() {
        return sudoku;
    }

    public void setUserSudoku(Integer[][] sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * @author Muhammad Al.Rifai
     * @return Sudoku solution.
     */
    public Integer[][] getSudokuSolution() {
        return solvedSudoku;
    }

    /**
     * @param sudoku
     */
    public void setSudoku(Integer[][] sudoku) {
        solvedSudoku = new Integer[9][9];
        for (int rowCounter = 0; rowCounter < 9; rowCounter++) {
            template.Template.instrum(47, "For", new template.Pair("rowCounter",rowCounter));
			for (int columnCounter = 0; columnCounter < 9; columnCounter++) {
                template.Template.instrum(49, "For", new template.Pair("columnCounter",columnCounter));
				solvedSudoku[rowCounter][columnCounter] = sudoku[rowCounter][columnCounter];
            }
        }
    }

    /**
     * This method will solve the Sudoku user have entered. To retreve solution,
     * call getSudokuSolution()
     * 
     * @return ture if solution exist, otherwise false.
     */
    public boolean solveSudoku() {
        startTime = System.currentTimeMillis();
        List<List<Integer>> sudokuList = Arrays.stream(solvedSudoku).map(Arrays::asList).collect(Collectors.toList());
        boolean search = false;
        int upperPart = 0, lowerPart = 0, start;
        for (int row = 0; row < 9; row++) {
            template.Template.instrum(67, "For", new template.Pair("row",row));
			for (int column = 0; column < 9; column++) {
                template.Template.instrum(69, "For", new template.Pair("column",column));
				if (sudokuList.get(row).get(column) == 0) {
                    if (row * 9 + column > 41) {
                        lowerPart++;
                    } else {
                        upperPart++;
                    }
                }
            }
        }
        
        search = upperPart < lowerPart;
        start = search ? 0 : 8;
        return solveSudoku(start, start, sudokuList, search);
    }

    /**
     * auxiliary function for solveSudoku()
     *
     * @param x row index
     * @param y column index
     * @param sudokuList Sudoku
     * @return ture if solution exist, otherwise false.
     */
    private boolean solveSudoku(int x, int y, List<List<Integer>> sudokuList, boolean searchForward) {
        // Check if the function exceeded one second.
        if (System.currentTimeMillis() - startTime > 1000) {
            return false;
        }
        // Next cell number
        int nextCellNum, XLimit, YLimit;
        if (searchForward) {
            XLimit = 8;
            YLimit = 8;
            nextCellNum = x * 9 + y + 1;
        } else {
            XLimit = 0;
            YLimit = 0;
            nextCellNum = x * 9 + y - 1;
        }
        // If cell is empty.
        if (solvedSudoku[x][y] == 0) {
            // Try numbers from 1 to 9.
            for (int PossibleNumber = 1; PossibleNumber < 10; PossibleNumber++) {
                template.Template.instrum(113, "For", new template.Pair("PossibleNumber",PossibleNumber));
				if (uniqueNumber(PossibleNumber, x, y, sudokuList)) {
                    // If the number is unique.
                    solvedSudoku[x][y] = PossibleNumber;
                    sudokuList.get(x).set(y, PossibleNumber);
                    if ((x == XLimit && y == YLimit) || solveSudoku(nextCellNum / 9, nextCellNum % 9, sudokuList, searchForward)) {
                        // If this is the last cell or the next cell returned true.
                        return true;
                    }
                }
            }
            // if there is no avilable number, sets current cell to zero and return to the previous cell.
            solvedSudoku[x][y] = 0;
            sudokuList.get(x).set(y, 0);
            return false;
        } else {
            // cell not empty.
            return (x == XLimit && y == YLimit) || solveSudoku(nextCellNum / 9, nextCellNum % 9, sudokuList, searchForward);
        }
    }

    /**
     * Check if the number is unique in row, column and block.
     *
     * @param PossibleNumber The number to check if its exist or not.
     * @param sudokuList Sudoku.
     * @param x Row index.
     * @param y Column index.
     * @return If number exist or not.
     */
    private boolean uniqueNumber(int PossibleNumber, int x, int y, List<List<Integer>> sudokuList) {
        // first cell in the block that contains (x, y).
        int block_x = (x / 3) * 3;
        int block_y = (y / 3) * 3;
        // if number exist in row.
        if (sudokuList.get(x).contains(PossibleNumber)) {
            return false;
        }
        // if number exist in culomn.
        if (sudokuList.stream().anyMatch(row -> row.get(y) == PossibleNumber)) {
            return false;
        }
        // if number exist in block.
        for (int j = 0; j < 3; j++) {
            template.Template.instrum(157, "For", new template.Pair("j",j));
			if (sudokuList.get(block_x + j).subList(block_y, block_y + 3).contains(PossibleNumber)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fill empty cell with it's correct number.
     *
     * @return Integer array with the following format {x, y, value}
     */
    public int[] hint() {
        solveSudoku();
        for (int i = 0; i < 9; i++) {
            template.Template.instrum(173, "For", new template.Pair("i",i));
			for (int j = 0; j < 9; j++) {
                template.Template.instrum(175, "For", new template.Pair("j",j));
				if (sudoku[i][j] == 0) {
                    sudoku[i][j] = solvedSudoku[i][j];
                    return new int[]{i, j, solvedSudoku[i][j]};
                }
            }
        }
        return null;
    }

    /**
     * Fill empty cell with it's correct number.
     *
     * @param RowIndex Cell Location
     * @param ColumnIndex Cell Location
     * @return Cell's new Value
     */
    public int hint(int RowIndex, int ColumnIndex) {
        solveSudoku();
        sudoku[RowIndex][ColumnIndex] = solvedSudoku[RowIndex][ColumnIndex];
        return solvedSudoku[RowIndex][ColumnIndex];
    }
}
