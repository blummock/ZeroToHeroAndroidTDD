package ru.easycode.zerotoheroandroidtdd

interface Count {
    fun initial(number: String): UiState
    fun increment(number: String): UiState
    fun decrement(number: String): UiState

    class Base(private val step: Int, private val max: Int, private val min: Int) : Count {
        init {
            if (step <= 0) throw IllegalStateException("step should be positive, but was $step")
            if (max <= 0) throw IllegalStateException("max should be positive, but was $max")
            if (max < step) throw IllegalStateException("max should be more than step")
            if (max < min) throw IllegalStateException("max should be more than min")
        }

        override fun initial(number: String): UiState {
            val num = number.toInt()
            return when {
                num > max - step -> UiState.Max(text = number)
                num < min + step -> UiState.Min(text = number)
                else -> UiState.Base(text = number)
            }
        }

        override fun increment(number: String): UiState {
            val newNumber = number.toInt() + step
            return when {
                newNumber > max - step -> UiState.Max(text = newNumber.toString())
                else -> UiState.Base(text = newNumber.toString())
            }
        }

        override fun decrement(number: String): UiState {
            val newNumber = number.toInt() - step
            return when {
                newNumber < min + step -> UiState.Min(text = newNumber.toString())
                else -> UiState.Base(text = newNumber.toString())
            }
        }
    }
}
