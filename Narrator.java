import java.util.Random;

public class Narrator {
    private static final Random random = new Random();

    // COLOR CODES (To make the text pop!)
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    // WELCOME
    // WELCOME
    public static void printWelcome() {
        printLine();
        System.out.println(CYAN + "     ||    WELCOME TO THE 'TIC TAC TOE'    ||    " + RESET);
        System.out.println("        (Win before your moves fade away...)   ");
        printLine();
    }

    // INVALID INPUT / SIZES
    public static void roastGridSize(int size) {
        String[] roasts = {
                "Whoa there, cowboy! " + size + "x" + size + "? I don't have enough RAM for your ego.",
                "Are you trying to simulate the universe? Let's keep it playable.",
                "I'm just a Java program, not a supercomputer. Try a number smaller than 10.",
                "Compensating for something with that grid size? Try again."
        };
        System.out.println(RED + "Narrator: " + getRandom(roasts) + RESET);
    }

    public static void roastInvalidMove() {
        String[] roasts = {
                "Occupied! Do you need glasses, or are you just incredibly optimistic?",
                "That seat is taken. Try somewhere else.",
                "I literally just said you can't go there.",
                "Physics 101: Two objects cannot occupy the same space at the same time."
        };
        System.out.println(YELLOW + "Narrator: " + getRandom(roasts) + RESET);
    }

    // GAME MODES
    public static void explainModes() {
        System.out.println("\nSelect your misery level:");
        System.out.println("1. " + GREEN + "Classic Mode" + RESET + " (For boring people)");
        System.out.println("2. " + GREEN + "Entropy Mode" + RESET + " (Moves disappear after 3 turns. Chaos ensues.)");
    }

    // WINNING / LOSING
    public static void announceWinner(String player) {
        String[] praises = {
                "Miracles do happen. " + player + " wins!",
                player + " wins! The other player should probably uninstall.",
                "And the winner is " + player + "! (Finally, it's over).",
                "Calculated. Precise. " + player + " takes the crown."
        };
        printLine();
        System.out.println(GREEN + getRandom(praises) + RESET);
        printLine();
    }

    // UTILS
    public static void printLine() {
        System.out.println("----------------------------------------------------");
    }

    private static String getRandom(String[] options) {
        return options[random.nextInt(options.length)];
    }

    // Add this to Narrator.java
    public static void announceDraw() {
        String[] roasts = {
            "It's a Draw. You are both equally matched... or equally bad.",
            "Stalemate. Boring. I fell asleep halfway through.",
            "Nobody won? I expected nothing and I'm still disappointed.",
            "A tie. Perfect for people who hate making decisions."
        };
        printLine();
        System.out.println(YELLOW + getRandom(roasts) + RESET);
        printLine();
    }

    // Add to Narrator.java
    public static void sayGoodbye() {
        printLine();
        System.out.println(CYAN + "       GAME OVER       " + RESET);
        System.out.println("   (Go touch some grass.)   ");
        printLine();
    }
}