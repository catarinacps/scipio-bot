package BroodWarUnits

import bwapi.{Unit => ScUnit, _}
import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer
import bwta.BWTA

import scala.collection.mutable.ListBuffer

trait BWAPIConnection {
    var game: Game = _
    var self: Player = _

    def updateFrame(game: Game, self: Player): Unit = {
        this.game = game
        this.self = self
    }

    // def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]) //this is abstract
}
