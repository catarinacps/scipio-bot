package BroodWarUnits.Aedificium
import bwapi.{Game, TilePosition, UnitType}

import scala.collection.JavaConverters._
import bwapi.{Game, Unit => ScUnit, _}

class SupplyDepot(worker: ScUnit, startPos: TilePosition, gameCons:Game) extends BroodWarUnits.Domus(worker, startPos,UnitType.Terran_Supply_Depot,gameCons){
  override def update(newUnit: bwapi.Unit, game: Game): Unit = {
    me=newUnit
    this.game=game
    //Checks every frame to see if construction has been completed. If so, updates myself
    if (!myself.exists()) {
      val units = game.getUnitsOnTile(this.pos).asScala
      for (item <- units) {
        if (item.getType == UnitType.Terran_Supply_Depot) {
          this.myself = item
        }
      }
    }
  }
}
