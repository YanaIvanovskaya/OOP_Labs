import java.awt.Color

interface IShape : ICanvasDrawable {
    fun getArea(): Double
    fun getPerimeter(): Double
    fun getOutlineColor(): Color
    fun stringify(): String
}