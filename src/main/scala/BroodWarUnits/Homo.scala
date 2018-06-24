package BroodWarUnits
import bwapi.{Position, Unit => ScUnit}

abstract class Homo(myself:ScUnit) extends BroodWarUnits.Unitas{
  //not sure if we really need this class but it looks cool
  def updateData():Unit   //this method is still abstract


  def move(xy:Position):Unit={  //polymorphism by inclusion :) (cuz every unit can move)
    me.move(xy)
  }

}
