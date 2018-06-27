package BroodWarUnitas.Milites

import bwapi.{Game, Unit => ScUnit}

class Miles(myself: ScUnit, gameCons: Game) extends BroodWarUnitas.Homo(gameCons, myself.getType) {

    def update(myself: ScUnit, game: Game): Unit = {
        this.game = game
        this.me = myself
    }
}
