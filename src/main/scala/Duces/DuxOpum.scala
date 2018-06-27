package Duces

import BroodWarUnitas.Operarii._
import BroodWarUnitas.Aedificium
import bwapi.{Unit => ScUnit, _}

import scala.collection.mutable.{Buffer, ListBuffer}

/** The resource controller class.
  *
  * It manages how many resources are there, how many buildings
  * exist and any new request for new buildings. Not sure if we need this but it looks cool.
  */

import BroodWarUnitas.Unitas

class DuxOpum(startPos: TilePosition) extends BWAPIConnection {
    private var ownUnits: Buffer[ScUnit] = Buffer()
    private var neutralUnits: Buffer[ScUnit] = Buffer()
    private var buildings: ListBuffer[Unitas] = ListBuffer()

    def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]): Unit = {
        this.ownUnits = ownUnits
        this.neutralUnits = neutralUnits
    }

    def buildBuilding(building: UnitType, worker: Operario): Unit = {
        buildings += new Aedificium(worker.me, startPos, game, building)
    }

}
