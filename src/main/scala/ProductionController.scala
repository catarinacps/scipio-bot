import bwapi._

import scala.collection.JavaConverters._
import scala.collection.mutable.Stack

class ProductionController {
  var queue : Stack[UnitType] = new Stack[UnitType]()
  var done : Boolean = true
  var ordered : Boolean = false
  var nextBuild : UnitType = _

  def initialization() {
    println("Initializing build order")
    queue.push(bwapi.UnitType.Terran_SCV)
    println("Initializing build order")
    queue.push(bwapi.UnitType.Terran_SCV)
    println("Initializing build order")
    queue.push(bwapi.UnitType.Terran_SCV)
    println("Initializing build order")
    queue.push(bwapi.UnitType.Terran_Refinery )

  }

  def update(game: Game, player: Player){
    val numberOfWorkers: Int = player.getUnits.asScala.filter(_.getType.isWorker).size
    val minerals: Int = player.minerals()
    val workerManager = new WorkerManager(game, player)
    val buildingManager = new BuildingManager(game, player)

    game.drawTextScreen(10, 10, "Number of workers: " + numberOfWorkers)

    if(done){
      done = false
      nextBuild = queue.pop()
    }

    game.drawTextScreen(10, 230, "Next build: " + nextBuild.toString)

    nextBuild match {
      case bwapi.UnitType.Terran_SCV => if(minerals >= 50){workerManager.addWorker(); done=true}
      case bwapi.UnitType.Terran_Refinery  => if(minerals > 100) {println("Hey all, prepare yourselves for the rubberband man");buildingManager.add(bwapi.UnitType.Terran_Refinery ); ordered = true}
                                              if(minerals < 100 && ordered == true){ done=true; ordered = false}
      case _ => println("yay")
    }

  }

}
