import java.awt.Color
import java.text.DecimalFormat

class ShapeEvaluator(
    private val parser: IShapeParser,
    private val canvas: ICanvas
) {

    fun evaluate(source: String) {
        try {
            val shapes = parser.parse(source)
            if (shapes.isEmpty()) {
                println("No description found")
                return
            }

            printSummary(shapes)

            drawShapes(canvas, shapes)

        } catch (ex: ParserException) {
            println(ex.description)
        }
    }

    // как лучше размещать статические функции: сделать их свободными но приватными внутри файла или использовать  companion object
    companion object {

        fun findShapeWithSmallestPerimeter(shapes: List<IShape>): IShape? {
            return shapes.minByOrNull(IShape::getPerimeter)
        }

        fun findShapeWithBiggestArea(shapes: List<IShape>): IShape? {
            return shapes.maxByOrNull(IShape::getArea)
        }

        private fun drawShapes(canvas: ICanvas, shapes: List<IShape>) {
            canvas.clear()
            shapes.forEach { shape -> shape.draw(canvas) }
        }

        private fun printSummary(shapes: List<IShape>) {
            val formatter = DecimalFormat("#.###")
            println("Shape with biggest area:")
            printShapeInfo(findShapeWithBiggestArea(shapes), formatter)
            println(" --- ")
            println("Shape with smallest perimeter:")
            printShapeInfo(findShapeWithSmallestPerimeter(shapes), formatter)
        }

        private fun printShapeInfo(shape: IShape?, formatter: DecimalFormat) {
            shape ?: return
            println(shape)
            println("Area: ${formatter.format(shape.getArea())}")
            println("Perimeter: ${formatter.format(shape.getPerimeter())}")
            (shape as? ISolidShape)?.let {
                println("Fill color: ${it.getFillColor().toHexString()}")
            }
            println("Outline color: ${shape.getOutlineColor().toHexString()}")
        }

        private fun Color.toHexString() =
            "#${Integer.toHexString(rgb).drop(2).uppercase().ifEmpty { "TRANSPARENT" }}"
    }

}