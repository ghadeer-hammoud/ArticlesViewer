package com.ghadeer.articlesviewer.data.models


data class Media(
   val type: String = "",
   val subtype: String = "",
   val caption: String = "",
   val copyright: String = "",
   val approvedForSyndication: Int = 0,
   val metadata: List<MediaMetaData> = emptyList(),
)