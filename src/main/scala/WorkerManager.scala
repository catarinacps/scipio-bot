import bwapi.{Game, Player, UnitType}
import scala.collection.JavaConverters._

class WorkerManager(game: Game, player: Player) {
  def addWorker(){
      player.getUnits.asScala
        .filter(u => (u.getType == UnitType.Terran_Command_Center)&&(!u.isTraining))  //Builds a worker in each command center! Must be reworked!
        .head.train(UnitType.Terran_SCV)
  }
}
