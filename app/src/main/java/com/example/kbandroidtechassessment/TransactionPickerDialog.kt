package com.example.kbandroidtechassessment

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.example.kbandroidtechassessment.databinding.BottomSheetTransactionDetailCalendarBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker

/**
 * @author Ricky
 * transaction detail's date picker
 */
class TransactionPickerDialog(
    context: Context,
    private val fragmentManager: FragmentManager?,
    private val callback: (String) -> Unit
) : BottomSheetDialog(context) {

    private var selectedButton: Button? = null
    private val binding: BottomSheetTransactionDetailCalendarBinding =
        BottomSheetTransactionDetailCalendarBinding.inflate(LayoutInflater.from(context))

    init {
        setEvent()
        setContentView(binding.root)
    }

    private fun setEvent() {
        binding.btn30Days.setOnClickListener { selectButton(binding.btn30Days) }
        binding.btn90Days.setOnClickListener { selectButton(binding.btn90Days) }
        binding.btn1Year.setOnClickListener { selectButton(binding.btn1Year) }
        binding.btnConfirm.setOnClickListener {
            selectedButton?.let {
                callback(it.text.toString())
                dismiss()
            }
        }

        binding.dateRangeInput.setOnClickListener {
            fragmentManager?.let {
                val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
                datePicker.show(it, "DatePicker")
            }
        }
    }

    private fun selectButton(button: Button) {
        if (selectedButton != button) {
            selectedButton?.setBackgroundResource(R.drawable.button_selector)
            selectedButton = button
            button.setBackgroundResource(R.color.teal_200) // 設定選中狀態
        }
    }
}