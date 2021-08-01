package com.denisferreira.progressbutton

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.denisferreira.progressbutton.databinding.ProgressButtonBinding

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var title: String? = null
    private var loadingTitle: String? = null;

    private val binding = ProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private var state: ProgressButtonState = ProgressButtonState.Normal
        set(value) {
            field = value
            refreshState()
        }

    private fun refreshState() {
        isEnabled = state.isEnable
        isClickable = state.isEnable
        refreshDrawableState()

        binding.textTitle.run {
            isEnabled = state.isEnable
            isClickable = state.isEnable
        }

        binding.progressBar.visibility = state.progressVisibility

        when (state) {
            ProgressButtonState.Normal -> binding.textTitle.text = title
            ProgressButtonState.Loading -> binding.textTitle.text = loadingTitle
        }
    }

    fun setLoading() {
        state = ProgressButtonState.Loading
    }
    fun setNormal() {
        state = ProgressButtonState.Normal
    }

    init {
        setLayout(attrs)
        setNormal()
        binding.textTitle.setOnClickListener {
            this.callOnClick()
        }
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(
                attrs,
                R.styleable.ProgressButton
            )
            setBackgroundResource(R.drawable.progress_button_background)

            val titleResId =
                attributes.getResourceId(R.styleable.ProgressButton_progress_button_title, 0)
            if (titleResId != 0)
                title = context.getString(titleResId)

            val loadingTitleResId =
                attributes.getResourceId(R.styleable.ProgressButton_progress_button_loading_tile, 0)
            if (loadingTitleResId != 0)
                loadingTitle = context.getString(loadingTitleResId)

            attributes.recycle()
        }

    }

    sealed class ProgressButtonState(val isEnable: Boolean, val progressVisibility: Int) {
        object Normal : ProgressButtonState(true, View.GONE)
        object Loading : ProgressButtonState(false, View.VISIBLE)
    }
}