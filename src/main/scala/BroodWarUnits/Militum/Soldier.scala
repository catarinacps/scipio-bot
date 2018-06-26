package BroodWarUnits.Militum

import bwapi.{Game, Unit => ScUnit}

class Soldier(myself: ScUnit,gameCons:Game) extends BroodWarUnits.Homo(myself,gameCons) {
    def update(myself: ScUnit,game: Game): Unit = {
        this.game=game
        this.me=myself
        print("f")
    }
}
