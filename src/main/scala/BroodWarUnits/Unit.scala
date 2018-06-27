package BroodWarUnits

import bwapi.{Game, Player, UnitType, Unit => ScUnit}

abstract class Unit(gameCons: Game, unitType: UnitType) {
    protected var me: ScUnit = _
    protected var game: Game = gameCons
    protected val ut: UnitType = unitType

    def getID: Int = me.getID

    def update(myself: ScUnit, game: Game): Unit //this is abstract

    def canDo(self: Player): Boolean = {
        ut.mineralPrice <= self.minerals
    }

}
