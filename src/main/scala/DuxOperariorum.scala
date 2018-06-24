import BroodWarUnits.BWAPIConnection
import BroodWarUnits.Operarius._
import bwapi.{Unit => ScUnit, _}

import scala.util.control.Breaks._
import scala.collection.JavaConverters._
import scala.collection.mutable.{Buffer, ListBuffer}

/** The worker controller class.
  *
  * It manages how many workers are active, how many minerals
  * are being gathered and any new request for new workers.
  */
class DuxOperariorum extends BWAPIConnection {

    //connect(gameCons,selfCons) //this will run on instantiation
    var workerList: ListBuffer[Operario] = ListBuffer()
    var idleWorkers: ListBuffer[Operario] = ListBuffer()
    var gatheringWorkers: ListBuffer[Operario] = ListBuffer()
    var buildingWorkers: ListBuffer[Operario] = ListBuffer()

    //this method handles the add or update of all units under this controllers care
    def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]):Unit = { //overrides abstract method
        for (i <- ownUnits) {
            if (i.getType == UnitType.Terran_SCV) {
                if (workerList.nonEmpty) { //if the list is not empty
                    if (workerList.find(_.getID == i.getID).isEmpty) { //if its not on the list
                        val worker = new Operario(i,game)
                        workerList += worker
                        workerList.last.update(i,game)
                        idleWorkers += worker

                    } else { //if it IS on the list
                        val wk = workerList.find(_.getID == i.getID).get
                        wk.update(i,game)
                        if (wk.isIdle) {
                            idleWorkers += wk
                        }

                    }
                } else { //if the list is empty (therefore its not on the list)
                    workerList += new Operario(i,game)
                    workerList.last.update(i,game)
                }
            }
        }
      if (self.minerals() >= 50) {
        trainUnit(UnitType.Terran_SCV)
      }
      gather(ownUnits,neutralUnits)
    }

    def trainUnit(unitType: UnitType):Unit = {
      for (i <- self.getUnits.asScala) {
        if (i.getType == UnitType.Terran_Command_Center && !i.isTraining) {
          print("moar workers\n")
          i.train(unitType)
        }
      }
    }

    def gather(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]): Unit = {
        for (worker <- workerList) {
            if (worker.isIdle) {
                print(worker.getID + ", we have work for you\n")
                for (i <- neutralUnits) {
                    if (i.getType.isMineralField && i.isVisible(self)) {
                        print("gather!")
                        worker.gather(i)
                        gatheringWorkers += worker
                        idleWorkers -= worker
                        gatheringWorkers=gatheringWorkers.distinct
                        idleWorkers=idleWorkers.distinct
                        break
                    }
                }
            }
        }
    }
    def getGatheringWorkers: ListBuffer[Operario] = this.gatheringWorkers
    def getIdleWorkers: ListBuffer[Operario] = this.idleWorkers

    //TODO: add part where it issues orders from workerList
}
