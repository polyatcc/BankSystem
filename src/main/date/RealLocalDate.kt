package date

import java.time.LocalDate

class RealLocalDate(val localDate: LocalDate) : CustomDate {

    companion object {
        fun now() = RealLocalDate(LocalDate.now())
    }

    override fun compareTo(date: CustomDate): Int {
        if (date !is RealLocalDate) return 0
        return localDate.compareTo(date.localDate)
    }

    override fun isLastInMonth(): Boolean {
        return localDate.dayOfMonth == localDate.lengthOfMonth()
    }

    override fun yearLength(): Int {
        return localDate.lengthOfYear()
    }

    override fun next(): CustomDate {
        return RealLocalDate(localDate.plusDays(1)!!)
    }

    override fun equals(other: Any?): Boolean {
        return other is RealLocalDate && localDate == other.localDate
    }

    override fun toString(): String = localDate.toString()

}