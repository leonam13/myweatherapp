package com.example.leoweatherapp.utils

import android.location.Location

fun Location.toQueryParam() = "$latitude,$longitude"