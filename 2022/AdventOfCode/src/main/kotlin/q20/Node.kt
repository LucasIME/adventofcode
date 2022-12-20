package q20

class Node<T> (val value:T, var previous: Node<T>?, var next: Node<T>?) {

    override fun toString(): String {
        return "value: $value next: ${next?.value} previous: ${previous?.value}"
    }
}