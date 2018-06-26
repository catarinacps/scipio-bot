import BroodWarUnits.Operarius.Operario
import BroodWarUnits.{Domus, Unitas}
import bwapi.{Game, Player, UnitType}

import scala.collection.mutable.Queue

class BuildOrder(gameCons: Game) {
    private var kiwi: Queue[Unitas] = Queue()
    private val game:Game = gameCons
    private var lock:Boolean = false

    kiwi.enqueue(new Operario(game))
    kiwi.enqueue(new Domus(game, UnitType.Terran_Barracks))
    kiwi.enqueue(new Domus(game, UnitType.Terran_Supply_Depot))
    kiwi.enqueue(new Domus(game, UnitType.Terran_Supply_Depot))

    def canDo(self:Player): Option[Unitas] = {
        print("Maybe build \n")
        if(kiwi.isEmpty || lock){
          print("AAaa")
          return None
        }
        if (kiwi.front.canDo(self)) {
            print("Will build!\n")
            print(kiwi)
            lock = true
            Some(kiwi.dequeue())
        } else {
            print("Wont build\n")
            print(kiwi)
            None
        }
    }

    def unlockBuildOrder(): Unit ={
        lock = false
    }

    def isLocked(): Boolean ={
        lock
    }

}
