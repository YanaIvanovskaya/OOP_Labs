import java.io.InputStream
import java.io.OutputStream
import java.util.Scanner
import kotlin.math.sign

class Rational {

    private var mNumerator: Int
    private var mDenominator: Int

    constructor(numerator: Int, denominator: Int) {
        require(denominator != 0) {
            "Cannot create rational number with zero denominator"
        }
        mNumerator = numerator
        mDenominator = denominator
        if (denominator.sign == -1) {
            mNumerator *= -1
            mDenominator *= -1
        }
        normalize()
    }

    constructor() : this(numerator = 0, denominator = 1)
    constructor(value: Int) : this(numerator = value, denominator = 1)

    fun getNumerator(): Int {
        return mNumerator
    }

    fun getDenominator(): Int {
        return mDenominator
    }

    fun toDouble(): Double {
        return mNumerator.toDouble() / mDenominator
    }

    fun toCompoundFraction(): Pair<Int, Rational> {
        val firstPart = mNumerator / mDenominator
        val secondPart = Rational(
                numerator = mNumerator % mDenominator,
                denominator = mDenominator
        )
        return firstPart to secondPart
    }

    fun writeTo(outputStream: OutputStream) {
        outputStream.write(this.toString().toByteArray())
    }

    operator fun unaryMinus(): Rational {
        return Rational(mNumerator * -1, mDenominator)
    }

    operator fun unaryPlus(): Rational {
        return Rational(mNumerator, mDenominator)
    }

    operator fun plus(other: Rational): Rational {
        val operands = toCommonDenominator(this, other)
        return Rational(
                numerator = operands.first.mNumerator + operands.second.mNumerator,
                denominator = operands.first.mDenominator
        )
    }

    operator fun plus(other: Int): Rational {
        return plus(Rational(other))
    }

    operator fun minus(other: Rational): Rational {
        val operands = toCommonDenominator(this, other)
        return Rational(
                numerator = operands.first.mNumerator - operands.second.mNumerator,
                denominator = operands.first.mDenominator
        )
    }

    operator fun minus(other: Int): Rational {
        return minus(Rational(other))
    }

    operator fun times(other: Rational): Rational {
        return Rational(
                numerator = mNumerator * other.mNumerator,
                denominator = mDenominator * other.mDenominator
        )
    }

    operator fun times(other: Int): Rational {
        return times(Rational(other))
    }

    operator fun div(other: Rational): Rational {
        return Rational(
                numerator = mNumerator * other.mDenominator,
                denominator = mDenominator * other.mNumerator
        )
    }

    operator fun div(other: Int): Rational {
        return div(Rational(other))
    }

    operator fun plusAssign(other: Rational) {
        val result = plus(other)
        mNumerator = result.mNumerator
        mDenominator = result.mDenominator
    }

    operator fun plusAssign(other: Int) {
        plusAssign(Rational(other))
    }

    operator fun minusAssign(other: Rational) {
        val result = minus(other)
        mNumerator = result.mNumerator
        mDenominator = result.mDenominator
    }

    operator fun minusAssign(other: Int) {
        minusAssign(Rational(other))
    }

    operator fun timesAssign(other: Rational) {
        val result = times(other)
        mNumerator = result.mNumerator
        mDenominator = result.mDenominator
    }

    operator fun timesAssign(other: Int) {
        timesAssign(Rational(other))
    }

    operator fun divAssign(other: Rational) {
        val result = div(other)
        mNumerator = result.mNumerator
        mDenominator = result.mDenominator
    }

    operator fun divAssign(other: Int) {
        divAssign(Rational(other))
    }

    operator fun compareTo(other: Rational): Int {
        val value1 = toDouble()
        val value2 = other.toDouble()
        return when {
            value1 == value2 -> 0
            value1 < value2 -> -1
            else -> 1
        }
    }

    operator fun compareTo(other: Int): Int {
        return compareTo(Rational(other))
    }

    override operator fun equals(other: Any?): Boolean {
        return when (other) {
            is Rational -> mNumerator == other.mNumerator && mDenominator == other.mDenominator
            is Int -> mNumerator == other && mDenominator == 1
            else -> false
        }
    }

    override fun toString(): String {
        return "$mNumerator/$mDenominator"
    }

    override fun hashCode(): Int {
        var result = mNumerator
        result = 31 * result + mDenominator
        return result
    }

    private fun normalize(): Rational {
        val gcd = greatestCommonDivisorOf(mNumerator, mDenominator)
        mNumerator /= gcd
        mDenominator /= gcd
        return this
    }

    companion object {

        private fun toCommonDenominator(first: Rational, second: Rational): Pair<Rational, Rational> {
            val denominator = smallestCommonMultipleOf(first.mDenominator, second.mDenominator)
            val processedFirst = Rational(1, denominator).apply {
                mNumerator = first.mNumerator * (denominator / first.mDenominator)
            }
            val processedSecond = Rational(1, denominator).apply {
                mNumerator = second.mNumerator * (denominator / second.mDenominator)
            }
            return processedFirst to processedSecond
        }

        fun readFrom(inputStream: InputStream): Rational? {
            val strRepresentation: String
            Scanner(inputStream).use {
                strRepresentation = if (it.hasNextLine()) it.next() else ""
            }
            return when {
                strRepresentation.matches(Regex("""(-?)\d+/\d+""")) -> {
                    val numerator = strRepresentation.substringBefore("/").toInt()
                    val denominator = strRepresentation.substringAfter("/").toInt()
                    Rational(numerator, denominator)
                }
                strRepresentation.matches(Regex("""(-?)\d+""")) ->
                    Rational(strRepresentation.toInt())
                else -> null
            }
        }

    }

}