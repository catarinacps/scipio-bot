package BroodWarUnits
import scala.collection.JavaConverters._
import bwapi.Position
import bwapi.{Unit => ScUnit, _}

abstract class Unitates(myself:ScUnit){
  private var me : ScUnit = myself
  def getID():Int = me.getID

  protected def updateData():Unit   //this method is abstract

  def update(newUnit:ScUnit):Unit={
    me=newUnit
    updateData()
  }

  def move(xy:Position):Unit={  //polymorphism by inclusion :) (cuz every unit can move)
    me.move(xy)
  }
}
