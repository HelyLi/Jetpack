package cn.today.architecture.util

open class SingletonHolderSingleArg<out T, in A>(private val creator:(A)->T) {
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T = instance ?: synchronized(this) {
            instance ?: creator(arg).apply {
                instance = this
            }
        }

}