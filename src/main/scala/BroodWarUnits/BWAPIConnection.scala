package BroodWarUnits
import bwapi.{Unit => ScUnit, _}
import bwta.BWTA
trait BWAPIConnection {
  var game: Game = _
  var self: Player = _
  def connect(game: Game,self: Player):Unit = {
    this.game=game
    this.self=self
  }
}
