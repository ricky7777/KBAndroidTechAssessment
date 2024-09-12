package com.example.kbandroidtechassessment.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import com.example.kbandroidtechassessment.R
import com.example.kbandroidtechassessment.databinding.BottomSheetTransactionDetailCalendarBinding
import com.example.kbandroidtechassessment.utils.DateUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar

/**
 * @author Ricky
 * transaction detail's date picker
 */
class DatePickerDialog(
    context: Context,
    private val fragmentManager: FragmentManager?,
    private val onDateRangeSelected: (dateRange: kotlin.Pair<String, String>) -> Unit
) : BottomSheetDialog(context) {

    private var selectedButton: Button? = null
    private var binding: BottomSheetTransactionDetailCalendarBinding =
        BottomSheetTransactionDetailCalendarBinding.inflate(LayoutInflater.from(context))

    private var dateRange: kotlin.Pair<String, String>? = null

    init {
        setEvent()
        setContentView(binding.root)
    }

    private fun setEvent() {
        binding.btn3Days.setOnClickListener { selectButton(binding.btn3Days) }
        binding.btn10Days.setOnClickListener { selectButton(binding.btn10Days) }
        binding.btn30Days.setOnClickListener { selectButton(binding.btn30Days) }
        binding.btnConfirm.setOnClickListener {
            dateRange?.let {
                onDateRangeSelected(it)
                dismiss()
            }
        }

        binding.dateRangeInput.setOnClickListener {
            fragmentManager?.let {
                showDatePicker()
            }
        }
    }

    private fun showDatePicker() {
        fragmentManager?.let {
            val picker = getDatePicker()
            picker.addOnPositiveButtonClickListener { selection ->
                val startDate = DateUtils.formatDate(selection.first)
                val endDate = DateUtils.formatDate(selection.second)
                val selectionDate = String.format("%s ~ %s", startDate, endDate)
                binding.dateRangeInput.setText(selectionDate)
                dateRange = kotlin.Pair(startDate, endDate)
            }
            picker.show(it, picker.toString())
        }
    }

    private fun getDatePicker(): MaterialDatePicker<Pair<Long, Long>> {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val todayInMillis = Calendar.getInstance().timeInMillis
        val constraintsBuilder = CalendarConstraints.Builder().setEnd(todayInMillis)
        val picker = builder.setCalendarConstraints(constraintsBuilder.build())
            .setTheme(R.style.DatePickerTheme).build()
        return picker
    }

    private fun selectButton(button: Button) {
        if (selectedButton != button) {
            resetButtonsColor()
            changeColor(button)
            selectedButton = button
            dateRange = DateUtils.getDateRangeFromToday(selectedButton?.tag.toString().toInt())
        }
    }

    private fun changeColor(button: Button) {
        button.setBackgroundResource(R.color.teal_200)
    }

    private fun resetButtonsColor(){
        binding.run {
            resetButtonColor(btn3Days)
            resetButtonColor(btn10Days)
            resetButtonColor(btn30Days)
        }
    }

    private fun resetButtonColor(button: Button){
        button.setBackgroundResource(R.drawable.button_selector)
    }
}