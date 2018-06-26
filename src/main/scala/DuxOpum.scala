import BroodWarUnits.{BWAPIConnection, Domus}
import BroodWarUnits.Operarius._
import bwapi.{Unit => ScUnit, _}

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

    var hasBuiltBarrack: Boolean = false

    def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]): Unit = { //Appele touts frames
        print("Resources\n")
        //build order things
        this.ownUnits = ownUnits
        this.neutralUnits = neutralUnits
    }

    def buildBuilding(building: UnitType, worker: Operario): Unit = {
        buildings += new Domus(worker.me, startPos, game, building)
    }

}
