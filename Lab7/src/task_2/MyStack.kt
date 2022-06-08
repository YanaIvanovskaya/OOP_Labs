package task_2

// реализовать через односвязный список
// сделать метод copyFrom
// cloneable copyable
class MyStack<T> {

    private var mTop: Item<T>? = null

    fun push(value: T) {
        mTop = Item(value = value, prev = mTop)
    }

    fun pull() {
        if (!isEmpty()) {
            mTop = mTop?.prev
        }
    }

    fun top(): T? {
        return mTop?.value
    }

    fun isEmpty(): Boolean {
        return mTop == null
    }

    fun clear() {
        mTop = null
    }

    fun makeCopy(): MyStack<T> {
        val copy = emptyStackOf<T>()
        mutableListOf<T>().apply {
            this@MyStack.forEach(this::add)
        }.reversed().forEach(copy::push)
        return copy
    }

    override fun toString(): String {
        var prev = mTop
        var str = "["
        while (prev != null) {
            str += "${prev.value}"
            prev = prev.prev
            if (prev != null) {
                str += ","
            }
        }
        str += "]"
        return str
    }

    private fun forEach(action: (T) -> Unit) {
        var prev = mTop
        while (prev != null) {
            action(prev.value)
            prev = prev.prev
        }
    }

    private data class Item<T>(
            val value: T,
            val prev: Item<T>?
    )

}

fun <T> emptyStackOf(): MyStack<T> {
    return MyStack()
}