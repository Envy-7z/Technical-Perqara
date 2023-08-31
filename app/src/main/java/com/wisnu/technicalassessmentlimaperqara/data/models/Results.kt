package com.wisnu.technicalassessmentlimaperqara.data.models

sealed class Results<out R> private constructor() {
    data class Success<out T>(val data: T) : Results<T>()
    data class Error(val error: String) : Results<Nothing>()
    object Loading: Results<Nothing>()
}