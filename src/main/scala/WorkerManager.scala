import bwapi.{Unit => ScUnit, _}
import scala.collection.JavaConverters._

object WorkerManager {
    def trainUnit(commandCenter: ScUnit, unit: UnitType): Boolean = {
        return commandCenter.train(unit)
    }
}
