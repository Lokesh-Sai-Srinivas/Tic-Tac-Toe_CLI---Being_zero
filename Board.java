public class Board {
    private char[][] grid;
    private int size;

    // NEW: Cool Colors for the grid lines
    private static final String GRID_COLOR = "\u001B[35m"; // Purple
    private static final String RESET = "\u001B[0m";
    private static final String X_COLOR = "\u001B[36m"; // Cyan
    private static final String O_COLOR = "\u001B[33m"; // Yellow

    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = ' '; // Use space instead of '-' for a cleaner look
            }
        }
    }

    public boolean placeMove(int row, int col, char symbol) {
        int r = row - 1;
        int c = col - 1;

        if (r < 0 || r >= size || c < 0 || c >= size) {
            System.out.println("Out of bounds!");
            return false;
        }

        if (grid[r][c] != ' ') {
            Narrator.roastInvalidMove();
            return false;
        }

        grid[r][c] = symbol;
        return true;
    }

    public void clearCell(int row, int col) {
        grid[row][col] = ' ';
    }

    public int getSize() {
        return size;
    }

    public char getCell(int r, int c) {
        return grid[r][c];
    }

    // --- THE COOL PART: RENDERER ---
    public void printBoard() {
        System.out.println(); // Spacing

        // 1. Print Column Headers
        System.out.print("    "); // Offset for row numbers
        for (int i = 1; i <= size; i++) {
            System.out.print("  " + i + " ");
        }
        System.out.println();

        // 2. Print Top Border
        System.out.print("    " + GRID_COLOR + "┌" + RESET);
        for (int i = 0; i < size - 1; i++) {
            System.out.print(GRID_COLOR + "───┬" + RESET);
        }
        System.out.println(GRID_COLOR + "───┐" + RESET);

        // 3. Print Rows
        for (int i = 0; i < size; i++) {
            // A. The Data Row
            System.out.print("  " + (i + 1) + " " + GRID_COLOR + "│" + RESET); // Row Number + Left Edge
            for (int j = 0; j < size; j++) {
                char sym = grid[i][j];
                String color = RESET;
                if (sym == 'X')
                    color = X_COLOR;
                if (sym == 'O')
                    color = O_COLOR;

                System.out.print(" " + color + sym + " " + GRID_COLOR + "│" + RESET);
            }
            System.out.println();

            // B. The Divider Row (only if not the last row)
            if (i < size - 1) {
                System.out.print("    " + GRID_COLOR + "├" + RESET);
                for (int j = 0; j < size - 1; j++) {
                    System.out.print(GRID_COLOR + "───┼" + RESET);
                }
                System.out.println(GRID_COLOR + "───┤" + RESET);
            }
        }

        // 4. Print Bottom Border
        System.out.print("    " + GRID_COLOR + "└" + RESET);
        for (int i = 0; i < size - 1; i++) {
            System.out.print(GRID_COLOR + "───┴" + RESET);
        }
        System.out.println(GRID_COLOR + "───┘" + RESET);
        System.out.println();
    }
}