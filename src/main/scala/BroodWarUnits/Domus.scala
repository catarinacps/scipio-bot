package BroodWarUnits
import scala.collection.JavaConverters._
import bwapi.{Game, TilePosition, UnitType, Unit => ScUnit}

class Domus[T](worker: ScUnit, startPos: TilePosition, gameCons:Game, param:T) extends Unitas(gameCons){
  var pos: TilePosition = game.getBuildLocation(param.asInstanceOf[UnitType], startPos, 100)

  def this(worker: ScUnit, game : Game, startPos : TilePosition, param:T){
    this(worker, game, startPos)
    worker.build(param.asInstanceOf[UnitType], pos)
  }

  override def update(game:Game)= {
    this.game=game
    //Checks every frame to see if construction has been completed. If so, updates myself
    if (!me.exists()) {
      val units = game.getUnitsOnTile(this.pos).asScala
      for (item <- units) {
        if (item.getType == param.asInstanceOf[UnitType]) {
          this.me = item
        }
      }
    }
  }
}
