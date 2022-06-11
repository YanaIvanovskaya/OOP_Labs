package vehicle.interfaces

interface IPerson {
    val name: String
}

interface IRacer : IPerson {
    val awardsCount: Int
}

interface IPoliceMan : IPerson {
    val departmentName: String
}