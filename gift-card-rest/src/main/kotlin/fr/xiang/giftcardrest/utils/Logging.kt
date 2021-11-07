package fr.xiang.giftcardrest.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

/*
Taken from https://stackoverflow.com/a/34462577
*/

// Return a lazy logger property delegate for enclosing class
fun <R : Any> R.lazyLogger(): Lazy<Logger> {
    return lazy { logger(this.javaClass) }
}

// Return logger for Java class, if companion object fix the name
fun <T : Any> logger(forClass: Class<T>): Logger {
    return LoggerFactory.getLogger(unwrapCompanionClass(forClass).name)
}

// Unwrap companion class to enclosing class given a Java Class
fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return ofClass.enclosingClass?.takeIf {
        ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
    } ?: ofClass
}
