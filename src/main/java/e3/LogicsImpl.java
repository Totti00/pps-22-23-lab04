package e2;

import e2.Model.Cell;
import e2.Model.Grid;
import e2.Model.GridImpl;

import java.util.Set;

public class LogicsImpl implements Logics {

    private final int size;
    private final Grid grid;


    public LogicsImpl(int size) {
        this.size = size;
        this.grid = new GridImpl(size);
    }

    @Override
    public void placeMines(int mines) {
        this.grid.placeMines(mines);
    }

    @Override
    public Set<Cell> getCellsWithMines() {
        return this.grid.listOfCellsWithMines();
    }

    @Override
    public boolean hit(Pair<Integer, Integer> positionCell) {
        if (!(positionCell.getX() < 0 || positionCell.getX() >= this.size || positionCell.getY() < 0 || positionCell.getY() >= this.size)) {
            if (this.grid.hasMine(positionCell)) {
                return true;
            }
            this.grid.hit(positionCell);
        }
        return false;
    }

    @Override
    public boolean checkWin() {
        return this.grid.getCells().stream().filter(cell -> cell.hasMine() && cell.canBeHit()).count() +
                this.grid.getCells().stream().filter(cell -> !cell.hasMine() && !cell.canBeHit()).count() == this.getCells().size();

    }

    @Override
    public boolean hasFlag(Pair<Integer, Integer> posCell) {
        return this.grid.hasFlag(posCell);
    }

    @Override
    public void removeFlag(Pair<Integer, Integer> pos) {
        this.grid.removeFlag(pos);
    }

    @Override
    public void placeFlag(Pair<Integer, Integer> pos) {
        this.grid.placeFlag(pos);
    }

    @Override
    public Set<Cell> getCells() {
        return this.grid.getCells();
    }

    @Override
    public Set<Cell> getAdjacentCells(Pair<Integer, Integer> pos) {
        return this.grid.getAdjacentCells(pos);
    }

}
