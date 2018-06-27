package BroodWarUnits.Workers

import bwapi.{Unit => ScUnit, _}

/** The workers data class.
  *
  * It keeps track of a worker and its current job
  */
class Worker(gameCons: Game) extends BroodWarUnits.Person(gameCons, UnitType.Terran_SCV) {

    def this(myself: ScUnit, gameCons: Game) {
        this(gameCons)
        this.me = myself
    }

    def update(myself: ScUnit, game: Game): Unit = {
        this.game = game
        this.me = myself
    }

    def isIdle: Boolean = me.isIdle

    def gather(mineral: ScUnit): Boolean = me.gather(mineral)

    //TODO: add enums as states to validate delay between issuing commands and taking action
}
