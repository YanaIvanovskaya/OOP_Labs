package vehicle.model

import vehicle.CarImpl
import vehicle.MakeOfCar
import vehicle.interfaces.IPoliceCar
import vehicle.interfaces.IPoliceMan

class PoliceCar(
        capacity: Int,
        override val makeOfCar: MakeOfCar
) : CarImpl<IPoliceMan>(capacity), IPoliceCar