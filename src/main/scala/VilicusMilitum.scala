import bwapi.{Unit => ScUnit, _}
import bwta.BWTA
import BroodWarUnits.{BWAPIConnection, Domum, Militum}
/** The soldier controller class.
  *
  * It manages how many soldier are active, how many soldiers
  * are being trained and any new request for new soldiers. And patrols.
  */
class VilicusMilitum(gameCons: Game,selfCons: Player) extends BWAPIConnection{
  connect(gameCons,selfCons) //this will run on instantiation
  def startPatrolAt(building:Domum):Unit ={ //assigns a unit to patrol this building
    //TODO: this
  }
}
