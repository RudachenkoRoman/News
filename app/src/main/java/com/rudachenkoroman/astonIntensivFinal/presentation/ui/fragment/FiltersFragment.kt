package com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentFiltersBinding
import com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment.HeadlinesFragment.Companion.language
import com.rudachenkoroman.astonIntensivFinal.presentation.util.setFragment
import com.rudachenkoroman.astonIntensivFinal.presentation.util.setOnBackPressedCallback
import java.text.SimpleDateFormat
import java.util.Locale

class FiltersFragment : Fragment() {

    private lateinit var binding: FragmentFiltersBinding
    private var isColorTextAndCalendarIcon = true
    private var newsCategory = 0
    private var languageCategory = 0
    private var data = "null date"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiltersBinding.inflate(layoutInflater)
        binding.apply {
            toolbarInit()
            backClicked()
            previousFragment()
            calendarFragmentClicked()
            newsCategory()
            languageCategory()
            choiceDate()
            choiceNewsCategory()
            choiceLanguageCategory()
            showText()
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            data = savedInstanceState.getString("text", "")
            isColorTextAndCalendarIcon = savedInstanceState.getBoolean("color")
            newsCategory = savedInstanceState.getInt("newsCategory")
            languageCategory = savedInstanceState.getInt("languageCategory")
        }
    }

    private fun showText() {
        if (data !== "null date") {
            binding.chooseDate.text = data
        }
    }

    private fun toolbarInit() {
        binding.apply {
            toolbar.toolbarMain.inflateMenu(R.menu.toolbar_menu_filters)
            toolbar.fragmentName.text = getText(R.string.filters)
            toolbar.toolbarMain.setBackgroundResource(R.color.primary60)
        }
    }

    private fun backClicked() {
        binding.apply {
            toolbar.toolbarMain.setNavigationIcon(R.drawable.back)
            toolbar.toolbarMain.setNavigationOnClickListener {
                setOnBackPressedCallback()
            }
        }
    }

    private fun calendarFragmentClicked() {
        binding.apply {
            calendar.setOnClickListener {
                datePicker()
            }
        }
    }

    private fun previousFragment() {
        binding.apply {
            toolbar.toolbarMain.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.done -> {
                        parentFragmentManager.setFragment(
                            R.id.fragmentContainerView,
                            HeadlinesFragment(),
                            HeadlinesFragment.HEADLINES_FRAGMENT_TAG
                        )
                    }
                }
                true
            }
        }
    }

    private fun datePicker() {
        val datePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select date")
                .setTheme(R.style.ThemeOverlay_Material3_MaterialCalendar_)
                .setSelection(
                    Pair(
                        MaterialDatePicker.todayInUtcMilliseconds(),
                        null
                    )
                )
                .build()
        datePicker.show(parentFragmentManager, "date_picker")
        datePicker.addOnPositiveButtonClickListener {
            val simpleFormatDateAndMonth = SimpleDateFormat("MMM dd", Locale.getDefault())
            val simpleFormatYear = SimpleDateFormat("yyyy", Locale.getDefault())
            val startDay = simpleFormatDateAndMonth.format(it.first)
            val endDay = simpleFormatDateAndMonth.format(it.second)
            val year = simpleFormatYear.format(it.second)
            binding.apply {
                calendar.setImageResource(R.drawable.calendar_choice)
                chooseDate.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.primary60,
                        null
                    )
                )
                if (it.first != it.second) {
                    chooseDate.text = getString(R.string.two_date, startDay, endDay, year)
                } else {
                    chooseDate.text = getString(R.string.one_date, startDay, year)
                }
                isColorTextAndCalendarIcon = false
            }
        }
        datePicker.addOnNegativeButtonClickListener {
            binding.apply {
                calendar.setImageResource(R.drawable.calendar)
                chooseDate.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
                chooseDate.text = getString(R.string.choose_date)
                isColorTextAndCalendarIcon = true
            }
        }
    }

    private fun choiceDate() {
        binding.apply {
            if (isColorTextAndCalendarIcon) {
                chooseDate.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
                calendar.setImageResource(R.drawable.calendar)
            } else {
                chooseDate.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.primary60,
                        null
                    )
                )
                calendar.setImageResource(R.drawable.calendar_choice)
            }
        }
    }

    private fun newsCategory() {
        binding.apply {
            buttonPopular.setOnClickListener {
                buttonPopular.setImageResource(R.drawable.button_popular_checked)
                buttonNew.setImageResource(R.drawable.button_new)
                buttonRelevant.setImageResource(R.drawable.button_relevant)
                newsCategory = 1
            }

            buttonNew.setOnClickListener {
                buttonPopular.setImageResource(R.drawable.button_popular)
                buttonNew.setImageResource(R.drawable.button_new_checked)
                buttonRelevant.setImageResource(R.drawable.button_relevant)
                newsCategory = 2
            }

            buttonRelevant.setOnClickListener {
                buttonPopular.setImageResource(R.drawable.button_popular)
                buttonNew.setImageResource(R.drawable.button_new)
                buttonRelevant.setImageResource(R.drawable.button_relevant_checked)
                newsCategory = 3
            }
        }
    }

    private fun choiceNewsCategory() {
        binding.apply {
            when (newsCategory) {
                1 -> {
                    buttonPopular.setImageResource(R.drawable.button_popular_checked)
                }

                2 -> {
                    buttonNew.setImageResource(R.drawable.button_new_checked)
                }

                3 -> {
                    buttonRelevant.setImageResource(R.drawable.button_relevant_checked)
                }
            }
        }
    }

    private fun languageCategory() {
        binding.apply {
            buttonRussian.setOnClickListener {
                buttonRussian.setImageResource(R.drawable.button_russian_checked)
                buttonEnglish.setImageResource(R.drawable.button_english)
                buttonDeutsch.setImageResource(R.drawable.button_deutsch)
                language = "ru"
                languageCategory = 1
            }
            buttonEnglish.setOnClickListener {
                buttonRussian.setImageResource(R.drawable.button_russian)
                buttonEnglish.setImageResource(R.drawable.button_english_checked)
                buttonDeutsch.setImageResource(R.drawable.button_deutsch)
                language = "us"
                languageCategory = 2
            }
            buttonDeutsch.setOnClickListener {
                buttonRussian.setImageResource(R.drawable.button_russian)
                buttonEnglish.setImageResource(R.drawable.button_english)
                buttonDeutsch.setImageResource(R.drawable.button_deutsch_checked)
                language = "de"
                languageCategory = 3
            }

        }
    }


    private fun choiceLanguageCategory() {
        binding.apply {
            when (languageCategory) {
                1 -> {
                    buttonRussian.setImageResource(R.drawable.button_russian_checked)
                }

                2 -> {
                    buttonEnglish.setImageResource(R.drawable.button_english_checked)
                }

                3 -> {
                    buttonDeutsch.setImageResource(R.drawable.button_deutsch_checked)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("text", binding.chooseDate.text.toString())
        outState.putBoolean("color", isColorTextAndCalendarIcon)
        outState.putInt("newsCategory", newsCategory)
        outState.putInt("languageCategory", languageCategory)
    }

    companion object {
        const val FILTERS_FRAGMENT_TAG = "FILTERS_FRAGMENT_TAG"
    }
}