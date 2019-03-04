package home.oleg.placesnearme.corepresentation.delegate

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Observer
import com.smedialink.common.base.ErrorEvent
import com.smedialink.common.base.LiveEvent
import com.smedialink.common.base.MessageEvent
import com.smedialink.common.base.handle
import es.dmoral.toasty.Toasty
import javax.inject.Inject
import kotlin.properties.Delegates

class ToastDelegate @Inject constructor(
) : Observer<LiveEvent> {

    override fun onChanged(event: LiveEvent?) {
        when (event) {
            is MessageEvent -> event.handle { showSuccess(it.text) }
            is ErrorEvent -> event.handle { showError(it.errorText) }
        }
    }

    private var context: Context by Delegates.notNull()

    fun attach(context: Context) {
        this.context = context
    }

    fun showError(text: String) {
        Toasty.error(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showSuccess(text: String) {
        Toasty.success(context, text, Toast.LENGTH_SHORT).show()
    }

}
