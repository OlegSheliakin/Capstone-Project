package home.oleg.placesnearme.core_presentation.delegate

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty
import javax.inject.Inject
import kotlin.properties.Delegates

class ToastDelegate @Inject constructor() {

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
