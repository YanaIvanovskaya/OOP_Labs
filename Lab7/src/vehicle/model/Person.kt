package vehicle.model

import vehicle.interfaces.IPerson
import vehicle.interfaces.IPoliceMan
import vehicle.interfaces.IRacer

class Person(override val name: String) : IPerson

class Racer(
        override val name: String,
        override val awardsCount: Int
) : IRacer

class PoliceMan(
        override val name: String,
        override val departmentName: String
) : IPoliceMan

