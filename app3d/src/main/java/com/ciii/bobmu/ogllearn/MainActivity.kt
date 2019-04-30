package com.ciii.bobmu.ogllearn

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_first.setOnClickListener {
            var intent=Intent(this, FirstActivity::class.java)
            startActivityForResult(intent, 101)
        }
        button_second.setOnClickListener {
            var intent=Intent(this, com.ciii.bobmu.ogllearn.airhockey_second.AirHockeyActivity::class.java)
            startActivityForResult(intent, 102)
        }
        button_third.setOnClickListener{
            var intent=Intent(this, com.ciii.bobmu.ogllearn.airhockey_texture.AirHockeyActivity::class.java)
            startActivityForResult(intent, 103)
        }
        button_fourth.setOnClickListener{
            var intent=Intent(this, com.ciii.bobmu.ogllearn.airhockey_mallet.AirHockeyActivity::class.java)
            startActivityForResult(intent, 104)
        }
        button_fifth.setOnClickListener{
            var intent=Intent(this, com.ciii.bobmu.ogllearn.airhockey_mallet.AirHockeyActivity::class.java)
            startActivityForResult(intent, 105)
        }
    }

}
