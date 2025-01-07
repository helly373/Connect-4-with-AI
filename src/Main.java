/*
Helly Chauhan - 101414910
Kashyap Mavani - 101413749
Piyush Patel - 101410303
Rushil Tamakuwala - 101413662
*/

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        Connect4Game game = new Connect4Game();
        System.out.println("Welcome to Connect 4!");
        System.out.println("Choose game mode:");
        System.out.println("1. Multiplayer");
        System.out.println("2. Against AI");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                game.multiPlayer();
                break;
            case 2:
                game.AI();
                break;
            default:
                System.out.println("Invalid choice. Please choose either 1 or 2.");
        }
    }

}