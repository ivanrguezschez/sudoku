package com.irs;

public class Sudoku {

    private static final int BOARD_SIZE = 9;

    private static final String LINE = "-------------------------------";

    public static final int[][] SUDOKU_EXAMPLE = {
            { 0, 0, 0, 0, 0, 0, 8, 0, 9 },
            { 9, 0, 0, 0, 7, 0, 4, 0, 0 },
            { 6, 0, 2, 8, 0, 0, 7, 0, 3 },
            { 0, 0, 0, 6, 0, 0, 0, 0, 7 },
            { 3, 0, 5, 0, 0, 1, 0, 0, 0 },
            { 0, 1, 0, 9, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 4, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 2, 0, 5, 0 },
            { 8, 0, 0, 0, 0, 0, 9, 0, 0 }
    };

    private int[][] sudoku;
    public Sudoku(int[][] sudoku) {
        super();
        this.sudoku = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.sudoku[i][j] = sudoku[i][j];
            }
        }
    }

    public int[][] getSudoku() {
        return sudoku;
    }

    public boolean resolve() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (this.sudoku[i][j] == 0) {
                    for (int k = 1; k < 10; k++) {
                        if (isValid(i, j, k)) {
                            this.sudoku[i][j] = k;
                            if (resolve()) {
                                return true;
                            } else {
                                this.sudoku[i][j] = 0;
                            }
                        }
                    }
                    // Backtracking
                    return false;
                }
            }
        }
        // Sudoku resuelto
        return true;
    }

    private boolean isValid(int row, int col, int val) {
        return isValidInRow(row, col, val)
                && isValidInCol(row, col, val)
                && isValidInBlock(row, col, val);
    }

    private boolean isValidInRow(int row, int col, int val) {
        // Filas
        for (int i = 0; i < BOARD_SIZE; i++) {
            //if (i != col && this.sudoku[row][i] == val) {
            if (this.sudoku[row][i] == val) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidInCol(int row, int col, int val) {
        // Columnas
        for (int i = 0; i < BOARD_SIZE; i++) {
            //if (i != row && this.sudoku[i][col] == val) {
            if (i != row && this.sudoku[i][col] == val) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidInBlock(int row, int col, int val) {
        // Bloques
        /*
        int indexRowBlock = (row / 3) * 3; // Devuelve 0, 3, 6
        int indexColBlock = (col / 3) * 3; // Devuelve 0, 3, 6

        for (int i = indexRowBlock; i < (indexRowBlock + 3); i++) {
            for (int j = indexColBlock; j < (indexColBlock + 3); j++) {
                //if (i != row && j != col && this.sudoku[i][j] == val) {
                if (this.sudoku[i][j] == val) {
                    return false;
                }
            }
        }
        */
        int indexRowBlock = row - row % 3; // Devuelve 0. 3. 6
        int indexColBlock = col - col % 3; // Devuelve 0. 3. 6
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.sudoku[i + indexRowBlock][j + indexColBlock] == val) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printBoard() {
        System.out.println(LINE);
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("|");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(" " + this.sudoku[i][j] + " ");
                if (j % 3 == 2) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i % 3 == 2) {
                System.out.println(LINE);
            }
        }
    }

    public static void main( String[] args ) {
        Sudoku sudoku = new Sudoku(SUDOKU_EXAMPLE);

        System.out.println("Sudoku Sin Resolver");
        sudoku.printBoard();

        long  start = System.currentTimeMillis();
        boolean success = sudoku.resolve();
        long  end = System.currentTimeMillis();
        System.out.println("Tiempo de resolucion: " + (end - start) + " ms");

        if (success) {
            System.out.println("Sudoku Resuelto");
            sudoku.printBoard();
        } else {
            System.out.println("No existe solución para el Sudoku");
        }
    }
}
