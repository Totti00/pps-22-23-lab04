package e2.Model;

import e2.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GridImpl implements Grid {
    private final int size;
    private final Set<Cell> cells = new HashSet<>();

    public GridImpl(int size) {
        this.size = size;
        initializeCells();
    }

    private void initializeCells() {
        IntStream.range(0, this.size).forEach(i -> {
            IntStream.range(0, this.size).forEach(j -> {
                this.cells.add(new CellImpl(new Pair<>(i, j), CellType.EMPTY));
            });
        });
    }

    @Override
    public void placeMines(int mines) {
        IntStream.range(0, mines).map(i -> i + 1).forEach(i -> {
            this.cells.stream().filter(cell -> !cell.hasMine()).skip((int) (cells.size() / 2 * Math.random())).findFirst().ifPresent(Cell::setMine);
        });
    }

    @Override
    public Set<Cell> listOfCellsWithMines() {
        return this.cells.stream().filter(Cell::hasMine).collect(Collectors.toSet());
    }

    @Override
    public void hit(Pair<Integer, Integer> positionCell) {
        getPositionCell(positionCell).hit();
    }

    @Override
    public boolean hasMine(Pair<Integer, Integer> positionCell) {
        return getPositionCell(positionCell).hasMine();
    }

    @Override
    public boolean hasFlag(Pair<Integer, Integer> posCell) {
        return getPositionCell(posCell).hasFlag();
    }

    @Override
    public void removeFlag(Pair<Integer, Integer> pos) {
        getPositionCell(pos).removeFlag();
    }

    @Override
    public void placeFlag(Pair<Integer, Integer> pos) {
        getPositionCell(pos).placeFlag();
    }

    private Cell getPositionCell(Pair<Integer, Integer> positionCell) {
        return this.cells.stream().filter(c -> c.getPosition().equals(positionCell)).findAny().orElse(null);
    }

    @Override
    public Set<Cell> getCells() {
        return this.cells;
    }

    @Override
    public Set<Cell> getAdjacentCells(Pair<Integer, Integer> pos) {
        return this.cells.stream().filter(cell -> cell.isAdjacent(pos)).collect(Collectors.toSet());
    }

}
