package com.salgam.upbit.api.rest.enum

enum class Period(val urlName: String) {
  OneMinuit("minutes/1"), Day("days"),
  Weeks("weeks"), Month("months")
}