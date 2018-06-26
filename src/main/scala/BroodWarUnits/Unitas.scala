package BroodWarUnits

import bwapi.{Game, Player, UnitType, Unit => ScUnit}

abstract class Unitas(gameCons: Game, param: UnitType) {
    var me: ScUnit = _
    var game: Game = gameCons
    val ut:UnitType = param
    def getID: Int = me.getID

    def update(myself: ScUnit, game: Game): Unit //this is abstract

    def canDo(self :Player): Boolean ={
        println(ut.mineralPrice)
        ut.mineralPrice <= self.minerals
    }

}
