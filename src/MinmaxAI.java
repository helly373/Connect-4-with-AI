/*
Helly Chauhan - 101414910
Kashyap Mavani - 101413749
Piyush Patel - 101410303
Rushil Tamakuwala - 101413662
*/
public class MinmaxAI {
    private String color;

    //constructor
    public MinmaxAI(String color) {
        this.color = color;
    }

    //Finds the best move for the AI player using the Minimax algorithm.
    public int findBestMove(Board board) {
        int bestMove = -1;
        int maxScore = Integer.MIN_VALUE;
        for (int col = 0; col < Board.getColumns(); col++) {
            if (!board.isColumnFull(col)) {
                // Clone the board to simulate the move
                Board clonedBoard = board.clone();
                clonedBoard.addPiece(col, color);

                int currentScore = minmax(clonedBoard, 8, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                // Update the best move if the current score is higher
                if (currentScore > maxScore) {
                    maxScore = currentScore;
                    bestMove = col;
                }
            }
        }
        return bestMove;
    }

    private int minmax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        //base case
        if (depth == 0 || board.isGameOver()) {
            return evaluate(board);
        }

        if (maximizingPlayer) { // If it's the AI player's turn
            Board clonedBoard = board.clone();
            int maxScore = Integer.MIN_VALUE;

            for (int col = 0; col < Board.getColumns(); col++) {
                if (!clonedBoard.isColumnFull(col)) {
                    clonedBoard.addPiece(col, color);
                    int currentScore = minmax(clonedBoard, depth - 1, alpha, beta, false);
                    maxScore = Math.max(maxScore, currentScore);
                    alpha = Math.max(alpha, currentScore);
                    clonedBoard.removePiece(col); // Undo move

                    if (beta <= alpha) // Beta cutoff
                        break;
                }
            }
            return maxScore;
        } else { // If it's the opponent's turn
            Board clonedBoard = board.clone();
            int minScore = Integer.MAX_VALUE;
            String opponentColor = color.equals("R") ? "Y" : "R"; // Determine opponent's color

            for (int col = 0; col < Board.getColumns(); col++) {
                if (!clonedBoard.isColumnFull(col)) {
                    clonedBoard.addPiece(col, opponentColor);
                    int currentScore = minmax(clonedBoard, depth - 1, alpha, beta, true);
                    minScore = Math.min(minScore, currentScore);
                    beta = Math.min(beta, currentScore);
                    clonedBoard.removePiece(col); // Undo move

                    if (beta <= alpha) // Alpha cutoff
                        break;
                }
            }
            return minScore;
        }
    }

    //Evaluates the current state of the game board.
    private int evaluate(Board board) {
        int score = 0;

        // Evaluate rows
        for (int row = 0; row < Board.getRows(); row++) {
            for (int col = 0; col <= Board.getColumns() - 4; col++) {
                int consecutive = 0;
                for (int k = 0; k < 4; k++) {
                    Piece piece = board.ourBoard[row][col + k];
                    if (piece != null) {
                        if (piece.getColor().equals(color)) {
                            consecutive++;
                        } else {
                            consecutive = 0;
                            break;
                        }
                    }
                }
                score += scoreLine(consecutive);
            }
        }

        // Evaluate columns
        for (int col = 0; col < Board.getColumns(); col++) {
            for (int row = 0; row <= Board.getRows() - 4; row++) {
                int consecutive = 0;
                for (int k = 0; k < 4; k++) {
                    Piece piece = board.ourBoard[row + k][col];
                    if (piece != null) {
                        if (piece.getColor().equals(color)) {
                            consecutive++;
                        } else {
                            consecutive = 0;
                            break;
                        }
                    }
                }
                score += scoreLine(consecutive);
            }
        }

        //  diagonals (bottom-left to top-right)
        for (int row = 0; row <= Board.getRows() - 4; row++) {
            for (int col = 0; col <= Board.getColumns() - 4; col++) {
                int consecutive = 0;
                for (int k = 0; k < 4; k++) {
                    Piece piece = board.ourBoard[row + k][col + k];
                    if (piece != null) {
                        if (piece.getColor().equals(color)) {
                            consecutive++;
                        } else {
                            consecutive = 0;
                            break;
                        }
                    }
                }
                score += scoreLine(consecutive);
            }
        }

        // diagonals (top-left to bottom-right)
        for (int row = 3; row < Board.getRows(); row++) {
            for (int col = 0; col <= Board.getColumns() - 4; col++) {
                int consecutive = 0;
                for (int k = 0; k < 4; k++) {
                    Piece piece = board.ourBoard[row - k][col + k];
                    if (piece != null) {
                        if (piece.getColor().equals(color)) {
                            consecutive++;
                        } else {
                            consecutive = 0;
                            break;
                        }
                    }
                }
                score += scoreLine(consecutive);
            }
        }

        return score;
    }

    //Assigns a score based on the number of consecutive pieces in a line.
    private int scoreLine(int consecutive) {
        if (consecutive == 4) {
            return 100000;
        } else if (consecutive == 3) {
            return 1000;
        } else if (consecutive == 2) {
            return 100;
        } else if (consecutive == 1) {
            return 10;
        } else {
            return 0;
        }
    }
}

