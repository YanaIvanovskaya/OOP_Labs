sealed interface Value {
    object Void : Value
    object Nan : Value
    data class Number(val value: Double) : Value
}