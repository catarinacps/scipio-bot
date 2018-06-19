package BroodWarUnits
import bwapi.{Unit => ScUnit, _}

abstract class Hominum (myself:ScUnit) extends BroodWarUnits.Unitates (myself){
  //not sure if we really need this class but it looks cool
  def updateData():Unit   //this method is still abstract

}
