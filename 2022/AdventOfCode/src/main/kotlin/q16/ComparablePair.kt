package q16

class ComparablePair<A : Comparable<A>, B : Comparable<B>>(val first: A, val second: B) :
    Comparable<ComparablePair<A, B>> {

    override fun compareTo(other: ComparablePair<A, B>): Int {
        val firstComparison = first.compareTo(other.first)
        return if (firstComparison != 0) {
            firstComparison
        } else {
            second.compareTo(other.second)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (javaClass != other?.javaClass) {
            return false
        }

        other as ComparablePair<A, B>

        if (first != other.first) {
            return false
        }
        if (second != other.second) {
            return false
        }

        return true
    }
}