package ru.org.codingteam.keter.game

import ru.org.codingteam.keter.game.objects.{Surface, Player, GameObject}
import ru.org.codingteam.rotjs.interface.Arena

case class LocationMap(surfaces: Array[Array[Surface]], objects: Map[GameObject, (Int, Int)]) {

  def surfaceAt(x: Int, y: Int): Option[Surface] = {
    (x, y) match {
      case _ if y < 0 || x < 0 => None
      case _ if y >= surfaces.length => None
      case _ if x >= surfaces(y).length => None
      case _ => Some(surfaces(y)(x))
    }
  }

}

object LocationMap {

  val wall = Surface("wall", "#", passable = false)
  val floor = Surface("floor", ".", passable = true)

  def generate() = {
    val map = new Arena(7, 7)
    val surfaces = Array.ofDim[Surface](7, 7)
    map.create { (x: Int, y: Int, value: Int) =>
      surfaces(x)(y) = value match {
        case 0 => floor
        case 1 => wall
      }
    }

    LocationMap(surfaces, Map(Player("Dr. Növer") -> (2, 2)))
  }

}
