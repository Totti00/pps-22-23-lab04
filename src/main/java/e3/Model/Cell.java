package e2.Model;

import e2.Pair;

public interface Cell {

    /**
     * Returns true if the cell has a mine, false otherwise.
     */
    boolean hasMine();

    /**
     * Sets the cell as a mine.
     */
    void setMine();

    /**
     * @return the type of the cell.
     */
    CellType getType();

    /**
     *
     * @return the position of the cell.
     */
    Pair<Integer, Integer> getPosition();

    /**
     * @returntrue if the cell can be hit, false otherwise.
     */
    boolean canBeHit();

    /**
     * Sets the cell as hit.
     */
    void hit();

    /**
     * @return true if the cell has a flag, false otherwise.
     */
    boolean hasFlag();

    /**
     * Removes the flag from the cell.
     */
    void removeFlag();

    /**
     * Places a flag on the cell.
     */
    void placeFlag();

    /**
     * @param posButton the position of the button.
     * @return true if the button is adjacent to the cell, false otherwise.
     */
    boolean isAdjacent(Pair<Integer, Integer> posButton);
}
