package home.oleg.placesnearme.feature_map.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Sheliakin on 25.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class CheckedItem<T> {

    private boolean isChecked;
    private final T data;

    public CheckedItem(boolean isChecked, T data) {
        this.isChecked = isChecked;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public static  <T> List<CheckedItem<T>> wrap(List<T> dataList) {
        List<CheckedItem<T>> result = new ArrayList<>();

        for (T data : dataList) {
            result.add(new CheckedItem<>(false, data));
        }

        return result;
    }
}
