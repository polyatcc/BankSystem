package date

interface CustomDate {

    companion object {
        lateinit var nowProvider: () -> CustomDate
    }

    operator fun compareTo(date: CustomDate): Int

    fun isLastInMonth(): Boolean

    fun yearLength(): Int

    fun next(): CustomDate

}