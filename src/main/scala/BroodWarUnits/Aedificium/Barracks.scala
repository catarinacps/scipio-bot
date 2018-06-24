package BroodWarUnits.Aedificium

import bwapi.{Game, Unit => ScUnit, _}

class Barracks(worker: ScUnit, game: Game, startPos: TilePosition) extends BroodWarUnits.Domus {
    var pos: TilePosition = game.getBuildLocation(UnitType.Terran_Barracks, startPos, 100)
    var myself: ScUnit = _

    def this(worker: ScUnit, game: Game, startPos: TilePosition) {
        this(worker, game, startPos)
        worker.build(UnitType.Terran_Barracks, pos)
        //myself = algo
    }

    def updateData(game: Game):Unit = {
        //Checks every frame to see if construction has been completed. If so, updates myself
        if (!myself.exists()) {
            val units = game.getUnitsOnTile(this.pos)
            for (item <- units) {
                if (item.getType == UnitType.Terran_Barracks) {
                    this.myself = item
                }
            }
        }
    }

}
