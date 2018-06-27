package BroodWarUnits

import bwapi.{Game, Position, UnitType}

abstract class Person(gameCons: Game, param: UnitType) extends BroodWarUnits.Units(gameCons, param) {

    def move(xy: Position): Unit = { //polymorphism by inclusion :) (cuz every unit can move)
        me.move(xy)
    }
}
