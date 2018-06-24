import BroodWarUnits.BWAPIConnection
import bwapi.{Unit => ScUnit, _}
import BroodWarUnits.Aedificium._
import BroodWarUnits.Domus
import BroodWarUnits.Operarius._

import scala.collection.JavaConverters._
import scala.collection.mutable.{Buffer, ListBuffer}

/** The resource controller class.
  *
  * It manages how many resources are there, how many buildings
  * exist and any new request for new buildings. Not sure if we need this but it looks cool.
  */
class DuxOpum(startPos: TilePosition) extends BWAPIConnection {
    //connect(gameCons,selfCons) //this will run on instantiation

    var ownUnits: Buffer[ScUnit] = Buffer()
    var neutralUnits: Buffer[ScUnit] = Buffer()
    var buildings: ListBuffer[Domus] = ListBuffer()

    def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit], idleWorkers: ListBuffer[Operario]):Unit = { //Appele touts frames
        print("Resources\n")
        //build order things
        this.ownUnits = ownUnits
        this.neutralUnits = neutralUnits

        if (self.minerals() >= 100) {
            // pegar worker
            // pegar pos
            buildings += new Barracks(idleWorkers.remove(0), game, startPos)

        }

    }

    def trainUnit(unitType: UnitType):Unit = {
        for (i <- ownUnits) {
            if (i.getType == UnitType.Terran_Command_Center && !i.isTraining) {
                print("moar workers\n")
                i.train(unitType)
            }
        }
    }

    def buildBuilding(unitType: UnitType):Unit = {
        print("refinary\n")
        for (i <- ownUnits) {
            if (i.getType == UnitType.Resource_Vespene_Geyser) {
                for (u <- ownUnits) {
                    print("all units")
                    if (u.getType == UnitType.Terran_SCV && !u.isConstructing) {
                        print("found worker")
                        u.build(unitType, i.getTilePosition)
                    }
                }
            }
        }
    }

}
