import bwapi.{Unit => ScUnit, _}

object WorkerManager {
    def trainUnits(commandCenter: ScUnit, units: List[UnitType]): List[Boolean] = units match {
        case Nil => Nil
        case head :: tail => commandCenter.train(head) :: trainUnits(commandCenter, tail)
    }
}
