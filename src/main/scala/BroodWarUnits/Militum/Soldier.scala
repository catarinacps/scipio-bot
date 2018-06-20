package BroodWarUnits.Militum
import bwapi.{Unit => ScUnit}

class Soldier(myself:ScUnit) extends BroodWarUnits.Homo(myself) {
  def updateData():Unit={
    print("f")
  }
}
