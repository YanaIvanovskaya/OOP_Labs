operator fun Int.plus(other: Rational): Rational {
    return other.plus(this)
}

operator fun Int.minus(other: Rational): Rational {
    return Rational(this).minus(other)
}

operator fun Int.div(other: Rational): Rational {
    return Rational(this).div(other)
}

operator fun Int.times(other: Rational): Rational {
    return Rational(this).times(other)
}

operator fun Int.compareTo(other: Rational): Int {
    return Rational(this).compareTo(other)
}