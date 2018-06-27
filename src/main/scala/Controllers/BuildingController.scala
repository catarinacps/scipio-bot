package Controllers

import BroodWarUnits.Workers._
import BroodWarUnits.Building
import bwapi.{Unit => ScUnit, _}

import scala.collection.mutable.{Buffer, ListBuffer}

/** The resource controller class.
  *
  * It manages how many resources are there, how many buildings
  * exist and any new request for new buildings. Not sure if we need this but it looks cool.
  */

import BroodWarUnits.Units

class BuildingController(startPos: TilePosition) extends BWAPIConnection {
    private var ownUnits: Buffer[ScUnit] = Buffer()
    private var neutralUnits: Buffer[ScUnit] = Buffer()
    private var buildings: ListBuffer[Units] = ListBuffer()

    def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]): Unit = {
        this.ownUnits = ownUnits
        this.neutralUnits = neutralUnits
    }

    def buildBuilding(building: UnitType, worker: Worker): Unit = {
        buildings += new Building(worker.me, startPos, game, building)
    }

}
