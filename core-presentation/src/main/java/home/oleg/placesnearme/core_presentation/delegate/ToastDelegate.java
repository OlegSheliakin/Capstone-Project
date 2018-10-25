package home.oleg.placesnearme.core_presentation.delegate;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import home.oleg.placesnearme.core_presentation.base.ErrorView;

public class ToastDelegate {

    private Context context;

    @Inject
    public ToastDelegate() {

    }

    public void attach(Context context) {
        this.context = context;
    }

    public void showError(String text) {
        Toasty.error(context, text, Toast.LENGTH_SHORT).show();
    }

    public void showSuccess(String text) {
        Toasty.success(context, text, Toast.LENGTH_SHORT).show();
    }

}
