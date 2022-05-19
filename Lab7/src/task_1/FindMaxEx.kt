package task_1

data class Sportsman(
        val name: String,
        val weight: Int,
        val height: Int
)

fun <T> List<T>.findMaxEx(comparator: Comparator<T>): T? {
    if (isEmpty()) {
        return null
    }
    var maxValue: T = get(0)

    forEach {
        if (comparator.compare(it, maxValue)) {
            maxValue = it
        }
    }

    return maxValue
}

fun interface Comparator<T> {
    fun compare(first: T, second: T): Boolean
}

//fun someFun(first: Sportsman, second: Sportsman): Boolean {
//    return false
//}

fun main() {
    val test = listOf(
            Sportsman("Jack", 90, 280),
            Sportsman("Jack2", 179, 173),
            Sportsman("Jack3", 80, 183),
            Sportsman("Jack4", 110, 210))

    val result = test.findMaxEx { first, second ->
        first.weight > second.weight
    }
    //val result = test.findMaxEx(::someFun)

    // Передача null, пустого списка
    //Если есть несколько одинаковых максимальных значений то возвращаем последнее из них
    println(result)

}