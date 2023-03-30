package u04lab.polyglot.e3;

import u04lab.polyglot.e3.Model.Cell;
import u04lab.polyglot.e3.Model.Grid;
import u04lab.polyglot.e3.Model.GridImpl;

import java.util.Set;

public class LogicsImpl implements Logics {

    private final int size;
    private final Grid grid;


    public LogicsImpl(int size) {
        this.size = size;
        this.grid = new GridImpl(size);
    }

    @Override
    public boolean isAdjacentCellAMine(int row, int col) {
        return false;
    }

    @Override
    public boolean hasMine(int row, int col) {
        return true;
    }

    @Override
    public void setVisibleMines() {
        //this.grid.listOfCellsWithMines().forEach(cell -> cell.setVisible(true));
    }

    @Override
    public boolean hit(int row, int col) {
        Pair<Integer, Integer> positionCell = new Pair<>(row, col);
        if (!(positionCell.getX() < 0 || positionCell.getX() >= this.size || positionCell.getY() < 0 || positionCell.getY() >= this.size)) {
            if (this.grid.hasMine(positionCell)) {
                return true;
            }
            this.grid.hit(positionCell);
        }
        return false;
    }

    @Override
    public boolean won() {
        return false;
        //return this.grid.getCells().stream().filter(cell -> cell.hasMine() && cell.canBeHit()).count() +
          //      this.grid.getCells().stream().filter(cell -> !cell.hasMine() && !cell.canBeHit()).count() == this.getCells().size();

    }

    @Override
    public boolean hasFlag(int row, int col) {
        Pair<Integer, Integer> posCell = new Pair<>(row, col);
        return this.grid.hasFlag(posCell);
    }

    @Override
    public void removeFlag(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        this.grid.removeFlag(pos);
    }

    @Override
    public void placeFlag(int row, int col) {
        Pair<Integer, Integer> pos = new Pair<>(row, col);
        this.grid.placeFlag(pos);
    }

}
