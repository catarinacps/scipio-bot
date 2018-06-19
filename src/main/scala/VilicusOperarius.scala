import bwapi.{Unit => ScUnit, _}
import bwta.BWTA
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import BroodWarUnits.Operarius._

import BroodWarUnits.{BWAPIConnection, Operarius}
/** The worker controller class.
  *
  * It manages how many workers are active, how many minerals
  * are being gathered and any new request for new workers.
  */
class VilicusOperarius(gameCons: Game,selfCons: Player) extends BWAPIConnection{
  connect(gameCons,selfCons) //this will run on instantiation
  var workerList : ListBuffer[Operario] = _

  //this method handles the add or update of all units under this controllers care
  def getUnits(): Unit ={ //overrides abstract method
    val allUnits = self.getUnits.asScala
    for(i <- allUnits){
      if(i.getType == UnitType.Terran_SCV){
        if(!workerList.exists(_.getID() == i.getID)){
          workerList+= new Operario(i)
          workerList.last.update(i)
        }else{
          workerList.find(_.getID() == i.getID).get.update(i)
        }
      }
    }
    //TODO: add part where it issues orders from workerList
  }


}
