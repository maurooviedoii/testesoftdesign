package br.testesoftdesign.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.testesoftdesign.R
import br.testesoftdesign.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_host)
    }

    fun configureToolbar(showToolbar: Boolean, showBackArrow: Boolean, title: String) {

        when (showToolbar) {
            true -> supportActionBar?.show()
            false -> supportActionBar?.hide()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
        supportActionBar?.title = title

    }
}