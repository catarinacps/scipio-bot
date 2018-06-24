package BroodWarUnits

import bwapi.{Game, Position, Unit => ScUnit}

abstract class Homo(myself: ScUnit,gameCons:Game) extends BroodWarUnits.Unitas(gameCons){
    //not sure if we really need this class but it looks cool

    def move(xy: Position): Unit = { //polymorphism by inclusion :) (cuz every unit can move)
        me.move(xy)
    }
}
