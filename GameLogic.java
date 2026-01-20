import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GameLogic {
    private Board board;
    private Scanner scanner;
    private String player1;
    private String player2;
    private boolean isEntropyMode;
    
    // Move History for Entropy Mode
    private Queue<int[]> p1Moves;
    private Queue<int[]> p2Moves;
    private final int MAX_MOVES = 3; 

    public GameLogic() {
        this.scanner = new Scanner(System.in);
        this.p1Moves = new LinkedList<>();
        this.p2Moves = new LinkedList<>();
    }

    public void start() {
        Narrator.printWelcome();
        
        // 1. Get names ONLY ONCE (User stays same)
        setupNames();
        
        // 2. Tutorial (Optional, only once)
        System.out.print("\nDo you want a quick tutorial? (y/n): ");
        if (scanner.next().equalsIgnoreCase("y")) {
            runTutorial();
        }

        // 3. The Replay Loop
        boolean playing = true;
        while (playing) {
            resetState();    // Clear old moves from queues
            configureGame(); // Ask for Grid Size & Mode again
            gameLoop();      // Run the game
            
            // Ask to Replay
            System.out.print("\nPlay another round? (y/n): ");
            String ans = scanner.next();
            if (!ans.equalsIgnoreCase("y")) {
                playing = false;
            }
        }

        Narrator.sayGoodbye();
    }

    private void setupNames() {
        System.out.print("Player 1 Name (X): ");
        player1 = scanner.next();
        System.out.print("Player 2 Name (O): ");
        player2 = scanner.next();
    }

    private void resetState() {
        p1Moves.clear();
        p2Moves.clear();
    }

    private void configureGame() {
        // Mode Selection
        Narrator.explainModes();
        System.out.print("Choose Mode (1 or 2): ");
        int modeChoice = 1;
        if (scanner.hasNextInt()) {
            modeChoice = scanner.nextInt();
        } else {
            scanner.next(); // clear bad input
        }
        isEntropyMode = (modeChoice == 2);

        // Grid Setup
        System.out.print("\nEnter Grid Size (e.g., 3 for 3x3): ");
        int size = 3;
        if (scanner.hasNextInt()) {
            size = scanner.nextInt();
        } else {
            scanner.next(); // clear bad input
        }
        
        // Sassy Check
        if (size > 9 || size < 3) {
            Narrator.roastGridSize(size);
            if(size > 9) size = 3;
            if(size < 3) size = 3;
            System.out.println("(Setting grid to 3x3...)");
        }
        
        board = new Board(size);
    }

    private void runTutorial() {
        Narrator.printLine();
        System.out.println("TUTORIAL:");
        System.out.println("1. The board uses (Row, Column) coordinates.");
        System.out.println("2. To play in the center of a 3x3, type: 2 2");
        System.out.println("3. ENTROPY MODE: Moves fade away after " + MAX_MOVES + " turns.");
        Narrator.printLine();
    }

    private void gameLoop() {
        boolean p1Turn = true;
        boolean running = true;
        int movesMade = 0;
        int maxMovesTotal = board.getSize() * board.getSize();

        while (running) {
            board.printBoard();
            
            String currentPlayer = p1Turn ? player1 : player2;
            char symbol = p1Turn ? 'X' : 'O';
            Queue<int[]> currentQueue = p1Turn ? p1Moves : p2Moves;

            System.out.println("\n" + currentPlayer + "'s Turn (" + symbol + ")");
            System.out.print("Enter Row and Column (e.g., 2 2): ");
            
            // Input Validation
            int row, col;
            while (true) {
                if (scanner.hasNextInt()) {
                    row = scanner.nextInt();
                    col = scanner.nextInt();
                    if (board.placeMove(row, col, symbol)) {
                        break; 
                    }
                } else {
                    scanner.next(); 
                    Narrator.roastInvalidMove();
                }
                System.out.print("Try again: ");
            }

            // ENTROPY LOGIC
            currentQueue.add(new int[]{row - 1, col - 1});
            if (isEntropyMode && currentQueue.size() > MAX_MOVES) {
                int[] oldMove = currentQueue.poll(); 
                board.clearCell(oldMove[0], oldMove[1]); 
                System.out.println(Narrator.RED + ">> ENTROPY STRIKES! A move faded away..." + Narrator.RESET);
            }

            // CHECK WIN
            if (checkWin(symbol)) {
                board.printBoard();
                Narrator.announceWinner(currentPlayer);
                running = false;
            } 
            // CHECK DRAW
            else if (!isEntropyMode && ++movesMade >= maxMovesTotal) {
                board.printBoard();
                Narrator.announceDraw(); 
                running = false;
            }

            p1Turn = !p1Turn; 
        }
    }

    private boolean checkWin(char symbol) {
        int n = board.getSize();
        
        // Rows & Cols
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n - 3; j++) {
                if (board.getCell(i, j) == symbol && 
                    board.getCell(i, j+1) == symbol && 
                    board.getCell(i, j+2) == symbol) return true;
                
                if (board.getCell(j, i) == symbol && 
                    board.getCell(j+1, i) == symbol && 
                    board.getCell(j+2, i) == symbol) return true;
            }
        }
        // Diagonals
        for (int i = 0; i <= n - 3; i++) {
            for (int j = 0; j <= n - 3; j++) {
                if (board.getCell(i, j) == symbol && 
                    board.getCell(i+1, j+1) == symbol && 
                    board.getCell(i+2, j+2) == symbol) return true;
                
                if (board.getCell(i, j+2) == symbol && 
                    board.getCell(i+1, j+1) == symbol && 
                    board.getCell(i+2, j) == symbol) return true;
            }
        }
        return false;
    }
}