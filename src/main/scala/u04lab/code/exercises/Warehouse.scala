package u04lab.code.exercises

import List.*
import u04lab.code.*

import scala.collection.immutable.List.*

trait Item {
  def code: Int
  def name: String
  def tags: List[String]
}

object Item:
  def apply(code: Int, name: String, tags: String*): Item =
    var tagList: List[String] = Nil()
    for tag <- tags do tagList = append(tagList, cons(tag, Nil()))
    ItemImpl(code, name, tagList)

case class ItemImpl(code: Int, name: String, override val tags: List[String]) extends Item


/**
 * A warehouse is a place where items are stored.
 */
trait Warehouse {
  /**
   * Stores an item in the warehouse.
   * @param item the item to store
   */
  def store(item: Item): Unit
  /**
   * Searches for items with the given tag.
   * @param tag the tag to search for
   * @return the list of items with the given tag
   */
  def searchItems(tag: String): List[Item]
  /**
   * Retrieves an item from the warehouse.
   * @param code the code of the item to retrieve
   * @return the item with the given code, if present
   */
  def retrieve(code: Int): Option[Item]
  /**
   * Removes an item from the warehouse.
   * @param item the item to remove
   */
  def remove(item: Item): Unit
  /**
   * Checks if the warehouse contains an item with the given code.
   * @param itemCode the code of the item to check
   * @return true if the warehouse contains an item with the given code, false otherwise
   */
  def contains(itemCode: Int): Boolean
}

object Warehouse {
  def apply(): Warehouse = WarehouseImpl()
}

object SameTag:
  private def checkAllEquals(l: List[Item], tagsCheck: List[String]): Boolean = length(filter(map(l)(_.tags))(x => x == tagsCheck)) == length(l)

  def unapply(items: List[Item]): scala.Option[List[String]] = items match
    case Cons(h, _) if checkAllEquals(items, h.tags) => scala.Option(h.tags)
    case _ => scala.Option(List.empty)


def someTag(l: List[Item]): String = l match
  case SameTag(t) => "Same tag:" + t
  case _ => "No same tag"

case class WarehouseImpl() extends Warehouse:
  private var itemList: List[Item] = Nil()
  override def store(item: Item): Unit = itemList = Cons(item, itemList)
  def contains(itemCode: Int): Boolean = List.contains(map(itemList)(_.code), itemCode)
  def searchItems(tag: String): List[Item] = filter(itemList)(x => List.contains(x.tags, tag))
  def retrieve(code: Int): Option[Item] = find(itemList)(_.code == code)
  def remove(item: Item): Unit = itemList = filter(itemList)(_ != item)


@main def mainWarehouse(): Unit =
  val warehouse = Warehouse()

  val dellXps = Item(33, "Dell XPS 15", "notebook")
  val dellInspiron = Item(34, "Dell Inspiron 13", "notebook")
  val xiaomiMoped = Item(35, "Xiaomi S1", "moped", "mobility")

  println(warehouse.contains(dellXps.code)) // false
  println(warehouse.store(dellXps)) // side effect, add dell xps to the warehouse
  println(warehouse.contains(dellXps.code)) // true
  println(warehouse.store(dellInspiron)) // side effect, add dell inspiron to the warehouse
  println(warehouse.store(xiaomiMoped)) // side effect, add xiaomi moped to the warehouse
  println(warehouse.searchItems("mobility")) // List(xiaomiMoped)
  println(warehouse.searchItems("notebook")) // List(dellXps, dellInspiron)
  println(warehouse.retrieve(11)) // None
  println(warehouse.retrieve(dellXps.code)) // Some(dellXps)
  println(warehouse.remove(dellXps)) // side effect, remove dell xps from the warehouse
  println(warehouse.retrieve(dellXps.code)) // None

  println(someTag(List(dellXps, dellInspiron))) // Same tag:List(notebook)
  println(someTag(List(dellXps, xiaomiMoped))) // No same tag

/** Hints:
 * - Implement the Item with a simple case class
 * - Implement the Warehouse keeping a private List of items
 * - Start implementing contains and store
 * - Implement searchItems using filter and contains
 * - Implement retrieve using find
 * - Implement remove using filter
 * - Refactor the code of Item accepting a variable number of tags (hint: use _*)
*/