package task_1

fun <T> List<T>.findMaxWith(lessThan: LessThan<T>): T? {
    if (isEmpty()) {
        return null
    }
    var maxValue: T = get(0)

    forEach {
        if (lessThan(it, maxValue)) {
            maxValue = it
        }
    }

    return maxValue
}

fun interface LessThan<T> {
    operator fun invoke(first: T, second: T): Boolean
}