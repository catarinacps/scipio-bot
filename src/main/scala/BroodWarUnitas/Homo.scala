package BroodWarUnitas

import bwapi.{Game, Position, UnitType}

abstract class Homo(gameCons: Game,  param: UnitType) extends BroodWarUnitas.Unitas(gameCons, param) {

    def move(xy: Position): Unit = { //polymorphism by inclusion :) (cuz every unit can move)
        me.move(xy)
    }
}
