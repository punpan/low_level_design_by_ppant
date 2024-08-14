package TicTacToe.strategies;

import TicTacToe.models.*;

import java.util.*;
import java.util.stream.Collectors;

public class RowWinningStrategy implements WinningStrategy {
    //Need to check, whether same player has all same sign in any column.
    @Override
    public void checkWinner(Game game) {
        System.out.println("Checking for row win");

        List<List<Cell>> grid = game.getBoard().getGrid();
        int size = game.getBoard().getSize();

        int countFilledCells =0;
        int countBlockedCells=0;

        //for each row:
        for(int i=0;i<=size;i++){
            //for each column:
            for(int j=0;j<=size;++j){
                Cell cell = grid.get(i).get(j);
                if(cell.getCellState().equals(CellState.FILLED)){
                    countFilledCells++;
                }
                if(cell.getCellState().equals(CellState.BLOCKED)){
                    countBlockedCells++;
                }
            }//each col
            if(size==countFilledCells+countBlockedCells){ //means row is filled completely
                Map<Symbol, Integer> cellMovesMap = new HashMap<>();
                for(int j=0;j<=size;++j){
                    Cell cell = grid.get(i).get(j);
                    if(cell.getCellState().equals(CellState.FILLED)){
                        Symbol symbol = cell.getSymbol();
                        cellMovesMap.put(symbol,cellMovesMap.getOrDefault(symbol,0)+1);
                    }
                    //If map has more than one type of symbols, so no one can win in this row.
                    if(cellMovesMap.size() == 1){
                        Symbol symbol=cellMovesMap.keySet().iterator().next();
                        List<Player> players = game.getPlayers().stream().
                                filter(player -> player.getSymbol().equals(symbol)).
                                collect(Collectors.toList());
                        Player player = players.getFirst();
                        game.setGameState(GameState.SUCCESS);
                        game.setWinner(player);
                    }
                }
            }
        }


    }
}
