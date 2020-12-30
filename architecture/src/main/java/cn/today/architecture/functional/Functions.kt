package cn.today.architecture.functional

typealias Supplier<T> = () -> T

interface Consumer<T> {

    fun accept(t: T)
}