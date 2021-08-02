package com.yamsy.medreminder.eventbus

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.*

object MedReminderEventBus {

    private val eventPublisher = PublishSubject.create<Any>()

    fun publish(event : Any) {
        eventPublisher.onNext(event)
    }

    //Return an observable of the type eventType class
    fun<T> listen(eventType: Class<T>): Observable<T> = eventPublisher.ofType(eventType)

    fun<T> subscribe(eventType: Class<T>, onNext: ((T) -> Unit)) : Disposable {
        return listen(eventType).subscribe(onNext)
    }
}

//Events
internal data class DateChangeEvent(val timeInMillis: Long)