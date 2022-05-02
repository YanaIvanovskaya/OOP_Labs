import java.awt.Color

interface IShape {
    fun getArea(): Double
    fun getPerimeter(): Double
    fun getOutlineColor(): Color
    override fun toString(): String
}