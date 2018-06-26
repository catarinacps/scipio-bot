import BroodWarUnits.BWAPIConnection
import BroodWarUnits.Operarius._
import bwapi.{Unit => ScUnit, _}

import scala.collection.JavaConverters._
import scala.collection.mutable.{Buffer, ListBuffer}
import scala.util.control.Breaks._

/** The worker controller class.
  *
  * It manages how many workers are active, how many minerals
  * are being gathered and any new request for new workers.
  */
class DuxOperariorum extends BWAPIConnection {

    //connect(gameCons,selfCons) //this will run on instantiation
    private var workerList: ListBuffer[Operario] = ListBuffer()
    private var idleWorkers: ListBuffer[Operario] = ListBuffer()
    private var gatheringWorkers: ListBuffer[Operario] = ListBuffer()
    private var buildingWorkers: ListBuffer[Operario] = ListBuffer()

    //this method handles the add or update of all units under this controllers care
    def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]): Unit = { //overrides abstract method
        print("update\n")
        for (i <- ownUnits) {
            if (i.getType == UnitType.Terran_SCV) {
                if (workerList.nonEmpty) { //if the list is not empty
                    if (!workerList.exists(_.getID == i.getID)) { //if its not on the list
                        val worker = new Operario(i, game)
                        workerList += worker
                        idleWorkers += worker
                    } else { //if it IS on the list
                        val wk = workerList.find(_.getID == i.getID).get
                        wk.update(i, game)
                        if (wk.isIdle) {
                            idleWorkers += wk
                        }

                    }
                } else { //if the list is empty (therefore its not on the list)
                    var worker = new Operario(i, game)
                    workerList += worker
                    idleWorkers += worker
                }
            }
        }
        gather(ownUnits, neutralUnits)
    }

    def trainUnit(op: Operario): Boolean = {
        for (i <- self.getUnits.asScala) {
            if (i.getType == UnitType.Terran_Command_Center && !i.isTraining) {
                print("moar workers\n")
                i.train(op.ut)
                return true
            }
        }
        return false
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
                        gatheringWorkers = gatheringWorkers.distinct
                        idleWorkers = idleWorkers.distinct
                        break
                    }
                }
            }
        }
    }

    def getGatheringWorkers: ListBuffer[Operario] = this.gatheringWorkers

    def getIdleWorkers: ListBuffer[Operario] = this.idleWorkers

}
