package e2.Model;

import e2.Model.Cell;
import e2.Pair;

import java.util.Set;

public interface Grid {
    /**
     * Place mines in the grid
     * @param mines number of mines to place
     */
    void placeMines(int mines);

    /**
     * Get the list of cells with mines
     * @return the list of cells with mines
     */
    Set<Cell> listOfCellsWithMines();

    /**
     * Hit a cell
     * @param positionCell the position of the cell to hit
     */
    void hit(Pair<Integer, Integer> positionCell);

    /**
     * Check if a cell has a mine
     * @param positionCell the position of the cell to check
     * @return true if the cell has a mine, false otherwise
     */
    boolean hasMine(Pair<Integer, Integer> positionCell);

    /**
     * Check if a cell has a flag
     * @param posCell the position of the cell to check
     * @return true if the cell has a flag, false otherwise
     */
    boolean hasFlag(Pair<Integer, Integer> posCell);

    /**
     * Remove a flag from a cell
     * @param pos the position of the cell
     */
    void removeFlag(Pair<Integer, Integer> pos);

    /**
     * Place a flag on a cell
     * @param pos the position of the cell
     */
    void placeFlag(Pair<Integer, Integer> pos);

    /**
     * Get the list of cells
     * @return the list of cells
     */
    Set<Cell> getCells();

    /**
     * Get the list of adjacent cells
     * @param pos the position of the cell
     * @return the list of adjacent cells
     */
    Set<Cell> getAdjacentCells(Pair<Integer, Integer> pos);
}
