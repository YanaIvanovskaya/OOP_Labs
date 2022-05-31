package task_2

class MyStack<T> {

    private var mList = mutableListOf<T>()

    // Вталкивание элемента на вершину стека
    fun push(value: T) {
        mList.add(0, value)
    }

    // Выталкивание элемента с вершины стека
    fun pull() {
        mList.removeAt(0)
    }

    //  Возвращение элемента с вершины стека
    fun head(): T? {
        return mList.getOrNull(0)
    }

    fun isEmpty(): Boolean {
        return mList.isEmpty()
    }

    fun clear() {
        mList.clear()
    }

    override fun toString(): String {
        return "[${mList.joinToString(",")}]"
    }

}


fun <T> emptyStackOf(): MyStack<T> {
    return MyStack()
}

fun main() {
    val stack1 = MyStack<String>()
    val stack2 = emptyStackOf<String>()
    stack2.push("3")
    stack2.push("2")
    stack2.push("1")
    stack2.pull()
    println(stack2.head())

    println(stack2)

}