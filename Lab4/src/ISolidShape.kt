import java.awt.Color

abstract class ISolidShape : IShape() {
    abstract fun getFillColor(): Color

    override fun equals(other: Any?): Boolean {
        return if (other is ISolidShape) {
            other.getFillColor() == other.getFillColor() && super.equals(other)
        } else false
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

}

