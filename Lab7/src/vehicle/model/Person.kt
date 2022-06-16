package vehicle.model

import vehicle.interfaces.IPerson
import vehicle.interfaces.IPoliceMan
import vehicle.interfaces.IRacer

open class Person(override val name: String) : IPerson {
    override fun sayHello() {
       println("Hello from $name")
    }
}

class Racer(
        override val name: String,
        override val awardsCount: Int
) : IRacer, Person(name)

class PoliceMan(
        override val name: String,
        override val departmentName: String
) : IPoliceMan, Person(name)

