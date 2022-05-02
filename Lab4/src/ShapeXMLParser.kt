import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import org.xml.sax.InputSource
import shape.Circle
import shape.LineSegment
import shape.Rectangle
import shape.Triangle
import java.awt.Color
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class ShapeXMLParser {

    fun parse(source: String): List<IShape> {
        if (source.isEmpty()) {
            return listOf()
        }

        val document = createXMLDocument(source)

        val rootNode = document.firstChild
        if (rootNode.nodeName != "shapes") {
            throw ParserException(ErrorType.INVALID_ROOT_ELEMENT, "Expected root element <shapes></shapes>")
        }

        val shapes = mutableListOf<IShape>()
        val childNodes = rootNode.childNodes
        for (index in 0 until childNodes.length) {
            val child = childNodes.item(index)
            if (child.nodeType == Node.TEXT_NODE) continue

            shapes.add(createShapeFromNode(child))
        }
        return shapes
    }

    private fun createXMLDocument(source: String): Document {
        try {
            val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val xml = InputSource(StringReader(source))
            return docBuilder.parse(xml)
        } catch (ex: Throwable) {
            throw ParserException(ErrorType.INVALID_XML_FILE, "The source is not valid XML")
        }
    }

    private fun createShapeFromNode(node: Node): IShape {
        val attrs = node.attributes

        val outlineColor = runCatching {
            Color.decode(attrs.getAttributeByName("outline_color"))
        }.getOrElse { throw ParserException(ErrorType.INCORRECT_COLOR, "Incorrect color") }

        val fillColor = runCatching {
            val hexColor = attrs.getAttributeByName("fill_color")
            hexColor ?: return@runCatching Color.BLACK
            Color.decode(hexColor)
        }.getOrElse { throw ParserException(ErrorType.INCORRECT_COLOR, "Incorrect color") }

        return when (node.nodeName) {
            "rectangle" -> {
                if (attrs.length != 6) {
                    val msg = "Tag <rectangle> expected 6 required attributes"
                    throw ParserException(ErrorType.INCORRECT_NUMBER_OF_ATTRIBUTES, msg)
                }
                val leftTop = createPoint(attrs, "left_top_x", "left_top_y")
                Rectangle(
                    outlineColor = outlineColor,
                    fillColor = fillColor,
                    leftTop = leftTop,
                    width = attrs.getDoubleAttributeByName("width"),
                    height = attrs.getDoubleAttributeByName("height")
                )
            }
            "circle" -> {
                if (attrs.length != 5) {
                    val msg = "Tag <circle> expected 5 required attributes"
                    throw ParserException(ErrorType.INCORRECT_NUMBER_OF_ATTRIBUTES, msg)
                }
                val center = createPoint(attrs, "center_x", "center_y")
                Circle(
                    center = center,
                    outlineColor = outlineColor,
                    fillColor = fillColor,
                    radius = attrs.getDoubleAttributeByName("radius")
                )
            }
            "triangle" -> {
                if (attrs.length != 8) {
                    val msg = "Tag <triangle> expected 8 required attributes"
                    throw ParserException(ErrorType.INCORRECT_NUMBER_OF_ATTRIBUTES, msg)
                }
                val vertex1 = createPoint(attrs, "vertex1_x", "vertex1_y")
                val vertex2 = createPoint(attrs, "vertex2_x", "vertex2_y")
                val vertex3 = createPoint(attrs, "vertex3_x", "vertex3_y")
                Triangle(
                    outlineColor = outlineColor,
                    fillColor = fillColor,
                    vertex1 = vertex1,
                    vertex2 = vertex2,
                    vertex3 = vertex3
                )
            }
            "line" -> {
                if (attrs.length != 5) {
                    val msg = "Tag <line> expected 5 required attributes"
                    throw ParserException(ErrorType.INCORRECT_NUMBER_OF_ATTRIBUTES, msg)
                }
                val start = createPoint(attrs, "start_x", "start_y")
                val end = createPoint(attrs, "end_x", "end_y")
                LineSegment(
                    start = start,
                    end = end,
                    outlineColor = outlineColor
                )
            }
            else -> throw ParserException(ErrorType.UNSUPPORTED_SHAPE, "Unexpected tag: ${node.nodeName}")
        }
    }

    private fun createPoint(
        attrs: NamedNodeMap,
        nameX: String,
        nameY: String
    ) = Point(
        x = attrs.getDoubleAttributeByName(nameX),
        y = attrs.getDoubleAttributeByName(nameY)
    )

    private fun NamedNodeMap.getAttributeByName(name: String): String? {
        return getNamedItem(name)?.childNodes?.item(0)?.textContent
    }

    private fun NamedNodeMap.getDoubleAttributeByName(name: String): Double {
        return runCatching {
            getAttributeByName(name)?.toDouble() ?: 0.0
        }.getOrElse { throw ParserException(ErrorType.INCORRECT_NUMBER, "Attribute $name expected type number") }
    }

    class ParserException(val type: ErrorType, val description: String) : Throwable()

    enum class ErrorType {
        UNSUPPORTED_SHAPE,
        INVALID_ROOT_ELEMENT,
        INVALID_XML_FILE,
        INCORRECT_NUMBER_OF_ATTRIBUTES,
        INCORRECT_NUMBER,
        INCORRECT_COLOR
    }

}

