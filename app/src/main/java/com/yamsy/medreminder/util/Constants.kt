package com.yamsy.medreminder.util

object Constants {

    const val LOG_PREFIX = "Med-Remind:"

    const val DEFAULT_PRESCRIPTION_TIME = -1L

    const val DEFAULT_DATE_FORMAT = "dd-MM-yyyy"

    enum class MedSessionType(private val mTitle: String) {
        MORNING("Morning"),
        NOON("Noon"),
        EVENING("Evening"),
        NIGHT("Night");

        val title: String
            get() = mTitle
    }

    enum class MedTaskType(private val mTitle: String) {
        MEDICINE("MEDICINE"),
        VOD("VOD");

        val title: String
            get() = mTitle
    }
}