package dev.zidali.recipeapp.business.domain.util

/**
 * I used this directly from one of mitch's projects: codingwithmitch.com
 */

data class DataState<T>(
    val stateMessage: StateMessage? = null,
    val data: T? = null,
    val isLoading: Boolean = false
) {

    companion object {

        fun <T> error(
            response: Response,
        ): DataState<T> {
            return DataState(
                stateMessage = StateMessage(
                    response
                ),
                data = null,
            )
        }

        fun <T> data(
            response: Response?,
            data: T? = null,
        ): DataState<T> {
            return DataState(
                stateMessage = response?.let {
                    StateMessage(
                        it
                    )
                },
                data = data,
            )
        }

        fun <T> loading(): DataState<T> = DataState(isLoading = true)
    }
}