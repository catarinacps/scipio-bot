package BroodWarUnits.Operarius
import bwapi.{Unit => ScUnit}
/** The workers data class.
  *
  * It keeps track of a worker and its current job
  */
class Operario (myself:ScUnit) extends BroodWarUnits.Homo(myself){

  override def updateData():Unit={
    //TODO:this
  }
  //TODO: add enums as states to validate delay between issuing commands and taking action
}
