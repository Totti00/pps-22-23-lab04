package e2.Model;

import e2.Pair;

public class CellImpl implements Cell {
    private final Pair<Integer, Integer> posCell;
    private CellType type;
    private boolean canBeHit;

    public CellImpl(Pair<Integer, Integer> posCell, CellType type) {
        this.canBeHit = true;
        this.posCell = posCell;
        this.type = type;
    }

    @Override
    public boolean hasMine() {
        return this.getType().equals(CellType.MINE);
    }

    @Override
    public CellType getType() {
        return this.type;
    }

    @Override
    public void setMine() {
        this.type = CellType.MINE;
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.posCell;
    }

    @Override
    public boolean canBeHit() {
        return this.canBeHit;
    }

    @Override
    public void hit() {
        this.canBeHit = false;
    }

    @Override
    public boolean hasFlag() {
        return this.type.equals(CellType.FLAG_EMPTY) || this.type.equals(CellType.FLAG_MINE);
    }

    @Override
    public void removeFlag() {
        if (this.type.equals(CellType.FLAG_EMPTY)) {
            this.type = CellType.EMPTY;
        } else {
            this.type = CellType.MINE;
        }
    }

    @Override
    public void placeFlag() {
        if (this.type.equals(CellType.MINE)) {
            this.type = CellType.FLAG_MINE;
        } else {
            this.type = CellType.FLAG_EMPTY;
        }
    }

    @Override
    public boolean isAdjacent(Pair<Integer, Integer> posButton) {
        return (Math.abs(this.posCell.getX() - posButton.getX()) <= 1 && Math.abs(this.posCell.getY() - posButton.getY()) <= 1
                && !(this.posCell.getX().equals(posButton.getX()) && this.posCell.getY().equals(posButton.getY())));
    }
}
