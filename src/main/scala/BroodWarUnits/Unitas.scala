package BroodWarUnits

import bwapi.{Game, Position, Unit => ScUnit}

import scala.collection.JavaConverters._

abstract class Unitas(gameCons:Game){
    var me: ScUnit = _
    var game:Game=gameCons
    def getID: Int = me.getID

    def update(game:Game):Unit //this is abstract

}
