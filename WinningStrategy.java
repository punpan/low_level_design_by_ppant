package TicTacToe.strategies;

import TicTacToe.models.Game;

public interface WinningStrategy {

    public void checkWinner(Game game);
}
