package BroodWarUnits.Aedificium
import scala.collection.JavaConverters._
import bwapi.{Game, Unit => ScUnit, _}

class Barracks(worker: ScUnit, startPos: TilePosition,gameCons:Game) extends BroodWarUnits.Domus(worker, startPos,UnitType.Terran_Barracks,gameCons){
    def update(newUnit: ScUnit,game: Game):Unit = {
      me=newUnit
      this.game=game
        //Checks every frame to see if construction has been completed. If so, updates myself
        if (!myself.exists()) {
            val units = game.getUnitsOnTile(this.pos).asScala
            for (item <- units) {
                if (item.getType == UnitType.Terran_Barracks) {
                    this.myself = item
                }
            }
        }
    }

}
