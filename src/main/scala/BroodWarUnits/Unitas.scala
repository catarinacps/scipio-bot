package BroodWarUnits

import bwapi.{Game, Unit => ScUnit}

abstract class Unitas(gameCons: Game) {
    var me: ScUnit = _
    var game: Game = gameCons

    def getID: Int = me.getID

    def update(myself: ScUnit, game: Game): Unit //this is abstract

}
