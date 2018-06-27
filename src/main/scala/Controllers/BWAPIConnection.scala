package Controllers

import bwapi._

trait BWAPIConnection {
    var game: Game = _
    var self: Player = _

    def updateFrame(game: Game, self: Player): Unit = {
        this.game = game
        this.self = self
    }

    // def update(ownUnits: Buffer[ScUnit], neutralUnits: Buffer[ScUnit]) //this is abstract
}
