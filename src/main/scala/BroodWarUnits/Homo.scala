package BroodWarUnits
import bwapi.{Unit => ScUnit}

abstract class Homo(myself:ScUnit) extends BroodWarUnits.Unitas (myself){
  //not sure if we really need this class but it looks cool
  def updateData():Unit   //this method is still abstract

}
