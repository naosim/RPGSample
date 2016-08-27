package com.naosim.rpgmodel.lib.android

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import com.naosim.rpgmodel.lib.model.value.field.ArrowButtonType
import com.naosim.rpgmodel.lib.model.viewmodel.FieldViewModel

class ArrowPadView(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {
    var fieldViewModel: FieldViewModel? = null

    var lastArrowButtonType: ArrowButtonType? = null

    init {
        this.setOnTouchListener { view, motionEvent ->
            val center = Point(width.toFloat(), height.toFloat()).center()
            val touchPosition = Point(motionEvent.x, motionEvent.y)
            val pointFromCenter = touchPosition.minus(center)
            val deg = Math.atan2(pointFromCenter.y.toDouble(), pointFromCenter.x.toDouble()) * 180 / Math.PI

            val arrowButtonType = when(deg) {
                in -45 .. 45 -> ArrowButtonType.right
                in 45 .. 135 -> ArrowButtonType.down
                in -135 .. -45 -> ArrowButtonType.up
                in -180 .. -135, in 135 .. 180 -> ArrowButtonType.left
                else -> null
            }

            arrowButtonType?.apply {
                when(motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastArrowButtonType = arrowButtonType
                        fieldViewModel?.onButtonDown(arrowButtonType)
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        fieldViewModel?.onButtonUp(arrowButtonType)
                        lastArrowButtonType = null
                    }
                    MotionEvent.ACTION_MOVE -> {
                        lastArrowButtonType?.apply {
                            if(lastArrowButtonType != arrowButtonType) {
                                fieldViewModel?.onButtonUp(lastArrowButtonType!!)
                                lastArrowButtonType = arrowButtonType
                                fieldViewModel?.onButtonDown(arrowButtonType)
                            }
                        }
                    }
                }
            }



//            Log.e(this.javaClass.simpleName, "touch ${deg} ${center}, ${touchPosition}, ${pointFromCenter}")
            Log.e(this.javaClass.simpleName, "touch ${arrowButtonType}")
            true
        }
    }

    private class Point(var x: Float, var y: Float) {
        fun center(): Point {
            return Point(x / 2, y / 2)
        }
        fun minus(other: Point): Point {
            return Point(x - other.x, y - other.y)
        }

        override fun toString(): String {
            return "${this.javaClass.simpleName}(${x}, ${y})"
        }
    }
}