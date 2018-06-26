import BroodWarUnits.BWAPIConnection
import bwapi.{Unit => ScUnit, _}
import BroodWarUnits.Domus
import BroodWarUnits.Operarius._

import scala.collection.JavaConverters._
import scala.collection.mutable.{Buffer, ListBuffer}

/** The resource controller class.
  *
  * It manages how many resources are there, how many buildings
  * exist and any new request for new buildings. Not sure if we need this but it looks cool.
  */
import BroodWarUnits.Unitas
class DuxOpum(startPos: TilePosition) extends BWAPIConnection {
    //connect(gameCons,selfCons) //this will run on instantiation

    var ownUnits: Buffer[ScUnit] = Buffer()
    var neutralUnits: Buffer[ScUnit] = Buffer()
    var buildings: ListBuffer[Unitas] = ListBuffer()

    var hasBuiltBarrack:Boolean = false

    def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit], gatheringWorkers: ListBuffer[Operario]):Unit = { //Appele touts frames
        print("Resources\n")
        //build order things
        this.ownUnits = ownUnits
        this.neutralUnits = neutralUnits
        print("test ")
        print(gatheringWorkers)
        print("\n")

        if(hasBuiltBarrack==false && self.minerals>=150){
            print("entrou\n")
            buildings += new Domus(gatheringWorkers.remove(0).me, startPos,game,UnitType.Terran_Barracks)
            print("passou\n")
            hasBuiltBarrack=true
            print("tchau\n")
        }else if (hasBuiltBarrack==true && self.minerals >= 100) {
            buildings+=new Domus(gatheringWorkers.remove(0).me, startPos,game,UnitType.Terran_Supply_Depot)
        }

    }

    def buildBuilding(building: UnitType, worker: Operario):Unit = {
        buildings += new Domus(worker.me,startPos,game,building)
    }

}
