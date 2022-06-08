package task_1

fun <T> List<T>.findMaxWith(comparator: Comparator<T>): T? {
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