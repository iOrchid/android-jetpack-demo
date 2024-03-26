package org.zhiwei.jetpack.components.paging

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.max

/**
 * 0、添加paging依赖库
 * paging3的库使用步骤，1、创建model
 */
data class Teacher(
    val id: Int,
    val name: String,
    val title: String,
    val created: LocalDateTime,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    val createText: String = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss").format(created)
}

/**
 * 步骤2，创建PagingSource
 */
class TeacherPagingSource : PagingSource<Int, Teacher>() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val firstCreatedTime = LocalDateTime.now()

    override fun getRefreshKey(state: PagingState<Int, Teacher>): Int? {
        val index = state.anchorPosition ?: return null
        val teacher = state.closestItemToPosition(anchorPosition = index) ?: return null
        return ensureValidKey(teacher.id - (state.config.pageSize / 2))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Teacher> {
        val startKey = params.key ?: START_INDEX
        val range = startKey.until(startKey + params.loadSize)
        //模拟延迟加载数据效果
        if (startKey != START_INDEX) delay(3000L)
        //构建Page的参数释义：
//        LoadResult.Page(
//            data = , //页面数据的list，
//            prevKey = ,//用于向前加载数据的分页标记 if（page>1)page-1 else null
//            nextKey = ,//用于判断加载下页数据时候，分页标记的区分，if(list.isNOtEmpty) page +1 else null
//        )
        return LoadResult.Page(
            data = range.map { number ->
                Teacher(
                    id = number,
                    name = "张三$number",
                    title = "教授 $number",
                    created = firstCreatedTime.minusDays(number.toLong())
                )
            }, prevKey = when (startKey) {
                START_INDEX -> null
                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
                    START_INDEX -> null
                    else -> prevKey
                }
            },
            nextKey = range.last + 1
        )
    }

    private fun ensureValidKey(key: Int) = max(START_INDEX, key)


    companion object {
        private const val START_INDEX = 0
    }
}