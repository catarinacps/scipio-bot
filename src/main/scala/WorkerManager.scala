import bwapi.{Game, Player, UnitType}
import scala.collection.JavaConverters._

class WorkerManager(game: Game, player: Player) {
  def addWorker(){
      player.getUnits.asScala
        .filter(_.getType == UnitType.Terran_Command_Center)  //Builds a worker in each command center! Must be reworked!
        .filter(_.isTraining==false)//gotta join these two filters... because its prettier
        .foreach(_.train(UnitType.Terran_SCV))
  }
}
