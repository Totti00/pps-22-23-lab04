package e2;

import e2.Model.Cell;

import java.util.Set;

public interface Logics {

    /**
     * Place mines in the grid
     *
     * @param mines number of mines to place
     */
    void placeMines(int mines);

    /**
     * Get the cells with mines
     *
     * @return the cells with mines
     */
    Set<Cell> getCellsWithMines();

    /**
     * Hit a cell
     *
     * @param positionCell the position of the cell to hit
     * @return true if the cell has a mine, false otherwise
     */
    boolean hit(Pair<Integer, Integer> positionCell);

    /**
     * Check if the game is won
     *
     * @return true if the game is won, false otherwise
     */
    boolean checkWin();

    /**
     * Check if a cell has a flag
     *
     * @param posCell the position of the cell to check
     * @return true if the cell has a flag, false otherwise
     */
    boolean hasFlag(Pair<Integer, Integer> posCell);

    /**
     * Remove a flag from a cell
     *
     * @param pos the position of the cell to remove the flag from
     */
    void removeFlag(Pair<Integer, Integer> pos);

    /**
     * Place a flag on a cell
     *
     * @param pos the position of the cell to place the flag on
     */
    void placeFlag(Pair<Integer, Integer> pos);

    /**
     * Get the cells of the grid
     *
     * @return the cells of the grid
     */
    Set<Cell> getCells();

    /**
     * Get the adjacent cells of a cell
     *
     * @param pos the position of the cell to get the adjacent cells from
     * @return the adjacent cells of the cell
     */
    Set<Cell> getAdjacentCells(Pair<Integer, Integer> pos);
}
