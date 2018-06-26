package BroodWarUnits

import bwapi.{Game, TilePosition, UnitType, Unit => ScUnit}

import scala.collection.JavaConverters._

class Domus[T](gameCons: Game, param: T) extends Unitas(gameCons, param.asInstanceOf[UnitType]) {
    var pos: TilePosition = _

    def this(worker: ScUnit, startPos: TilePosition, gameCons: Game, param: T) = {
        this(gameCons, param)
        build(worker, startPos)
    }

    def build(worker: ScUnit, startPos: TilePosition): Boolean = {
        pos = game.getBuildLocation(param.asInstanceOf[UnitType], startPos, 100)
        worker.build(param.asInstanceOf[UnitType], pos)
    }

    override def update(myself: ScUnit, game: Game): Unit = {
        this.game = game
        this.me = myself
        //Checks every frame to see if construction has been completed. If so, updates myself
        if (!me.exists) {
            val units = game.getUnitsOnTile(this.pos).asScala
            for (item <- units) {
                if (item.getType == param.asInstanceOf[UnitType]) {
                    this.me = item
                }
            }
        }
    }
}
