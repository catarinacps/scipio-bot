import BroodWarUnits.{BWAPIConnection, Domus}
import bwapi.{Unit => ScUnit, _}

import scala.collection.mutable.Buffer
/** The soldier controller class.
  *
  * It manages how many soldier are active, how many soldiers
  * are being trained and any new request for new soldiers. And patrols.
  */
class DuxMilitum(gameCons: Game, selfCons: Player) extends BWAPIConnection{
  //connect(gameCons,selfCons) //this will run on instantiation
  def startPatrolAt(building:Domus)={ //assigns a unit to patrol this building
    //TODO: this
  }

  def getUnits(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]) ={
    print("Military\n")
  }
}
