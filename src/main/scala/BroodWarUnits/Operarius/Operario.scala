package BroodWarUnits.Operarius

import bwapi.{Unit => ScUnit, _}

/** The workers data class.
  *
  * It keeps track of a worker and its current job
  */
class Operario(myself: ScUnit,gameCons:Game) extends BroodWarUnits.Homo(myself,gameCons) {

    def update(newUnit: ScUnit,game: Game): Unit = {
        me=newUnit
        this.game=game
    }

    def isIdle: Boolean = me.isIdle

    def gather(mineral: ScUnit): Boolean = me.gather(mineral)

    //TODO: add enums as states to validate delay between issuing commands and taking action
}
