import java.awt.Color
import java.util.*

fun main() {

    val result = ShapeXMLParser().parse(
        """
        <shapes>
        
            <rectangle 
                width='30'
                height='20'
                left_top_x='-150.70'
                left_top_y='0.23'
                outline_color='#121212'
                fill_color='#909090'
            />
            
            <circle
                radius='100'
                center_x='30'
                center_y='12'
                outline_color='#222222'
                fill_color='#665544'/>
                
            <triangle
                vertex1_x='10'
                vertex1_y='12'
                vertex2_x='60'
                vertex2_y='2'
                vertex3_x='70'
                vertex3_y='92'
                outline_color='#FFF455'
                fill_color='#662222'/>
                
                
            <line
                outline_color='#000565'
                start_x='70'
                start_y='45'
                end_x='34'
                end_y='88'/>
          
        </shapes>

    """.trimIndent()
    )

    readShapesFromConsole()

}

fun readShapesFromConsole() {
    val parser = ShapeXMLParser()
    val canvas = Canvas()
    println("Enter the shape description:")
    getUserInput {
        try {
            val shapes = parser.parse(it)
            if (shapes.isEmpty()) {
                println("No description")
                return@getUserInput
            }
            println("Shape with biggest area:")
            printShapeInfo(findShapeWithBiggestArea(shapes))
            println(" --- ")
            println("Shape with smallest perimeter:")
            printShapeInfo(findShapeWithSmallestPerimeter(shapes))

            //canvas.clear()
            shapes.forEach { shape ->
                (shape as? ICanvasDrawable)?.draw(canvas)
            }

        } catch (ex: ShapeXMLParser.ParserException) {
            println(ex.description)
        }
    }
}

//fun readShapesFromFile() {
//    val parser = ShapeXMLParser()
//    println("Enter the filepath:")
//
//    getUserInput {
//        try {
//            val shapes = parser.parse(it)
//            if (shapes.isEmpty()) {
//                println("No description found in file")
//                return@getUserInput
//            }
//            println("Shape with biggest area:")
//            printShapeInfo(findShapeWithBiggestArea(shapes))
//            println(" --- ")
//            println("Shape with smallest perimeter:")
//            printShapeInfo(findShapeWithSmallestPerimeter(shapes))
//        } catch (ex: ShapeXMLParser.ParserException) {
//            println(ex.description)
//        }
//    }
//}


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

private fun printShapeInfo(shape: IShape?) {
    shape ?: return
    println(shape)
    println("Area: ${shape.getArea()}")
    println("Perimeter: ${shape.getPerimeter()}")
    (shape as? ISolidShape)?.let {
        println("Fill color: ${it.getFillColor().toHexString()}")
    }
    println("Outline color: ${shape.getOutlineColor().toHexString()}")
}

private fun Color.toHexString() = "#${Integer.toHexString(rgb).drop(2).uppercase()}"
