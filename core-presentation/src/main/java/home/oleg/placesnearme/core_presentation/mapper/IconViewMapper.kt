package home.oleg.placesnearme.core_presentation.mapper

import home.oleg.placesnearme.core_presentation.viewdata.IconViewData

object IconViewMapper {

    fun map(prefix: String, suffix: String) = IconViewData(
            prefix = prefix,
            suffix = suffix
    )

}
