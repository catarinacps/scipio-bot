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
    private var workerList: ListBuffer[Operario] = ListBuffer()
    private var idleWorkers: ListBuffer[Operario] = ListBuffer()
    private var gatheringWorkers: ListBuffer[Operario] = ListBuffer()
    private var buildingWorkers: ListBuffer[Operario] = ListBuffer()

    var workerCount:Int = 4

    //this method handles the add or update of all units under this controllers care
    def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]):Unit = { //overrides abstract method
      print("update\n")
        for (i <- ownUnits) {
            if (i.getType == UnitType.Terran_SCV) {
              print("achou scv\n")
                if (workerList.nonEmpty) { //if the list is not empty
                  print("oie\n")
                  print("workers "+workerList+"\n")
                  print("wtf "+workerList.find(_.getID == i.getID)+"\n")
                    if (workerList.find(_.getID == i.getID).isEmpty) { //if its not on the list
                      print("if1\n")
                        val worker = new Operario(i,game)
                        workerList += worker
                        idleWorkers += worker
                      print("fim fi1\n")
                    } else { //if it IS on the list
                      print("else2\n")
                        val wk = workerList.find(_.getID == i.getID).get
                        wk.update(i,game)
                        if (wk.isIdle) {
                            idleWorkers += wk
                        }
                      print("fim else2")

                    }
                } else { //if the list is empty (therefore its not on the list)
                    var worker = new Operario(i,game)
                    workerList += worker
                    idleWorkers += worker
                }
            }
        }
      if (self.minerals() >= 50 && workerCount<10) {
        if(trainUnit(UnitType.Terran_SCV)==true)
          workerCount = workerCount + 1
      }
      print("idk "+workerList+"\n")
      gather(ownUnits,neutralUnits)
    }

    def trainUnit(unitType: UnitType):Boolean = {
      for (i <- self.getUnits.asScala) {
        if (i.getType == UnitType.Terran_Command_Center && !i.isTraining) {
          print("moar workers\n")
          i.train(unitType)
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

}
