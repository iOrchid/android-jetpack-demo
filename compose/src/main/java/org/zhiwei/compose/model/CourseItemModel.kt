package org.zhiwei.compose.model

import androidx.compose.runtime.Composable

/**
 * 每个单元笔记的item的数据类
 */
internal data class CourseItemModel(
    val title: String,
    val description: String,
    val ui: @Composable () -> Unit,
)