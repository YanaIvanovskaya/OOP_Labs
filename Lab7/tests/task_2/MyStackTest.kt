package task_2

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class MyStackTest {

    @Test
    @DisplayName("Конструктор создает пустой стек")
    fun case_1() {
        val stack = MyStack<String>()
        assertTrue { stack.isEmpty() }
    }

    @Test
    @DisplayName("Функция emptyStackOf создает пустой стек")
    fun case_2() {
        val stack = emptyStackOf<String>()
        assertTrue { stack.isEmpty() }
    }

    @Test
    @DisplayName("Метод push кладет элемент на вершину стека")
    fun case_3() {
        val stack = emptyStackOf<String>()
        stack.push("Hello")
        stack.push("World")
        assertEquals("World", stack.top())
    }

    @Test
    @DisplayName("Метод pull убирает элемент с вершины стека")
    fun case_4() {
        val stack = emptyStackOf<String>()
        stack.push("Hello")
        stack.push("World")
        stack.pull()
        assertEquals("Hello", stack.top())
    }

    @Test
    @DisplayName("Если стек пустой, метод pull ничего не делает или выбрасывает исключение?")
    fun case_5() {
        val stack = emptyStackOf<String>()
        stack.pull()
        assertEquals(null, stack.top())
    }

    @Test
    @DisplayName("Метод pop возвращает элемент с вершины стека - список при этом не меняется")
    fun case_6() {
        val stack = emptyStackOf<String>()
        assertEquals(null, stack.top())
        stack.push("Hello")
        assertEquals("Hello", stack.top())
        stack.push("World")
        assertEquals("World", stack.top())
    }

    @Test
    @DisplayName("Метод isEmpty должен вернуть true если стек пуст")
    fun case_7() {
        val stack = emptyStackOf<String>()
        assertTrue { stack.isEmpty() }
        stack.push("Hello")
        stack.push("World")
        assertFalse { stack.isEmpty() }
    }

    @Test
    @DisplayName("Метод clear должен очистить стек")
    fun case_8() {
        val stack = emptyStackOf<String>()
        assertTrue { stack.isEmpty() }
        stack.push("Hello")
        stack.push("World")
        stack.clear()
        assertTrue { stack.isEmpty() }
    }

    @Test
    @DisplayName("Метод makeCopy должен вернуть копию стека")
    fun case_9() {
        var hello = "Hello"
        val world = "World"
        val stack = emptyStackOf<String>()
        assertEquals(null, stack.top())
        stack.push(hello)
        assertEquals(hello, stack.top())
        stack.push(world)
        assertEquals(world, stack.top())
        hello = "Goodbye"
        val copy = stack.makeCopy()
        assertEquals(world, copy.top())
        copy.pull()
        assertEquals("Hello", copy.top())
        copy.pull()
        assertEquals(null, copy.top())
    }

}