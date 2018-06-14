import bwapi.{Unit => ScUnit, _}
import bwta.BWTA

import scala.collection.JavaConverters._

class Scipio extends DefaultBWListener {
    val mirror = new Mirror()
    var game: Game = _
    var self: Player = _
    var military: VilicusMilitum = _
    var workers: VilicusOperarius = _
    var resources: VilicusOpibus = _


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
        military = new VilicusMilitum(game,self)
        workers = new VilicusOperarius(game,self)
        resources = new VilicusOpibus(game,self)
        //if we are not doing any setup for the controllers we might as well rework the constructors
    }

    override def onFrame(): Unit = {
        //game.setTextSize(10);
        game.drawTextScreen(10, 10, "Playing as " + self.getName + " - " + self.getRace)
        /*
        self.getUnits.asScala
            .filter(_.getType == UnitType.Terran_Command_Center && self.minerals >= 50)
            .foreach(_.train(UnitType.Terran_SCV))

        self.getUnits.asScala
            .filter(_.getType.isWorker)
            .filter(_.isIdle)
            .foreach { worker =>
                val closestMineral = game.neutral.getUnits.asScala
                    .filter(_.getType.isMineralField)
                    .map(mineral => (mineral.getDistance(worker), mineral))
                    .sortBy(_._1)
                    .map(_._2)
                    .headOption

                closestMineral.foreach(worker.gather)
            }*/
        military.connect(game,self)
        workers.connect(game,self)
        resources.connect(game,self)    //we have to reconnect because there's no such thing as "pass by reference" here ;( any way to make those vars global?

    }
}

object Scipio {
    def main(args: Array[String]): Unit =
        new Scipio().run()
}
