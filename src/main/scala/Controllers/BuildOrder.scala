package Controllers

import BroodWarUnits.Workers.Worker
import BroodWarUnits.{Building, Unit}
import bwapi.{Game, Player, UnitType}

import scala.collection.mutable.Queue

class BuildOrder(gameCons: Game) {
    private var buildOrder: Queue[Unit] = Queue()
    private val game: Game = gameCons
    private var lock: Boolean = false

    buildOrder.enqueue(new Worker(game))
    buildOrder.enqueue(new Building(game, UnitType.Terran_Barracks))
    buildOrder.enqueue(new Building(game, UnitType.Terran_Supply_Depot))
    buildOrder.enqueue(new Building(game, UnitType.Terran_Supply_Depot))

    def canDo(self: Player): Option[Unit] = {
        if (buildOrder.isEmpty || lock) {
            return None
        }
        if (buildOrder.front.canDo(self)) {
            lock = true
            Some(buildOrder.dequeue())
        } else {
            None
        }
    }

    def unlockBuildOrder(): Unit = {
        lock = false
    }

    def isLocked(): Boolean = {
        lock
    }

}
