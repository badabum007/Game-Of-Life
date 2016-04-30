package GameOfLifeStatistics

import GameOfLife.Form

class NotationTransformer {
  def parse(temp: Any): Any = {
    temp match {
      case mas: Array[Int] if mas(2) == -1 => getOutputMessage(mas)
      case mas: Array[Int] if mas(2) != -1 => getOutputMessage(mas)
      case moves: Int => "Game is end. Number of steps a bot is " + Integer.toString(moves)
    }
  }

  def getOutputMessage(mas: Array[Int]): String = {
    if (mas(2) == -1) {
      "New botgame is started. Size of field is " +
        Integer.toString(mas(0)) + ":" +
        Integer.toString(mas(1))
    } else {
      "Figure \"" + Form.getName(mas(2)) + "\" added at the coordinates " +
        "(" + Integer.toString(mas(0)) + "," +
        Integer.toString(mas(1)) + ")"
    }
  }

}