package u04lab.polyglot.e3;

import u04lab.polyglot.e3.Model.Cell;

import java.util.Set;
import u04lab.code.exercises.List;

public interface Logics {

    /**
     * Hit a cell
     *
     * @param positionCell the position of the cell to hit
     * @return true if the cell has a mine, false otherwise
     */
    boolean hit(int row, int col);

    /**
     * Check if the game is won
     *
     * @return true if the game is won, false otherwise
     */
    boolean won();

    /**
     * Check if a cell has a flag
     *
     * @param posCell the position of the cell to check
     * @return true if the cell has a flag, false otherwise
     */
    boolean hasFlag(int row, int col);

    /**
     * Remove a flag from a cell
     *
     * @param pos the position of the cell to remove the flag from
     */
    void removeFlag(int row, int col);

    /**
     * Check if a cell has a mine
     *
     * @return true if the cell has a mine, false otherwise
     */
    boolean hasMine(int row, int col);

    /**
     * Place a flag on a cell
     *
     * @param pos the position of the cell to place the flag on
     */
    void placeFlag(int row, int col);

    /**
     * Make all the cells with mines visible
     */
    void setVisibleMines();

    /**
     * Check if a cell is adjacent to a mine
     *
     * @param row the row of the cell to check
     * @param col the column of the cell to check
     * @return true if the cell is adjacent to a mine, false otherwise
     */
    boolean isAdjacentCellAMine(int row, int col);
}
