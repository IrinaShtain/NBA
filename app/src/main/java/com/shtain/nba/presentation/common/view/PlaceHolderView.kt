package com.shtain.nba.presentation.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ScrollingView
import com.shtain.nba.R
import com.shtain.nba.databinding.ViewPlaceHolderBinding
import com.shtain.nba.presentation.common.extensions.visibleOrGoneIf


class PlaceHolderView @kotlin.jvm.JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ScrollingView {

    private lateinit var binding: ViewPlaceHolderBinding

    init {
        init(attrs)
    }

    /**
     * Init this widget
     * @param attrs
     */
    private fun init(attrs: AttributeSet?) {
        binding = ViewPlaceHolderBinding.inflate(LayoutInflater.from(context), this, true)

        if (attrs != null) {
            val styles = context.obtainStyledAttributes(attrs, R.styleable.PlaceHolderView)
            try {
                if (styles.hasValue(R.styleable.PlaceHolderView_title)) {
                    setTitle(styles.getString(R.styleable.PlaceHolderView_title) ?: "")
                }

                if (styles.hasValue(R.styleable.PlaceHolderView_description)) {
                    setDescription(styles.getString(R.styleable.PlaceHolderView_description) ?: "")
                }


                if (styles.hasValue(R.styleable.PlaceHolderView_state_button_text)) {
                    setButtonText(
                        styles.getString(R.styleable.PlaceHolderView_state_button_text)
                            ?: ""
                    )
                }

                if (styles.hasValue(R.styleable.PlaceHolderView_state_icon)) {
                    setImageResource(
                        styles.getResourceId(
                            R.styleable.PlaceHolderView_state_icon,
                            R.drawable.ic_no_connection
                        )
                    )
                }

                if (styles.hasValue(R.styleable.PlaceHolderView_state_button_visibility)) {
                    setButtonVisibility(
                        styles.getBoolean(
                            R.styleable.PlaceHolderView_state_button_visibility,
                            false
                        )
                    )
                }

                if (styles.hasValue(R.styleable.PlaceHolderView_state_icon_visibility)) {
                    setIconVisibility(
                        styles.getBoolean(
                            R.styleable.PlaceHolderView_state_icon_visibility,
                            false
                        )
                    )
                }
            } finally {
                styles.recycle()
            }
        }
    }

    /**
     * Sets text for title
     * @param text
     */
    fun setTitle(text: String) {
        binding.titleTextView.text = text
    }

    /**
     * Sets text for description
     * @param text
     */
    fun setDescription(text: String) {
        binding.descriptionTextView.text = text
    }

    /**
     * Sets text for button
     * @param text
     */
    fun setButtonText(text: String) {
        binding.reloadButton.text = text
    }

    /**
     * Set image resource id for back button
     * @param imageResId
     */
    fun setImageResource(imageResId: Int) {
        binding.iconImageView.setImageResource(imageResId)
    }

    /**
     * Set visibility for icon
     * @param isVisible
     */
    fun setIconVisibility(isVisible: Boolean) {
        binding.iconImageView.visibleOrGoneIf(isVisible)
    }

    /**
     * Set visibility for button
     * @param isVisible
     */
    fun setButtonVisibility(isVisible: Boolean) {
        binding.reloadButton.visibleOrGoneIf(isVisible)
    }

    /**
     * Set click listener for reload button
     * @param clickListener
     */
    fun setReloadClickListener(clickListener: (m: View) -> Unit) {
        binding.reloadButton.setOnClickListener {
            clickListener.invoke(it)
        }
    }

    override fun computeHorizontalScrollOffset(): Int {
        return 0
    }

    override fun computeHorizontalScrollExtent(): Int {
        return 0
    }

    override fun computeHorizontalScrollRange(): Int {
        return 0
    }

    override fun computeVerticalScrollOffset(): Int {
        return 0
    }

    override fun computeVerticalScrollExtent(): Int {
        return 0
    }

    override fun computeVerticalScrollRange(): Int {
        return 0
    }
}