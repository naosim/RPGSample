package com.naosim.rpglib.android.activity

interface ActivityLifeCycle {
    fun onStart() {}
    fun onResume() {}
    fun onPause() {}
    fun onStop() {}
    fun onDestroy() {}
}