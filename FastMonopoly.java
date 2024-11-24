import java.util.Scanner;

public class FastMonopoly {
    private Player[] players;
    private int turn; // Tracks whose turn it is
    private Card[] board;
    private final int BOARD_SIZE = 10; // Smaller board
    private final int GPA_TO_WIN = 200; // Lower winning GPA
    private final int MAX_TURNS = 10; // 5 turns per player

    public FastMonopoly() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to FastMonopoly!");

        // Initialize players
        players = new Player[2]; // Fixed to two players
        for (int i = 0; i < 2; i++) {
            System.out.print("Enter Player " + (i + 1) + "'s name: ");
            players[i] = new Player(scan.nextLine());
        }

        // Initialize a smaller board
        initializeBoard();
        turn = 0; // Start with the first player
    }

    private void initializeBoard() {
        board = new Card[BOARD_SIZE];
        board[0] = new Special("Go", 0, false, false, false, true);
        board[1] = new Property("Library", 1, 10, 5);
        board[2] = new Chance(2, "GPA");
        board[3] = new Property("Cafeteria", 3, 15, 8);
        board[4] = new Chance(4, "Property");
        board[5] = new Property("Gym", 5, 20, 10);
        board[6] = new Chance(6, "GPA");
        board[7] = new Property("Auditorium", 7, 25, 12);
        board[8] = new Chance(8, "Property");
        board[9] = new Property("AP Class", 9, 30, 15);
    }

    public void playGame() {
        int totalTurns = 0;

        while (totalTurns < MAX_TURNS) {
            Player currentPlayer = players[turn % 2]; // Determine the current player
            System.out.println("\n" + currentPlayer.getName() + "'s turn!");

            // Present player with options
            boolean endGame = takeTurn(currentPlayer);
            if (endGame) {
                System.out.println("\nGame has been ended by " + currentPlayer.getName() + "!");
                return;
            }

            // Check if the player has won
            if (currentPlayer.getGPA() >= GPA_TO_WIN) {
                System.out.println("\n" + currentPlayer.getName() + " wins with " + currentPlayer.getGPA() + " GPA!");
                return;
            }

            // Move to the next player's turn
            turn++;
            totalTurns++;
        }

        // If max turns are reached, determine the winner by GPA
        Player winner = players[0].getGPA() > players[1].getGPA() ? players[0] : players[1];
        System.out.println("\nTime's up! The winner is " + winner.getName() + " with " + winner.getGPA() + " GPA!");
    }

    private boolean takeTurn(Player player) {
        Scanner scan = new Scanner(System.in);

        // Player chooses action
        while (true) {
            System.out.println("\n" + player.getName() + ", choose an option:");
            System.out.println("1. Roll and move");
            System.out.println("2. End the game");
            System.out.print("Enter choice: ");
            String choice = scan.nextLine();

            if (choice.equals("1")) {
                // Roll and move
                rollAndMove(player);
                return false; // Continue the game
            } else if (choice.equals("2")) {
                return true; // End the game
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void rollAndMove(Player player) {
        Scanner scan = new Scanner(System.in);

        // Roll the dice
        System.out.println(player.getName() + ", press [ENTER] to roll the dice.");
        scan.nextLine();
        int roll = (int) (Math.random() * 6) + 1;
        System.out.println(player.getName() + " rolled a " + roll);

        // Move the player
        int newPosition = (player.getPos() + roll) % BOARD_SIZE;
        player.setPos(newPosition);
        Card currentCard = board[newPosition];
        System.out.println(player.getName() + " landed on " + currentCard.getName());

        // If the card is "Chance," show GPA gain/loss
        if (currentCard.isChance() && ((Chance) currentCard).getChanceType().equals("GPA")) {
            System.out.println("You are about to get a random GPA adjustment from Chance!");
        }

        // Handle the card action
        handleCard(player, currentCard);
    }

    private void handleCard(Player player, Card card) {
        Scanner scan = new Scanner(System.in);

        if (card.isSpecial() && ((Special) card).isGo()) {
            player.addGPA(25); // Collect GPA for passing "Go"
            System.out.println(player.getName() + " collected 25 GPA for passing Go!");
        } else if (card.isProperty()) {
            Property property = (Property) card;
            if (!property.getIsOwned()) {
                System.out.println("\n" + player.getName() + ", do you want to buy " + property.getName() + " for " + property.getCost() + " GPA? (Y/N)");
                String choice = scan.nextLine().toUpperCase();
                if (choice.equals("Y") && player.getGPA() >= property.getCost()) {
                    player.buy(card);
                    System.out.println(player.getName() + " bought " + property.getName());
                } else if (choice.equals("Y")) {
                    System.out.println("You don't have enough GPA to buy this property!");
                }
            } else if (property.getOwner() != player) {
                System.out.println(player.getName() + " pays rent to " + property.getOwner().getName());
                player.payRent(card);
            } else {
                System.out.println(player.getName() + " landed on their own property.");
            }
        } else if (card.isChance()) {
            Chance chance = (Chance) card;
            if (chance.getChanceType().equals("GPA")) {
                chance.chanceGPA(player);
            } else if (chance.getChanceType().equals("Property")) {
                chance.chanceProperty(player, board);
            }
        }
    }

    public static void main(String[] args) {
        FastMonopoly game = new FastMonopoly();
        game.playGame();
    }

    void divider() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean checkIfWinner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void choices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean isPlayerInJail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void move() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean isGameOver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void action() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void checkBankrupcy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void nextTurn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void inJailAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
