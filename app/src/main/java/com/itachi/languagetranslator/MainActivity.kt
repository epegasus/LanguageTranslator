package com.itachi.languagetranslator

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.itachi.languagetranslator.databinding.ActivityMainBinding
import com.itachi.languagetranslator.helper.dataProviders.DpLanguages
import com.itachi.languagetranslator.helper.interfaces.TranslationListener
import com.itachi.languagetranslator.helper.retrofit.TranslationManager
import dev.pegasus.utils.extensions.ui.hideKeyboard
import dev.pegasus.utils.extensions.ui.showToast
import dev.pegasus.utils.extensions.uiComponents.gone
import dev.pegasus.utils.extensions.uiComponents.visible
import dev.pegasus.utils.managers.InternetManager
import dev.pegasus.utils.utils.PegasusHelperUtils.TAG
import dev.pegasus.utils.utils.PegasusValidationUtils

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val translationManager by lazy { TranslationManager(listener) }
    private val internetManager by lazy { InternetManager(this) }
    private val dpLanguages by lazy { DpLanguages() }

    private var languageCodeFrom = "en"
    private var languageCodeTo = "af"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUI()

        binding.mbTranslate.setOnClickListener { onTranslateClick() }
        binding.etFrom.setOnItemClickListener { _, _, pos, _ -> languageCodeFrom = dpLanguages.languageList[pos].first }
        binding.etTo.setOnItemClickListener { _, _, pos, _ -> languageCodeTo = dpLanguages.languageList[pos].first }
    }

    private fun setUI() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dpLanguages.languageList.map { it.second })
        binding.etFrom.setAdapter(adapter)
        binding.etTo.setAdapter(adapter)
        binding.etFrom.setText(dpLanguages.languageList[11].second, false)
        binding.etTo.setText(dpLanguages.languageList[0].second, false)
    }

    private fun onTranslateClick() {
        if (!checkValidation()) return
        hideKeyboard()
        if (!internetManager.isInternetConnected) {
            showToast("No Internet Connection")
            return
        }
        translateLanguage()
    }

    private fun checkValidation(): Boolean {
        return PegasusValidationUtils.isNotEmpty(binding.ltText, binding.etText)
                && PegasusValidationUtils.isNotEmpty(binding.ltFrom, binding.etFrom)
                && PegasusValidationUtils.isNotEmpty(binding.ltTo, binding.etTo)
    }

    private fun translateLanguage() {
        val text = binding.etText.text?.trim().toString()
        translationManager.translateText(text, languageCodeFrom, languageCodeTo)
    }

    private val listener = object : TranslationListener {
        override fun onProgressStart() {
            binding.progressBar.visible()
        }

        override fun onProgressEnd() {
            binding.progressBar.gone()
        }

        override fun onSuccess(text: String) {
            binding.mtvOutput.text = text
        }

        override fun onError(ex: Exception) {
            showToast("Exception Found")
            Log.e(TAG, "onError: ", ex)
        }
    }
}