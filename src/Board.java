/*
Helly Chauhan - 101414910
Kashyap Mavani - 101413749
Piyush Patel - 101410303
Rushil Tamakuwala - 101413662
* */
public class Board {
    public static final int rows = 6;
    public static  final int columns = 7;
    Piece [][] ourBoard = new Piece[rows][columns];

    //constructor
    public Board(){
        for (int row = 0; row < rows ; row++) {
            for (int col = 0; col < columns; col++) {
                ourBoard[row][col] = null;
            }
        }
    }

    //getters
    public static int getColumns() {
        return columns;
    }

    public static int getRows() {
        return rows;
    }

    // Method to check for a winner after a piece is added to a column
    public boolean checkForWinner(int col, String winningColor) {
        boolean playerWon = false;

        for (int row = 0; row < rows; row++) {
            if (ourBoard[row][col] != null) {
                if (checkVertical(row, col, winningColor) || checkHorizontal(row, col, winningColor)
                        || checkDiagonal(row, col, winningColor)) {
                    return true;
                }
            }
        }
        return playerWon;
    }

    // Helper method to check for a vertical win
    private boolean checkVertical(int row, int col, String winningColor){
        int winningStreak = 3;

        //check downwards
        for(int winRow = row + 1; winRow < rows; winRow++) {
            if(ourBoard[winRow][col].getColor().equals(winningColor)) {
                winningStreak--;
                if(winningStreak == 0) return true;
            } else winningStreak = 3;
        }
        return false;
    }

    // Helper method to check for a horizontal win
    private boolean checkHorizontal(int row, int col, String winningColor){
        int winningStreak = 4;
        // look at the horizontal
        for(int winCol = col - 3; winCol <= col + 3; winCol++) {
            if(winCol < 0) continue;
            if(winCol >= columns) break;

            if(ourBoard[row][winCol] != null && ourBoard[row][winCol].getColor().equals(winningColor)) {
                winningStreak--;
                if(winningStreak == 0) return true;
            } else winningStreak = 4;
        }
        return false;
    }

    // Helper method to check for a diagonal win
    private boolean checkDiagonal(int row, int col, String winningColor) {
        // Check left diagonal
        int winningStrike = 0;
        int i = row, j = col;
        while (i < rows && j >= 0) {
            if (ourBoard[i][j] != null && ourBoard[i][j].getColor().equals(winningColor)) {
                winningStrike++;
                if (winningStrike == 4)
                    return true;
            } else {
                break;
            }
            i++;
            j--;
        }

        // Check right diagonal
        winningStrike = 0;
        i = row;
        j = col;
        while (i < rows && j < columns) {
            if (ourBoard[i][j] != null && ourBoard[i][j].getColor().equals(winningColor)) {
                winningStrike++;
                if (winningStrike == 4)
                    return true;
            } else {
                break;
            }
            i++;
            j++;
        }

        return false;
    }

    // Method to add a piece to the board
    public boolean addPiece(int colToAdd, String color){
        //within normal range
        if(colToAdd >= 0  && colToAdd < columns){
            //we add if it is not full
            if(ourBoard[0][colToAdd] == null){
                boolean addedThePiece = false;
                for (int row = rows - 1; row >= 0; row--) {
                    if(ourBoard[row][colToAdd] == null){
                        ourBoard[row][colToAdd] = new Piece();
                        ourBoard[row][colToAdd].setColor(color);
                        addedThePiece = true;
                        break;
                    }
                }
                return addedThePiece;
            }else{
                //that col is full
                System.err.println("This column is full, please choose another");
                return false;
            }
        }
        else{
            //succeeding normal range
            System.err.println("You are inserting piece out of the range");
            return false;
        }
    }

    // to check if a column is full
    public boolean isColumnFull(int col){
        return ourBoard[0][col]!=null;
    }

    // Method to check if the game is over (all columns are full)
    public boolean isGameOver(){
        for (int col = 0; col < columns; col++) {
            if(!isColumnFull(col)){
                return false;
            }
        }
        return true;
    }

    // Method to remove a piece from a column
    public void removePiece(int column) {
        if (ourBoard[rows - 1][column] != null) {
            // Find the lowest empty row in the specified column
            int row = -1;
            for (int i = 0; i < rows; i++) {
                if (ourBoard[i][column] == null) {
                    row = i;
                    break;
                }
            }

            if (row >= 0) {
                ourBoard[row][column] = null;
            }
        }
    }

    //Method to clone the board for checking in Minimax algorithm
    public Board clone(){
        Board boardClone = new Board();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if(ourBoard[row][col] != null){
                    Piece piece = new Piece();
                    piece.setColor(ourBoard[row][col].getColor());
                    boardClone.ourBoard[row][col] = piece;
                }
            }
        }
        return boardClone;
    }

    //prints the board
    public void printBoard(){
        for (int col = 0; col < columns; col++) {
            System.out.print("-");
        }
        System.out.println();
        for (int row = 0; row < rows ; row++) {
            System.out.print("|");

            for (int col = 0; col < columns; col++) {
                if(ourBoard[row][col] == null){
                    System.out.print("_");
                }else{
                    System.out.print(ourBoard[row][col].getColor());
                }
                System.out.print("|");
            }
            System.out.println();
        }
        for (int col = 0; col < columns; col++) {
            System.out.print("-");
        }
        System.out.println();
    }

    // Method to check if the board is full
    public boolean isBoardFull() {
        for (int col = 0; col < columns; col++) {
            if (!isColumnFull(col)) {
                return false;
            }
        }
        return true;
    }
}
