package Duces

import BroodWarUnitas.Operarii.Operario
import BroodWarUnitas.{Aedificium, Unitas}
import bwapi.{Game, Player, UnitType}

import scala.collection.mutable.Queue

class Structor(gameCons: Game) {
    private var buildOrder: Queue[Unitas] = Queue()
    private val game: Game = gameCons
    private var lock: Boolean = false

    buildOrder.enqueue(new Operario(game))
    buildOrder.enqueue(new Aedificium(game, UnitType.Terran_Barracks))
    buildOrder.enqueue(new Aedificium(game, UnitType.Terran_Supply_Depot))
    buildOrder.enqueue(new Aedificium(game, UnitType.Terran_Supply_Depot))

    def canDo(self: Player): Option[Unitas] = {
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
