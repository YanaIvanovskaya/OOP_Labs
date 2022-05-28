import java.io.File
import java.util.*

fun main() {
    val evaluator = ShapeEvaluator(
            parser = ShapeXMLParser(),
            canvas = Canvas()
    )
/*    println("Enter the shape description or filepath with description:")
    getUserInput { source ->
        val shapesDescription = tryReadDescriptionFromFile(source)
        evaluator.evaluate(shapesDescription ?: source)
    }*/
    val shapesDescription = tryReadDescriptionFromFile("Lab4/test_src/picture.xml")
    shapesDescription?.let { evaluator.evaluate(it) }
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

private fun tryReadDescriptionFromFile(filepath: String): String? {
    if (!File(filepath).exists()) return null

    val shapesDescription = StringBuilder()

    return runCatching {
        Scanner(File(filepath)).use {
            while (it.hasNext()) {
                shapesDescription.append("\n").append(it.next())
            }
        }
        shapesDescription.toString()
    }.getOrNull()
}