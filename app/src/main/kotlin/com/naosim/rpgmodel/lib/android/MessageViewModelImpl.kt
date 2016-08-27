package com.naosim.rpgmodel.lib.android

import android.os.Handler
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import com.naosim.rpgmodel.lib.model.value.Message
import com.naosim.rpgmodel.lib.model.viewmodel.MessageViewModel

class MessageViewModelImpl(
        private val textView: TextView,
        private val nextIcon: View
): MessageViewModel {
    private val handler = Handler()

    fun setMessageText(text: String) {
        handler.post { textView.text = text }
    }

    override fun clear() {
        textView.text = ""
    }

    override fun appendText(message: Message, onEnd: () -> Unit) {
        val text = message.value.trim { it <= ' ' } + "\n"
        val firstTextCount = textView.text.toString().length
        val data = text.toCharArray()
        nextIcon.visibility = View.INVISIBLE
        nextIcon.clearAnimation()

        Thread({
            while (true) {
                val currentText = textView.text.toString()
                var i = currentText.length
                if (i - firstTextCount >= data.size) {
                    handler.post {
                        nextIcon.visibility = View.VISIBLE
                        val aanim1 = AlphaAnimation(1f, 0.2f)
                        aanim1.duration = 400
                        aanim1.repeatCount = Animation.INFINITE
                        aanim1.repeatMode = Animation.REVERSE
                        nextIcon.startAnimation(aanim1)
                        onEnd.invoke()
                    }
                    break
                }
                setMessageText(currentText + data[i++ - firstTextCount])
                try {
                    Thread.sleep(50L)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }).start()
    }

    override fun show() {
        textView.visibility = View.VISIBLE
    }

    override fun dismiss() {
        textView.visibility = View.INVISIBLE
        nextIcon.clearAnimation()
        nextIcon.visibility = View.INVISIBLE
    }

    override fun isShowing(): Boolean {
        return textView.visibility == View.VISIBLE
    }
}