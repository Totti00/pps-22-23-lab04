package u04lab.polyglot.e3

import u04lab.polyglot.e3.*
import u04lab.code.Option
import u04lab.code.Option.*
import u04lab.code.exercises.List
import u04lab.code.exercises.List.*
import u04lab.polyglot.OptionToOptional

import scala.annotation.tailrec
import scala.util.Random

trait Cell:
  def position: Pair[Int,Int]
  def hasMine: Boolean
  def hasMine_=(state: Boolean): Unit
  def hasFlag: Boolean
  def hasFlag_=(state: Boolean): Unit
  def canBeHit: Boolean
  def hit(): Unit
  def isAdjacent(pos: Pair[Int,Int]): Boolean

object Cell:
  private case class CellImpl(position: Pair[Int,Int],
                              override var hasMine: Boolean,
                              override var  hasFlag: Boolean = false) extends Cell:
    private var _clicked = false
    def canBeHit: Boolean = !_clicked
    def hit(): Unit = _clicked = true
    def isAdjacent(pos: Pair[Int,Int]): Boolean = {
      Math.abs(position.getX - pos.getX) <= 1 && Math.abs(position.getY - pos.getY) <= 1 && position != pos
    }
  def apply(pos: Pair[Int,Int], hasMine: Boolean): Cell = CellImpl(pos, hasMine)

trait Grid:
  def getCells: List[Cell]

object Grid:
  private case class GridImpl(size: Int, mines: Int) extends Grid:
    private var cells: List[Cell] = List.empty
    private val listOfMines = setRandomMines(Nil())(mines)
    for i <- 0 until size do
        for j <- 0 until size do
          val hasMine = contains(listOfMines, new Pair[Int, Int](i, j))
          cells = append(cells, cons(Cell(Pair(i,j), hasMine), List.empty))

    def getCells: List[Cell] = cells

    private def generateRandomPos: Pair[Int, Int] = new Pair[Int, Int](new Random().nextInt(size), new Random().nextInt(size))

    @tailrec
    private def setRandomMines(pos: List[Pair[Int, Int]])(mines: Int): List[Pair[Int, Int]] = mines match
      case 0 => pos
      case _ =>
        if contains(pos, generateRandomPos)
        then
          setRandomMines(pos)(mines)
        else
          setRandomMines(append(pos, cons(generateRandomPos, List.empty)))(mines - 1)

  def apply(size: Int, mines: Int): Grid = GridImpl(size, mines)

class LogicsImpl2(private val size: Int, private val mines: Int) extends Logics:
  private val grid: Grid = Grid(size, mines)

  override def hit(row: Int, col: Int): Boolean =
    val cell = getCell(row, col)
    cell.hit()
    cell.hasMine

  override def hasFlag(row: Int, col: Int): Boolean = getCell(row, col).hasFlag
  override def removeFlag(row: Int, col: Int): Unit = getCell(row, col).hasFlag = false
  override def placeFlag(row: Int, col: Int): Unit = getCell(row, col).hasFlag = true
  override def hasMine(row: Int, col: Int): Boolean = getCell(row, col).hasMine
  override def isAdjacentCellAMine(row: Int, col: Int): Boolean =
    if find(this.grid.getCells)(_.position == new Pair[Int, Int](row, col)) == None() then false
    else getCell(row, col).hasMine

  override def setVisibleMines(): Unit =
    for i <- 0 until size; j <- 0 until size do
        if getCell(i, j).hasMine then getCell(i, j).hit()

  private def getCell(row: Int, col: Int): Cell = get(find(this.grid.getCells)(_.position == new Pair[Int, Int](row, col)))

  override def won: Boolean = length(filter(this.grid.getCells)( c => c.hasMine && c.canBeHit)) + length(filter(this.grid.getCells)( c => !c.hasMine && !c.canBeHit)) == length(this.grid.getCells)
