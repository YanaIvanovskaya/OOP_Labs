import java.awt.Color
import java.util.*
import javax.swing.JFrame

class Canvas : ICanvas {

    private val mDraw = Draw("Canvas").apply {
        setCanvasSize(CANVAS_WIDTH.toInt(), CANVAS_HEIGHT.toInt())
        setXscale(-CANVAS_WIDTH, CANVAS_WIDTH)
        setYscale(-CANVAS_HEIGHT, CANVAS_HEIGHT)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        penRadius = 0.002
    }

    override fun drawLine(from: Point, to: Point, color: Color) = with(mDraw) {
        penColor = color
        line(from.x, from.y, to.x, to.y)
    }

    override fun fillCircle(center: Point, radius: Double, fillColor: Color) = with(mDraw) {
        penColor = fillColor
        circle(center.x, center.y, radius)
    }

    override fun drawCircle(center: Point, radius: Double, outlineColor: Color) = with(mDraw) {
        penColor = outlineColor
        circle(center.x, center.y, radius)
    }

    override fun fillPolygon(points: Vector<Point>, fillColor: Color) = with(mDraw) {
        penColor = fillColor
        polygon(points.map { it.x }.toDoubleArray(), points.map { it.y }.toDoubleArray())
    }

    override fun drawPolygon(points: Vector<Point>, outlineColor: Color) = with(mDraw) {
        penColor = outlineColor
        polygon(points.map { it.x }.toDoubleArray(), points.map { it.y }.toDoubleArray())
    }

    override fun clear() {
        mDraw.clear()
    }

    companion object {
        private const val CANVAS_HEIGHT = 600.0
        private const val CANVAS_WIDTH = 600.0
    }

}