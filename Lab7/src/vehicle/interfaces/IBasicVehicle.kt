package vehicle.interfaces

interface IBasicVehicle {
    val placeCount: Int
    val passengerCount: Int

    fun isEmpty(): Boolean
    fun isFull(): Boolean
    fun removeAllPassengers()
}


