package TicTacToe.strategies;

import TicTacToe.models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ColumnWinningStrategy implements WinningStrategy{
    //Need to check, whether same player has all same sign in any column.
    @Override
    public void checkWinner(Game game) {
        System.out.println("Checking for col win");

        List<List<Cell>> grid = game.getBoard().getGrid();
        int size = game.getBoard().getSize();

        int countFilledCells =0;
        int countBlockedCells=0;


        //for each col:
        for(int j=0;j<=size;j++){
            //for each row:
            for(int i=0;i<=size;++i){
                Cell cell = grid.get(i).get(j);
                if(cell.getCellState().equals(CellState.FILLED)){
                    countFilledCells++;
                }
                if(cell.getCellState().equals(CellState.BLOCKED)){
                    countBlockedCells++;
                }
            }//each col
            if(size==countFilledCells+countBlockedCells){ //means column is filled completely
                Map<Symbol, Integer> cellMovesMap = new HashMap<>();
                for(int i=0;i<=size;++i){
                    Cell cell = grid.get(i).get(j);
                    if(cell.getCellState().equals(CellState.FILLED)){
                        Symbol symbol = cell.getSymbol();
                        cellMovesMap.put(symbol,cellMovesMap.getOrDefault(symbol,0)+1);
                    }
                    //If map has more than one type of symbols, so no one can win in this column.
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
