import kotlin.math.absoluteValue

fun greatestCommonDivisorOf(value1: Int, value2: Int): Int {
    var gcd = value1.absoluteValue
    var tmpValue2 = value2.absoluteValue

    while (tmpValue2 != 0) {
        val tmp = gcd % tmpValue2
        gcd = tmpValue2
        tmpValue2 = tmp
    }
    return gcd
}

fun smallestCommonMultipleOf(value1: Int, value2: Int): Int {
    val gcd = greatestCommonDivisorOf(value1, value2)
    return (value1 * value2) / gcd
}