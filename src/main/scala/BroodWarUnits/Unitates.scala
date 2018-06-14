package BroodWarUnits
import scala.collection.JavaConverters._
import bwapi.Position

abstract class Unitates (myID:Int) extends BWAPIConnection{
  connect(game,self)
  protected var ID: Int = _ //Can we make this work? It would help in identifying units
  def getID: Int = this.ID

  def move(xy:Position):Unit={  //polymorphism by inclusion :) (cuz every unit can move)
    //TODO:this
    /*var theUnits = self.getUnits.asScala
    while(theUnits.)*/

  }
}
