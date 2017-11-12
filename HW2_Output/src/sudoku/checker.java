package sudoku;

public class checker {

    //Creating objects from checker classes
    boxChecker box;
    rowChecker row;
    columnChecker column;

    /**
     * @throws InterruptedException
     */
    public void check() throws InterruptedException {
        Thread multiThreadrows[] = new Thread[9];
        Thread multiThreadcolumns[] = new Thread[9];

        for (int row = 0; row < 9; row++) {
            template.Template.instrum(17, "For", new template.Pair("row",row));
			this.row = new rowChecker(row);
            multiThreadrows[row] = new Thread(this.row);
            multiThreadrows[row].start();
        }

        for (int column = 0; column < 9; column++) {
            template.Template.instrum(24, "For", new template.Pair("column",column));
			this.column = new columnChecker(column);
            multiThreadcolumns[column] = new Thread(this.column);
            multiThreadcolumns[column].start();
        }

        int threadId = 0;

        Thread multiThreads[] = new Thread[9];
        for (int row = 0; row < 9; row += 3) {
            template.Template.instrum(34, "For", new template.Pair("row",row));
			for (int col = 0; col < 9; col += 3) {
                template.Template.instrum(36, "For", new template.Pair("col",col));
				this.box = new boxChecker(row, col);
                multiThreads[threadId] = new Thread(this.box);
                multiThreads[threadId].start();
                threadId++;
            }
        }


        for (int i = 0; i < 9; i++) {
            template.Template.instrum(46, "For", new template.Pair("i",i));
			multiThreads[i].join();
            multiThreadrows[i].join();
            multiThreadcolumns[i].join();
        }

    }
}
