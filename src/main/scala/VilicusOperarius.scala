import bwapi.{Unit => ScUnit, _}
import bwta.BWTA
import BroodWarUnits.{BWAPIConnection, Operarius}
/** The worker controller class.
  *
  * It manages how many workers are active, how many minerals
  * are being gathered and any new request for new workers.
  */
class VilicusOperarius(gameCons: Game,selfCons: Player) extends BWAPIConnection{
  connect(gameCons,selfCons) //this will run on instantiation



}
