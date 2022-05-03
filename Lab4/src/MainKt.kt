import java.awt.Color
import java.io.File
import java.text.DecimalFormat
import java.util.*

fun main() {

//    readShapesFromConsole()

    readShapesFromFile()

    //C:\Users\yana-\All_Projects_Intellij_Idea\OOP_Labs\Lab4\test_src\shapes.xml

}

private fun readShapesFromFile() {
    println("Enter the filepath:")
    Scanner(System.`in`).use { userInput ->
        val filepath = userInput.nextLine()
        if (filepath.isEmpty()) return

        val shapesDescription = StringBuilder()

        runCatching {
            Scanner(File(filepath)).use {
                while (it.hasNext()) {
                    shapesDescription.append("\n").append(it.next())
                }
            }
            processShapeDescription(shapesDescription.toString())
        }.getOrElse { println("File not found or cannot be read") }
    }
}

fun processShapeDescription(description: String) {
    val parser = ShapeXMLParser()
    var canvas: ICanvas? = null
    try {
        val shapes = parser.parse(description)
        if (shapes.isEmpty()) {
            println("No description")
            return
        }

        printSummary(shapes)

        canvas = canvas ?: Canvas()
        drawShapes(canvas, shapes)

    } catch (ex: ParserException) {
        println(ex.description)
    }
}

fun readShapesFromConsole() {
    println("Enter the shape description:")
    getUserInput(::processShapeDescription)
}

private fun drawShapes(canvas: ICanvas, shapes: List<IShape>) {
    canvas.clear()
    shapes.forEach { shape ->
        (shape as? ICanvasDrawable)?.draw(canvas)
    }
}

private fun printSummary(shapes: List<IShape>) {
    val formatter = DecimalFormat("#.###")
    println("Shape with biggest area:")
    printShapeInfo(findShapeWithBiggestArea(shapes), formatter)
    println(" --- ")
    println("Shape with smallest perimeter:")
    printShapeInfo(findShapeWithSmallestPerimeter(shapes), formatter)
}

fun findShapeWithSmallestPerimeter(shapes: List<IShape>): IShape? {
    return shapes.minByOrNull {
        it.getPerimeter()
    }
}

fun findShapeWithBiggestArea(shapes: List<IShape>): IShape? {
    return shapes.maxByOrNull {
        it.getArea()
    }
}

private fun getUserInput(onEach: (String) -> Unit) {
    var isInterrupted = false
    Scanner(System.`in`).use { userInput ->
        while (!isInterrupted) {
            val input = userInput.nextLine()
            if (input.isEmpty() || input == "Exit") {
                isInterrupted = true
            } else onEach(input)
        }
    }
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

private fun Color.toHexString() = "#${Integer.toHexString(rgb).drop(2).uppercase()}"

