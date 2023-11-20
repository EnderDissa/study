import java.util.concurrent.ConcurrentHashMap

class ConcurrentHashSet<E : Any> {
    private val map = ConcurrentHashMap<E, Boolean>()

    fun add(element: E): Boolean {
        return map.putIfAbsent(element, true) == null
    }

    fun remove(element: E): Boolean {
        return map.remove(element) != null
    }

    fun contains(element: E): Boolean {
        return map.containsKey(element)
    }

    fun size(): Int {
        return map.size
    }

    fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    fun clear() {
        map.clear()
    }

    operator fun iterator(): Iterator<E> {
        return map.keys.iterator()
    }
}