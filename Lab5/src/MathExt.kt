import kotlin.math.absoluteValue

fun greatestCommonDivisorOf(value1: Int, value2: Int): Int {
    var nod = value1.absoluteValue
    var tmpValue2 = value2.absoluteValue

    while (tmpValue2 != 0) {
        val tmp = nod % tmpValue2
        nod = tmpValue2
        tmpValue2 = tmp
    }
    return nod
}

fun smallestCommonMultipleOf(value1: Int, value2: Int): Int {
    val nod = greatestCommonDivisorOf(value1, value2)
    return (value1 * value2) / nod
}