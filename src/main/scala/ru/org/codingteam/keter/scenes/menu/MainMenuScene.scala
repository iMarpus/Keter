package ru.org.codingteam.keter.scenes.menu

import org.scalajs.dom.KeyboardEvent
import ru.org.codingteam.keter.Application
import ru.org.codingteam.keter.game.{LocationMap, GameState}
import ru.org.codingteam.keter.scenes.Scene
import ru.org.codingteam.keter.scenes.game.GameScene
import ru.org.codingteam.rotjs.interface.{Display, ROT}
import ru.org.codingteam.rotjs.wrapper.Wrappers._

class MainMenuScene(display: Display) extends Scene(display) {

  val menuItems = Vector(
    "New Game" -> newGame _,
    "Load Game" -> notImplemented _
  )

  var selectedItem = 0

  override def onKeyDown(event: KeyboardEvent): Unit = {
    event.keyCode match {
      case c if c == ROT.VK_UP =>
        selectedItem -= 1
      case c if c == ROT.VK_DOWN =>
        selectedItem += 1
      case c if c == ROT.VK_RETURN =>
        val (_, action) = menuItems(selectedItem)
        action()
      case _ =>
    }

    selectedItem = selectedItem match {
      case x if x < 0 => menuItems.length - 1
      case x if x >= menuItems.length => 0
      case x => x
    }
  }


  override def render(): Unit = {
    display.clear()

    display.drawTextCentered("Keter", Some(1))
    display.drawTextCentered("=====", Some(2))

    val base = 4
    menuItems.zipWithIndex.foreach { case ((name, action), index) =>
      val (indent, text) = if (index == selectedItem) {
        (2, s"> %b{#fff}%c{#000}$name%c{}%b{}")
      } else {
        (4, name)
      }

      display.drawText(indent, base + index, text)
    }
  }

  private def newGame(): Unit = {
    val state = GameState(Vector(), LocationMap.generate())
    val scene = new GameScene(display, state)
    Application.setScene(scene)
  }

  private def notImplemented(): Unit = {
    Application.setScene(new NotImplementedScene(display, this))
  }

}
