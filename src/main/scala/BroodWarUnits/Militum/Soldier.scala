package BroodWarUnits.Militum

import bwapi.{Game, Unit => ScUnit}

class Soldier(myself: ScUnit,gameCons:Game) extends BroodWarUnits.Homo(myself,gameCons) {
    def update(newUnit: ScUnit,game: Game): Unit = {
        me=newUnit
        this.game=game
        print("f")
    }
}
