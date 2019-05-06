package home.oleg.placesnearme.feature_map.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.smedialink.common.ext.gone

import home.oleg.placesnearme.feature_map.R
import kotlinx.android.synthetic.main.view_loading.view.*

class LoadingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this, true)
    }

    fun setOnRetryCLickListener(onRetryCLickListener: () -> Unit) {
        retryButton.setOnClickListener {
            onRetryCLickListener()
        }
    }

    fun showLoading(isShown: Boolean) {
        this.gone(isShown)
        retryButton.gone(true)
        spinKit.gone(!isShown)
    }

    fun showRetry() {
        this.visibility = View.VISIBLE
        retryButton.visibility = View.VISIBLE
        spinKit.visibility = View.GONE
    }

}
