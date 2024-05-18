package com.example.customchatbot

import android.animation.LayoutTransition
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customchatbot.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    lateinit private var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.layout2.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    fun expand(view: View) {
        // 클릭 이벤트에서 호출되는 함수
        val visibility = if (binding.details.visibility == View.GONE) View.VISIBLE else View.GONE
        TransitionManager.beginDelayedTransition(binding.layout, AutoTransition())
        binding.details.visibility = visibility
    }

    fun expand2(view: View) {
        // 클릭 이벤트에서 호출되는 함수
        val visibility = if (binding.details2.visibility == View.GONE) View.VISIBLE else View.GONE
        TransitionManager.beginDelayedTransition(binding.layout2, AutoTransition())
        binding.details2.visibility = visibility
    }
}