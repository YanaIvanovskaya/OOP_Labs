import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import shape.Circle
import shape.LineSegment
import shape.Rectangle
import shape.Triangle
import java.awt.Color

internal class ShapeXMLParserTest {

    private val parser = ShapeXMLParser()

    @Test
    @DisplayName("Если передано пустое описание фигур, вернется пустой список фигур")
    fun case_1() {
        Assertions.assertEquals(listOf<IShape>(), parser.parse(""))
    }

    @Test
    @DisplayName("Если корневой элемент XML описания не тег <shapes></shapes>, выбрасывается исключение")
    fun case_2() {
        val xml = """
            <tag>
                 <line
                    outline_color='#000565'
                    start_x='70'
                    start_y='45'
                    end_x='34'
                    end_y='88'/>
            </tag>
        """
        Assertions.assertThrows(ShapeXMLParser.ParserException::class.java) {
            parser.parse(xml)
        }
    }

    @Test
    @DisplayName("Если  XML описание содержит элемент с тегом, не являющимся описанием фигуры, выбрасывается исключение ")
    fun case_3() {
        val xml = """
            <shapes>
                 <oval
                    outline_color='#000565'
                    start_x='70'
                    start_y='45'
                    end_x='34'
                    end_y='88'/>
            </shapes>
        """
        Assertions.assertThrows(ShapeXMLParser.ParserException::class.java) {
            parser.parse(xml)
        }
    }

    @Test
    @DisplayName("Создание ноды rectangle соответствует созданию объекту класса shape.Rectangle")
    fun case_4() {
        val xml = """
            <shapes>
                 <rectangle 
                    width='30'
                    height='20'
                    left_top_x='-150.70'
                    left_top_y='0.23'
                    outline_color='#121212'
                    fill_color='#909090'
                />
            </shapes>
        """
        val expected = listOf<IShape>(
            Rectangle(
                width = 30.0,
                height = 20.0,
                leftTop = Point(-150.7, 0.23),
                outlineColor = Color.decode("#121212"),
                fillColor = Color.decode("#909090")
            )
        )
        Assertions.assertEquals(expected, parser.parse(xml))
    }

    @Test
    @DisplayName("Создание ноды circle соответствует созданию объекту класса shape.Circle")
    fun case_5() {
        val xml = """
            <shapes>
                 <circle
                    radius='100'
                    center_x='30'
                    center_y='12'
                    outline_color='#222222'
                    fill_color='#665544'
                />
            </shapes>
        """
        val expected = listOf<IShape>(
            Circle(
                radius = 100.0,
                center = Point(30.0, 12.0),
                outlineColor = Color.decode("#222222"),
                fillColor = Color.decode("#665544")
            )
        )
        Assertions.assertEquals(expected, parser.parse(xml))
    }

    @Test
    @DisplayName("Создание ноды triangle соответствует созданию объекту класса shape.Triangle")
    fun case_6() {
        val xml = """
            <shapes>
                 <triangle
                    vertex1_x='10'
                    vertex1_y='12'
                    vertex2_x='60'
                    vertex2_y='2'
                    vertex3_x='70'
                    vertex3_y='92'
                    outline_color='#FFF455'
                    fill_color='#662222'
                />
            </shapes>
        """
        val expected = listOf<IShape>(
            Triangle(
                vertex1 = Point(10.0, 12.0),
                vertex2 = Point(60.0, 2.0),
                vertex3 = Point(70.0, 92.0),
                outlineColor = Color.decode("#FFF455"),
                fillColor = Color.decode("#662222")
            )
        )
        Assertions.assertEquals(expected, parser.parse(xml))
    }

    @Test
    @DisplayName("Создание ноды line соответствует созданию объекту класса shape.LineSegment")
    fun case_7() {
        val xml = """
            <shapes>
                <line
                    outline_color='#000565'
                    start_x='70'
                    start_y='45'
                    end_x='34'
                    end_y='88'
                />
            </shapes>
        """
        val expected = listOf<IShape>(
            LineSegment(
                start = Point(70.0, 45.0),
                end = Point(34.0, 88.0),
                outlineColor = Color.decode("#000565")
            )
        )
        Assertions.assertEquals(expected, parser.parse(xml))
    }

    @Test
    @DisplayName("Если указаны не все атрибуты или указаны лишние/неизвестные атрибуты, выбрасывается исключение")
    fun case_8() {
        val xml = """
            <shapes>
                <line
                    outline_color='#000565'
                    width='12'
                    start_x='70'
                    start_y='45'
                    end_x='34'
                    end_y='88'
                />
            </shapes>
        """
        Assertions.assertThrows(ShapeXMLParser.ParserException::class.java) {
            parser.parse(xml)
        }
    }

    @Test
    @DisplayName("Если для атрибута указано невалидное значение, выбрасывается исключение")
    fun case_9() {
        val xml = """
            <shapes>
                <line
                    outline_color='#000565'
                    start_x='#000565'
                    start_y='45'
                    end_x='34'
                    end_y='88'
                />
            </shapes>
        """
        Assertions.assertThrows(ShapeXMLParser.ParserException::class.java) {
            parser.parse(xml)
        }
    }

//    @Test
//    @DisplayName("Если для атрибута указано невалидное значение, выбрасывается исключение")
//    fun case_10() {
//        val xml = """
//            <shapes>
//                <line
//                    outline_color='#000565'
//                    width='#454545'
//                    start_x='70'
//                    start_y='45'
//                    end_x='34'
//                    end_y='88'
//                />
//            </shapes>
//        """
//        Assertions.assertThrows(ShapeXMLParser.ParserException::class.java) {
//            parser.parse(xml)
//        }
//    }

}