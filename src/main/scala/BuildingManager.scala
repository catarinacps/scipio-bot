import bwapi._

import scala.collection.JavaConverters._
class BuildingManager (game: Game, player: Player){

  def findClosestGeyser(): Unit={
/*    if(!geysersList.isEmpty) {
      println("Atta boy")
      val start: Position = player.getStartLocation.toPosition
      val dist: Int = geysersList.head.getDistance(start)

      if (dist < minDist) {
        println("dist: " + dist)
        findClosestGeyser(geysersList.tail, dist, geysersList.indexOf(geysersList.head))
      }
      else {
        println("far!")
        findClosestGeyser(geysersList.tail, minDist, index)
      }
    }
    else{
      if(index != -1){
        println("Index:" + geysersList(0).toString)
        val pos : TilePosition =  geysersList(index).getTilePosition
        println("Done~ pos:" + pos.toString)
        return pos
      }
      else
        print("?:::")
      val pos : TilePosition =  geysersList(index).getTilePosition
      println("Done~ pos:" + pos.toString)
      return pos

    }*/
    val start: Position = player.getStartLocation.toPosition
    val geyser : Unit = game.neutral.getUnits.asScala.filter(_.getType == bwapi.UnitType.Resource_Vespene_Geyser)
                          .map(resource => (resource.getDistance(start), resource))
                          .sortBy(_._1)
                          .map(_._2).head
    return geyser
  }

  def add(building : UnitType) {
    println("In building mang")
    val work = new WorkerController(game, player)
    println("Now trying to match this: " + building.toString)
    building match{
      case bwapi.UnitType.Terran_Refinery  =>
        println("Tryin to build a refinary")
        val closestGeyser : Unit = findClosestGeyser()
        println("nice")
        work.build(building , closestGeyser.getTilePosition)
      case bwapi.UnitType.Terran_Supply_Depot =>
        println("Tryin to build a supply depot")
        work.build(building,game.getBuildLocation(building,player.getStartLocation,100000000))
        println("nice")
        //someone rewrite this please
      case _ => println ("oops")
    }
  }


}
