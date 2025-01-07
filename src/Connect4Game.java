/*
Helly Chauhan - 101414910
Kashyap Mavani - 101413749
Piyush Patel - 101410303
Rushil Tamakuwala - 101413662
* */

import java.util.Random;
import java.util.Scanner;

/**
 * This class represents the main Connect Four game logic, including multiplayer and AI modes.
 */

public class Connect4Game {
    Scanner scanner = new Scanner(System.in);
    private Board board;
    private String color1;
    private String color2;
    private boolean isPlayer1;//True if it is player1 , False if it is player2

    //constructor
    public Connect4Game(){
        this.board = new Board();

    }
    //Method for players to choose their colors.
    private void chooseColors() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Player 1, choose your color (R/Y): ");
        color1 = scanner.nextLine().toUpperCase();
        while (!color1.equals("R") && !color1.equals("Y")) {
            System.out.println("Invalid color! Please choose 'R' or 'Y'.");
            color1 = scanner.nextLine().toUpperCase();
        }

        color2 = (color1.equals("R")) ? "Y" : "R";
    }
    private String getPlayerName(int playerNumber) {
        System.out.println("Enter Player " + playerNumber + "'s name:");
        return scanner.nextLine();
    }

    private String getPlayerColor(int playerNumber) {
        System.out.println("Enter Player " + playerNumber + "'s color (R/Y):");
        String color = scanner.nextLine().toUpperCase();
        while (!color.equals("R") && !color.equals("Y")) {
            System.out.println("Invalid color! Please choose 'R' or 'Y'.");
            color = scanner.nextLine().toUpperCase();
        }
        return color;
    }

    //Checks if there is a winner after a move.

    public boolean checkForWinner(int column){
        String winningColor;
        if(isPlayer1){
            winningColor = color1;
        }else{
            winningColor=color2;
        }

        return board.checkForWinner(column, winningColor);
    }

    public void multiPlayer(){
        String player1Name = getPlayerName(1);
        String player2Name = getPlayerName(2);

        String player1Color = getPlayerColor(1);
        String player2Color = (player1Color.equals("R")) ? "Y" : "R";

        isPlayer1 = (new Random()).nextBoolean();
        boolean gameRunning = true;

        while (gameRunning){
            board.printBoard();
            String color;
            String playerName;
            if (isPlayer1){
                color = player1Color;
                playerName = player1Name;
                System.out.println(playerName + "'s turn");
            } else {
                color = player2Color;
                playerName = player2Name;
                System.out.println(playerName + "'s turn");
            }

            System.out.println("Please select which column you want to put your piece in ");
            System.out.print("Choose between 1 and " + board.getColumns() + " : ");
            Scanner input = new Scanner(System.in);
            int column = input.nextInt() - 1;

            boolean success = board.addPiece(column, color);

            if (success){
                if (board.checkForWinner(column, color)){
                    board.printBoard();
                    gameRunning = false;
                    System.out.println(playerName + " won !");
                } else if (board.isBoardFull()) {
                    board.printBoard();
                    gameRunning = false;
                    System.out.println("It's a draw!");
                }
                isPlayer1 = !isPlayer1; // switching the player
            }
        }

    }

    
    public void AI() {System.out.println("Please enter your name:");
        String playerName = scanner.nextLine();

        chooseColors();

        System.out.println("Who would you like to go first?");
        System.out.println("Enter '1' for Player 1 or '2' for AI:");
        int choice = scanner.nextInt();
        boolean isPlayer1;
        if (choice == 1) {
            isPlayer1 = true;
        } else {
            isPlayer1 = false;
        }

        MinmaxAI aiPlayer = new MinmaxAI(color2);
        boolean gameRunning = true;

        while (gameRunning) {
            board.printBoard();
            String color;
            if (isPlayer1) {
                color = color1;
                System.out.println("Player 1's turn");
                System.out.println("Hello, " + playerName + "! Please select which column you want to put your piece in ");
                System.out.print("Choose between 1 and " + Board.columns + " : ");
                Scanner input = new Scanner(System.in);
                int column = input.nextInt() - 1;

                boolean success = board.addPiece(column, color);

                if (success) {
                    if (checkForWinner(column)) {
                        board.printBoard();
                        gameRunning = false;
                        System.out.println("Player 1 won !");
                    }
                }
            } else {
                color = color2;
                System.out.println("AI's turn");
                int bestMove = aiPlayer.findBestMove(board);
                System.out.println("AI chooses column: " + (bestMove + 1));
                boolean success = board.addPiece(bestMove, color);

                if (success) {
                    if (checkForWinner(bestMove)) {
                        board.printBoard();
                        gameRunning = false;
                        System.out.println("AI won !");
                    } else if (board.isBoardFull()) {
                        board.printBoard();
                        gameRunning = false;
                        System.out.println("It's a draw!");
                    }
                }
            }

            isPlayer1 = !isPlayer1; // switching the player
        }
    }
}
