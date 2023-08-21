package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geoquiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCheatBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCheatBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}
