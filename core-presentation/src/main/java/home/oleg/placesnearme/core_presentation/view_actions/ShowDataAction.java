package home.oleg.placesnearme.core_presentation.view_actions;

import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import home.oleg.placesnearme.core_presentation.base.DataView;

public class ShowDataAction<R, V extends DataView<R>> implements Action<V> {

    private final R data;

    private ShowDataAction(@NonNull R data) {
        this.data = data;
    }

    public static <R, V extends DataView<R>> ShowDataAction<R, V> create(R data) {
        return new ShowDataAction<>(data);
    }

    @Override
    public void perform(@NonNull V dataView) {
        Optional.of(data).ifPresent(dataView::showData);

    }

}
