package vehicle.model

import vehicle.CarImpl
import vehicle.MakeOfCar
import vehicle.interfaces.IPerson
import vehicle.interfaces.ITaxi

class Taxi(
        capacity: Int,
        override val makeOfCar: MakeOfCar
) : CarImpl<IPerson>(capacity), ITaxi