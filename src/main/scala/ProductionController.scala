import bwapi._

import scala.collection.JavaConverters._
import scala.collection.mutable.Stack

class ProductionController {
  var queue : Stack[UnitType] = new Stack[UnitType]()
  var done : Boolean = true
  var ordered : Boolean = false
  var work : Int = 0
  var minerals: Int = 100
  var safety: Int = 0
  def initialization() {
    println("Initializing build order")
    queue.push(UnitType.Terran_Refinery)
  }

  def update(game: Game, player: Player){
    val workerManager = new WorkerManager(game, player)
    val buildingManager = new BuildingManager(game, player)
    val numberOfWorkers: Int = player.getUnits.asScala.filter(_.getType.isWorker).size
    def currentlyBuilding() :Int = player.getUnits.asScala.count(_.getRemainingBuildTime>0)
    //def currentlyTraining() :Int = player.getUnits.asScala.count(_.isTraining)
    game.drawTextScreen(10, 10, "Number of workers: " + numberOfWorkers)

    //if(currentlyBuilding + currentlyTraining < work){ //useless
    if(currentlyBuilding < work){  //finished building something so it's safe to check resources//finished building something so it's safe to check resources
      minerals=player.minerals()
      //work=currentlyBuilding()+currentlyTraining()
      work=currentlyBuilding()
    }else{
      //println(currentlyBuilding())
      safety+=1
      if(safety>100) {
        safety=-1
        minerals=player.minerals()
      }
    }
    //add something to queue based on resources count; if safety<0 there may be resources discrepancies
    //placeholder
    if(game.canMake(UnitType.Terran_SCV))
      queue.push(UnitType.Terran_SCV)
    else if(game.canMake(UnitType.Terran_Supply_Depot))
      queue.push(UnitType.Terran_Supply_Depot)
    //end placeholder
    issueBuildingOrders(game,player)
    //do something based on what's still in queue (order was maintained)
    //placeholder
    queue.clear()
    //end placeholder
  }
  def issueBuildingOrders(game: Game, player: Player):scala.Unit ={
    val workerManager = new WorkerManager(game, player)
    val buildingManager = new BuildingManager(game, player)
    var nextBuild : UnitType = UnitType.None
    if(queue.nonEmpty) {
      nextBuild = queue.pop()
      game.drawTextScreen(10, 230, "Next build: " + nextBuild.toString)
      if(game.canMake(nextBuild)) {
        nextBuild match {
          case UnitType.Terran_SCV => if (minerals >= 50) {
            workerManager.addWorker(); minerals -= 50; work+=1
          }
          case UnitType.Terran_Refinery => if (minerals >= 100) {
            println("Rubberband man incoming!"); buildingManager.add(nextBuild); minerals -= 100;work += 1
          }
          //{println("Hey all, prepare yourselves for the rubberband man");buildingManager.add(nextBuild); ordered = true}
          //if(minerals < 100 && ordered == true){ done=true; ordered = false}
          case UnitType.Terran_Supply_Depot => if(minerals>=100){
            println("MOAR SPACE"); buildingManager.add(nextBuild); minerals -= 100; work += 1
          }
          case _ => println("yay")
        }
        issueBuildingOrders(game,player)
      }
      else{
        //try the next unit in queue, pushes back
        issueBuildingOrders(game,player)
        queue.push(nextBuild)
      }
    }
  }
}
