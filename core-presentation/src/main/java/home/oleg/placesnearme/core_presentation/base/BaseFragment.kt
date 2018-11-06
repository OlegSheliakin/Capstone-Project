package home.oleg.placesnearme.core_presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import home.oleg.placesnearme.core_presentation.R
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
abstract class BaseFragment: Fragment() {

    override fun onAttach(context: Context?) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected abstract fun inject()

}