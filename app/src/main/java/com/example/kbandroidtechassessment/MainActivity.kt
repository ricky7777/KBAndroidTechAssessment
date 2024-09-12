package com.example.kbandroidtechassessment

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kbandroidtechassessment.compose.TxDetailScreen
import com.example.kbandroidtechassessment.ui.theme.KBAndroidTechAssessmentTheme
import com.example.kbandroidtechassessment.viewmodel.TransactionViewModel

/**
 * @Author Ricky
 * this is main entry point, transaction detail ui use Composable
 * and put in TxDetailScreen
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]
        setContent {
            KBAndroidTechAssessmentTheme {
                TxDetailScreen(supportFragmentManager, transactionViewModel)
            }
        }
    }
}
