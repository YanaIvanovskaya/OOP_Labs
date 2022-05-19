import java.awt.Color

abstract class IShape : ICanvasDrawable {
    abstract fun getArea(): Double
    abstract fun getPerimeter(): Double
    abstract fun getOutlineColor(): Color
    abstract fun stringify(): String
    final override fun toString(): String {
        return stringify()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is IShape) {
            other.getOutlineColor() == getOutlineColor()
        } else false
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    companion object {
        const val DEFAULT = -1.0
    }

}