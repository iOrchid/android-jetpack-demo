package org.zhiwei.compose.model

import androidx.compose.runtime.Composable

/**
 * 首页配合Tab和Pager的关联数据类
 */
data class TabPagerModel(val title: String, val page: @Composable (() -> Unit))