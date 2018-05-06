import bwapi.{Unit => ScUnit, _}
import bwta.BWTA

class Scipio extends DefaultBWListener {
    val mirror = new Mirror()
    var game: Game = _
    var self: Player = _

    var production = new ProductionController()

    def run(): Unit = {
        mirror.getModule.setEventListener(this)
        mirror.startGame()
    }

    override def onUnitCreate(unit: ScUnit): Unit = {
        System.out.println("New unit " + unit.getType)
    }

    override def onStart(): Unit = {
        game = mirror.getGame
        self = game.self()

        //Use BWTA to analyze map
        //This may take a few minutes if the map is processed first time!
        System.out.println("Analyzing map...")
        BWTA.readMap()
        BWTA.analyze()
        System.out.println("Map data ready")
        production.initialization()
    }

    override def onFrame(): Unit = {
      val workers = new WorkerController(game,self)

      production.update(game,self)
      workers.update()
    }
}

object Scipio {
    def main(args: Array[String]): Unit =
        new Scipio().run()
}
