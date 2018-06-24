package BroodWarUnits
import bwapi.{Unit => ScUnit, _}
import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer
import bwta.BWTA

import scala.collection.mutable.ListBuffer
trait BWAPIConnection {
  var game: Game = _
  var self: Player = _
  def connect(game: Game, self : Player ,ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]):Unit = {
    this.game = game
    this.self = self
    getUnits(ownUnits, neutralUnits)
  }
  def getUnits(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit])  //this is abstract
}
