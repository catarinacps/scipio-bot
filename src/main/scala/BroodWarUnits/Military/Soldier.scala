package BroodWarUnits.Military

import bwapi.{Game, Unit => ScUnit}

class Soldier(myself: ScUnit, gameCons: Game) extends BroodWarUnits.Person(gameCons, myself.getType) {

    def update(myself: ScUnit, game: Game): Unit = {
        this.game = game
        this.me = myself
    }
}
