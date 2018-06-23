import BroodWarUnits.BWAPIConnection
import BroodWarUnits.Operarius._
import bwapi._
import scala.util.control.Breaks._

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
/** The worker controller class.
  *
  * It manages how many workers are active, how many minerals
  * are being gathered and any new request for new workers.
  */
class VilicusOperariorum(gameCons: Game, selfCons: Player) extends BWAPIConnection{
  //connect(gameCons,selfCons) //this will run on instantiation
  var workerList : ListBuffer[Operario] = ListBuffer()

  //this method handles the add or update of all units under this controllers care
  def getUnits() ={ //overrides abstract method
    //print("Workers\n")
    //print("wtf "+workerList.filter(_.getID()>=0).isEmpty) //problematic
    val allUnits = self.getUnits.asScala
    val gameUnits = game.neutral.getUnits.asScala
    for(i <- allUnits){
      if(i.getType == UnitType.Terran_SCV){
        if(!workerList.isEmpty){ //if the list is not empty
          if(workerList.filter(_.getID()==i.getID).isEmpty){ //if its not on the list
            workerList+= new Operario(i)
            workerList.last.update(i)
          }else{  //if it IS on the list
            workerList.find(_.getID() == i.getID).get.update(i)
          }
        }else{  //if the list is empty (therefore its not on the list)
          workerList+= new Operario(i)
          workerList.last.update(i)
        }
      }
    }
    for(worker <- workerList){
      if(worker.isIdle){
        print(worker.getID+", we have work for you\n")
        for(i <- gameUnits){
          if(i.getType.isMineralField && i.isVisible(self)){
            print("gather!")
            worker.gather(i)
            break
          }
        }
      }
    }
  }

  //TODO: add part where it issues orders from workerList


}
