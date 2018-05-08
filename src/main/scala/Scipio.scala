import Jobs._
import bwapi.{Unit => ScUnit, _}
import bwta.BWTA

import scala.annotation.tailrec
import scala.collection.JavaConverters._
import scala.collection.mutable._

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
        Scipio.idleWorkers = self.getUnits.asScala
            .filter(u => u.getType.isWorker && u.isIdle)
            .toList
        Scipio.mineralList = game.neutral.getUnits.asScala
            .filter(_.getType.isMineralField)
        Scipio.vespeneList = game.neutral.getUnits.asScala
            .filter(_.getType.isRefinery)

        Scipio.commandCenterList = self.getUnits.asScala
            .filter(u => u.getType == UnitType.Terran_Command_Center && !u.isTraining)

        if (!Scipio.vespeneRefinery) {
            Scipio.vespeneRefinery = game.neutral.getUnits.asScala
                .exists(_.getType.isRefinery)
        }

        if (Scipio.idleWorkers.nonEmpty) {
            val separatedIdle: List[(ScUnit, Jobs)] = Scipio.separateIdleWorkers(Scipio.idleWorkers, 3-Scipio.gasWorkers.size, (mineral, gas))
            val newMineralWorkers: List[ScUnit] = separatedIdle.filter(_._2 == mineral).map(_._1)
            val newGasWorkers: List[ScUnit] = separatedIdle.filter(_._2 == gas).map(_._1)

            val workerController = Workers.controller(Scipio.mineralList, Scipio.vespeneList)
            val gatherMinerals = workerController(Jobs.mineral)
            val gatherGas = workerController(Jobs.gas)

            gatherMinerals(newMineralWorkers)
            gatherGas(newGasWorkers)

            Scipio.miningWorkers = Scipio.miningWorkers:::newMineralWorkers
            Scipio.gasWorkers = Scipio.gasWorkers:::newGasWorkers
        }

        production.update(game, self)
    }
}

object Scipio {
    var idleWorkers: List[ScUnit] = _
    var miningWorkers: List[ScUnit] = _
    var gasWorkers: List[ScUnit] = _

    var vespeneList: Buffer[ScUnit] = _
    var mineralList: Buffer[ScUnit] = _

    var commandCenterList: Buffer[ScUnit] = _

    var vespeneRefinery: Boolean = _

    def main(args: Array[String]): Unit =
        new Scipio().run()

    def separateIdleWorkers(workers: List[ScUnit], openJobs: Int, jobs: (Jobs, Jobs)): List[(ScUnit, Jobs)] = workers match {
        case Nil => Nil
        case head::tail => if (workers.size > openJobs - 1)
            (head, jobs._1)::separateIdleWorkers(tail, openJobs, jobs)
        else
            (head, jobs._2)::separateIdleWorkers(tail, openJobs-1, jobs)
    }
}
