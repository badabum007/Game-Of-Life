package GameOfLifeStatistics

import GameInformation.GameInfo
import javax.swing.JDialog
import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConversions
import GameOfLife.Form;
/**
 * Analysis of game statistics 
 */
class ScalaStatistic {
  /**
   * Find count of every figure
   */
  def getStatistic(gameInfo: Array[GameInfo]){
    val tempArray: ArrayBuffer[GameInfo] = ArrayBuffer()
    val statistic = new Array[Int](Form.count)
    val temp = new GameInfo
    var i = 0
    
    while(i < gameInfo.length){
      tempArray += gameInfo(i)
      i+=1;
    }
    i=0;

    for(temp <- tempArray){
      while(i < Form.count){
        statistic(i) += temp.getEveryFigureCount()(i);
        i+=1;
      }
      i=0;
    }
    var maxIndex = findMax(statistic, Form.count)
    new StatisticTable("Statistic", statistic, maxIndex)
  }
  /**
   * Find most common figure figure
   */
  def findMax(stat: Array[Int], size: Int): Int ={
    var indexOfMaxValue = 0
    var max = 0
    var i = 0
    
    while(i < size){
      if(stat(i) > max){
        max = stat(i)
        indexOfMaxValue = i
      }
      i += 1;
    }
    indexOfMaxValue
  }
}