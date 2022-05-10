import java.awt.Color

abstract class IShape : ICanvasDrawable {
    abstract fun getArea(): Double
    abstract fun getPerimeter(): Double
    abstract fun getOutlineColor(): Color
    abstract fun stringify(): String
    final override fun toString(): String {
        return stringify()
    }

    companion object {
        const val DEFAULT = -1.0
    }

}