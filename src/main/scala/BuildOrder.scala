import BroodWarUnits.{Domus, Unitas}
import bwapi.{Game, UnitType}

import scala.collection.mutable.Queue

class BuildOrder(game: Game) {
    private val kiwi: Queue[Unitas] = Queue()
    kiwi.enqueue(new Domus(game, UnitType.Terran_Barracks))
    kiwi.enqueue(new Domus(game, UnitType.Terran_Supply_Depot))
    kiwi.enqueue(new Domus(game, UnitType.Terran_Supply_Depot))

    def canDo: Option[Unitas] = {
        if (game canMake kiwi.front.me.getType) {
            Some(kiwi.dequeue())
        } else {
            None
        }
    }

}
