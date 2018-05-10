import bwapi.{Unit => ScUnit, _}

import scala.collection.mutable.Buffer

object BuildingManager {
    def findClosestGeyser(pos: Position, geysers: Buffer[ScUnit]): ScUnit = {
        return geysers.map(r => (r.getDistance(pos), r)).sortBy(_._1).map(_._2).head
    }

    def add(building: UnitType) {
        println("In building mang")
        val work = new WorkerController(game, player)
        println("Now trying to match this: " + building.toString)
        building match {
            case bwapi.UnitType.Terran_Refinery =>
                println("Tryin to build a refinary")
                val closestGeyser: Unit = findClosestGeyser()
                println("nice")
                work.build(building, closestGeyser.getTilePosition)
            case bwapi.UnitType.Terran_Supply_Depot =>
                println("Tryin to build a supply depot")
                work.build(building, game.getBuildLocation(building, player.getStartLocation, 100000000))
                println("nice")
            //someone rewrite this please
            case _ => println("oops")
        }
    }

    def getBuildPosition(orders: List[UnitType], game: Game): List[(UnitType, TilePosition)] = orders match {
        case Nil => Nil
        case head :: tail => head match {
            case bwapi.UnitType.Terran_Refinery =>
                (head, findClosestGeyser(Scipio.startLocation, Scipio.geyserList).getTilePosition) :: getBuildPosition(tail, game)
            case bwapi.UnitType.Terran_Supply_Depot =>
                (head, game.getBuildLocation(head, Scipio.startLocation.toTilePosition)) :: getBuildPosition(tail, game)
            case _ => getBuildPosition(tail, game)
        }
    }
}
