import bwapi.{Unit => ScUnit, _}

import scala.collection.mutable._

/**
  * The Workers namespace
  *
  * It gathers functions that say how many workers are active, how many minerals
  * are being gathered and any new request for new workers.
  */
object Workers {
    import Jobs._

    var idleWorkers: List[ScUnit] = _
    var miningWorkers: List[ScUnit] = _
    var gasWorkers: List[ScUnit] = _

    def controller(minerals: Buffer[ScUnit], gasRefs: Buffer[ScUnit])(wenum: Jobs): List[ScUnit] => List[Any] = {
        def applyGather(resources: Buffer[ScUnit])(workerList: List[ScUnit]): List[Boolean] = workerList match {
            case Nil => Nil
            case head :: tail =>
                head.gather(resources
                    .map(r => (r.getDistance(head), r))
                    .sortBy(_._1)
                    .map(_._2)
                    .head) :: applyGather(resources)(tail)
        }

        wenum match {
            case Jobs.mineral => return applyGather(minerals)
            case Jobs.gas => return applyGather(gasRefs)
            //case Jobs.build => return applyBuild()
        }
    }

    def separateIdleWorkers(workers: List[ScUnit], openJobs: Int, jobs: (Jobs, Jobs)): List[(ScUnit, Jobs)] = workers match {
        case Nil => Nil
        case head :: tail => if (workers.size > openJobs - 1)
            (head, jobs._1) :: separateIdleWorkers(tail, openJobs, jobs)
        else
            (head, jobs._2) :: separateIdleWorkers(tail, openJobs - 1, jobs)
    }

    def build(buildOrder: List[(UnitType, TilePosition)], workerList: List[ScUnit]): List[Boolean] = workerList match {
        case Nil => Nil
        case head :: tail => head.build(buildOrder.head._1, buildOrder.head._2) :: build(buildOrder.tail, tail)
    }

    /*def update(): Unit = {
        val idleWorkers: List[ScUnit] = player.getUnits.asScala
            .filter(_.getType.isWorker)
            .filter(_.isIdle).toList
        distributeJobs(idleWorkers)
    }

    def getIdleWorker(): ScUnit = {
        val idleWorkers: List[ScUnit] = player.getUnits.asScala
            .filter(_.getType.isWorker)
            .filter(_.isIdle).toList
        println("Success!")
        idleWorkers.head
    }

    def distributeJobs(idleWorkers: List[ScUnit]): Unit = {
        val refineryExists: Boolean = game.neutral.getUnits.asScala.exists(_.getType.isRefinery)
        if (miningWorkers <= gasWorkers || !refineryExists) {
            miningWorkers += 1
            setWorkerToCollect(idleWorkers, _.getType.isMineralField)
        }
        else {
            gasWorkers += 1
            setWorkerToCollect(idleWorkers, _.getType.isRefinery)
        }
    }*/

    /*def setWorkerToCollect(idleWorkers: List[ScUnit], f: ScUnit => Boolean): Unit = {
        if (idleWorkers.nonEmpty) {
            val closestResource = game.neutral.getUnits.asScala
                .filter(f)
                .map(resource => (resource.getDistance(idleWorkers.head), resource))
                .sortBy(_._1)
                .map(_._2).head
            idleWorkers.head.gather(closestResource)

            distributeJobs(idleWorkers.tail)
        }
    }

    def build(unitType: UnitType, tilePosition: TilePosition): Unit = {
        println("Ordering")
        if (player.getUnits.asScala
            .filter(_.getType.isWorker)
            .filter(_.isIdle).toList.isEmpty) {

            player.getUnits.asScala
                .filter(_.getType.isWorker).head.build(unitType, tilePosition)
            println("Ordered to build " + unitType + " in " + tilePosition)
        } else {
            print("Yeah")
            player.getUnits.asScala
                .filter(_.getType.isWorker)
                .filter(_.isIdle).toList.head.build(unitType, tilePosition)
            println("Ordered")
        }
    }*/
}
