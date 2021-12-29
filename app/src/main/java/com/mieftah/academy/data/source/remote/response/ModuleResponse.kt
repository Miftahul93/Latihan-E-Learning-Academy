package com.mieftah.academy.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModuleResponse(
    var modulId: String,
    var courseId: String,
    var title: String,
    var position: Int
) : Parcelable
