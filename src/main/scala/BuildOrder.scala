import bwapi.UnitType

object BuildOrder {
    var orderList: List[UnitType] = _

    def initializeOrders: List[UnitType] = {
        return UnitType.Terran_SCV ::
            UnitType.Terran_SCV ::
            UnitType.Terran_SCV ::
            UnitType.Terran_SCV ::
            UnitType.Terran_Refinery ::
            UnitType.Terran_Supply_Depot
    }
}
