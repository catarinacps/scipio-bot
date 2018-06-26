package BroodWarUnits

import bwapi.{Game, Position, UnitType, Unit => ScUnit}

abstract class Homo(gameCons: Game,  param: UnitType) extends BroodWarUnits.Unitas(gameCons, param) {

    def move(xy: Position): Unit = { //polymorphism by inclusion :) (cuz every unit can move)
        me.move(xy)
    }
}
