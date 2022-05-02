sealed interface Value {
    object Void : Value
    object NaN : Value
    data class Number(val value: Double) : Value
}