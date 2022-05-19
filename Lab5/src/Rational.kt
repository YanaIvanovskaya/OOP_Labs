// переопределение == невозможно
// чем заменить переопределение << >>
// чем заменить реализацию строки
class Rational {

    private var mNumerator: Int
    private var mDenominator: Int

    constructor(numerator: Int, denominator: Int) {
        require(denominator != 0) {
            "Cannot create rational number with zero denominator"
        }
        this.mNumerator = numerator
        this.mDenominator = denominator
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

    operator fun unaryMinus(): Rational {
        mNumerator *= -1
        return this.normalize()
    }

    operator fun unaryPlus(): Rational {
        return this.normalize()
    }

    operator fun plus(other: Rational): Rational {
        val operands = toCommonDenominator(other)
        val result = Rational(
                numerator = operands.first.mNumerator + operands.second.mNumerator,
                denominator = operands.first.mDenominator
        )
        return result.normalize()
    }

    operator fun plus(other: Int): Rational {
        return plus(Rational(other))
    }

    operator fun minus(other: Rational): Rational {
        val operands = toCommonDenominator(other)
        val result = Rational(
                numerator = operands.first.mNumerator - operands.second.mNumerator,
                denominator = operands.first.mDenominator
        )
        return result.normalize()
    }

    operator fun minus(other: Int): Rational {
        return minus(Rational(other))
    }

    operator fun times(other: Rational): Rational {
        return Rational(
                numerator = mNumerator * other.mNumerator,
                denominator = mDenominator * other.mDenominator
        ).normalize()
    }

    operator fun times(other: Int): Rational {
        return times(Rational(other))
    }

    operator fun div(other: Rational): Rational {
        return Rational(
                numerator = mNumerator * other.mDenominator,
                denominator = mDenominator * other.mNumerator
        ).normalize()
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

    // dont work for int
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

    private fun toCommonDenominator(other: Rational): Pair<Rational, Rational> {
        var thisCopy = this
        var otherCopy = other
        when {
            (mDenominator % other.mDenominator) == 0 -> {
                otherCopy = otherCopy.multiplyTo(mDenominator / other.mDenominator)
            }
            (other.mDenominator % mDenominator) == 0 -> {
                thisCopy = thisCopy.multiplyTo(other.mDenominator / mDenominator)
            }
            else -> {
                val denominator = smallestCommonMultipleOf(this.mDenominator, other.mDenominator)
                thisCopy = thisCopy.multiplyTo(denominator / mDenominator)
                otherCopy = otherCopy.multiplyTo(denominator / other.mDenominator)
            }
        }
        return thisCopy to otherCopy
    }

    private fun multiplyTo(multiplier: Int): Rational {
        return Rational(
                numerator = mNumerator * multiplier,
                denominator = mDenominator * multiplier
        )
    }

    private fun normalize(): Rational {
        val nod = greatestCommonDivisorOf(mNumerator, mDenominator)
        mNumerator /= nod
        mDenominator /= nod
        return this
    }

}