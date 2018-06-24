package BroodWarUnits
import bwapi.{Game, Position, Unit => ScUnit}

import scala.collection.JavaConverters._

abstract class Unitas{
  protected var me : ScUnit = _
  def getID():Int = me.getID

  protected def updateData(game : Game):Unit   //this method is abstract

  def update(newUnit:ScUnit):Unit={
    me=newUnit
    updateData()
  }


}
