import BroodWarUnits.BWAPIConnection
import BroodWarUnits.Operarius._
import bwapi._

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
/** The worker controller class.
  *
  * It manages how many workers are active, how many minerals
  * are being gathered and any new request for new workers.
  */
class VilicusOperariorum(gameCons: Game, selfCons: Player) extends BWAPIConnection{
  //connect(gameCons,selfCons) //this will run on instantiation
  var workerList : ListBuffer[Operario] = _

  //this method handles the add or update of all units under this controllers care
  def getUnits() ={ //overrides abstract method
    print("Workers\n")
    val allUnits = self.getUnits.asScala
    for(i <- allUnits){
      if(i.getType == UnitType.Terran_SCV){
        print("found a worker\n")
        if(workerList.exists(_.getID == i.getID)){
          print("does not exist\n")
          workerList+= new Operario(i)
          workerList.last.update(i)
          print("it does now\n")
        }else{
          print("Will update\n")
          workerList.find(_.getID() == i.getID).get.update(i)
          print("Updated\n")
        }
      }
    }
  }

  //TODO: add part where it issues orders from workerList


}
