package BroodWarUnits.Militum

import bwapi.{Game, UnitType, Unit => ScUnit}

class Soldier(myself: ScUnit, gameCons: Game) extends BroodWarUnits.Homo(gameCons, myself.getType) {
    def update(myself: ScUnit, game: Game): Unit = {
        this.game = game
        this.me = myself
        print("f")
    }
}
