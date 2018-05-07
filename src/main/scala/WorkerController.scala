import bwapi._

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer


/** The worker controller class.
  *
  * It manages how many workers are active, how many minerals
  * are being gathered and any new request for new workers.
  */
class WorkerController(game: Game, player: Player) {
  var miningWorkers: Int = 0
  var gasWorkers: Int = 0


  def update(){
    val idleWorkers : List[Unit]  =  player.getUnits.asScala
      .filter(_.getType.isWorker)
      .filter(_.isIdle).toList
    distributeJobs(idleWorkers)
  }

  def getIdleWorker(): Unit ={
    val idleWorkers : List[Unit]  =  player.getUnits.asScala
      .filter(_.getType.isWorker)
      .filter(_.isIdle).toList
    println("Success!")
     return idleWorkers.head
  }

  def distributeJobs(idleWorkers: List[Unit]){
      val refineryExists: Boolean = game.neutral.getUnits.asScala.filter(_.getType.isRefinery) != Nil
      if(miningWorkers <= gasWorkers || !refineryExists){
        miningWorkers += 1
        setWorkerToCollect(idleWorkers, _.getType.isMineralField)
      }
      else{
        gasWorkers += 1
        setWorkerToCollect(idleWorkers, _.getType.isRefinery)
      }

  }

  def setWorkerToCollect(idleWorkers: List[Unit], f: Unit => Boolean) {

    if (idleWorkers.size != 0) {
      val closestResource = game.neutral.getUnits.asScala
        .filter(f)
        .map(resource => (resource.getDistance(idleWorkers.head), resource))
        .sortBy(_._1)
        .map(_._2).head
      idleWorkers.head.gather(closestResource)

      distributeJobs(idleWorkers.tail)
    }
  }

  def build(unitType : UnitType, tilePosition: TilePosition){
    println("Ordering")
    if(player.getUnits.asScala
      .filter(_.getType.isWorker)
      .filter(_.isIdle).toList.isEmpty){

      player.getUnits.asScala
        .filter(_.getType.isWorker).head.build(unitType,tilePosition)
      println("Ordered to build " + unitType + " in " + tilePosition)
    }else{
      print("Yeah")
      player.getUnits.asScala
      .filter(_.getType.isWorker)
      .filter(_.isIdle).toList.head.build(unitType,tilePosition)
      println("Ordered")
    }


  }

}
