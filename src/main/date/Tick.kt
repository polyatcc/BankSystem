package date

class Tick(val tick: Int) : CustomDate {

    companion object {
        fun now() = Tick(0)
    }

    override fun compareTo(date: CustomDate): Int {
        if (date !is Tick) return 0
        return tick.compareTo(date.tick)
    }

    override fun isLastInMonth(): Boolean {
        return tick % 30 == 0
    }

    override fun yearLength(): Int {
        return 365
    }

    override fun next(): CustomDate {
        return Tick(tick + 1)
    }

    override fun equals(other: Any?): Boolean {
        return other is Tick && tick == other.tick
    }

    override fun toString(): String = "Tick[$tick]"

}