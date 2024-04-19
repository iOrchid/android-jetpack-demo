package org.zhiwei.compose.screen.basic.material3

import android.widget.Toast
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.screen.basic.Surface_Shape_Clickable_Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Material3ModalBottomSheetUI(sheetValue: SheetValue = SheetValue.PartiallyExpanded) {
    val sheetState =
        rememberStandardBottomSheetState(initialValue = sheetValue, skipHiddenState = false)
    val context = LocalContext.current
    //material3的modalBottomSheet，这里只是个sheet，不包含其他页面内容
    ModalBottomSheet(
        onDismissRequest = {
            //处理BottomSheet外部点击的逻辑，也可能需要包含页面的返回处理
            Toast.makeText(
                context,
                "ModalSheet要隐藏了",
                Toast.LENGTH_SHORT
            ).show()
        },
        sheetState = sheetState,
        sheetMaxWidth = 400.dp,
        shape = CutCornerShape(8.dp)
    ) {
        Image_Screen()
    }
}

//material库的ModalBottomSheet，与Material3的不同，
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MaterialModalBottomSheetUI(sheetValue: ModalBottomSheetValue = ModalBottomSheetValue.HalfExpanded) {
    val sheetState = rememberModalBottomSheetState(sheetValue)
    //todo ⚠️ 这里也只是演示了单独使用BottomSheet的用法，实际业务使用，可结合Scaffold，实现topAppBar，侧边栏，等其他效果
    ModalBottomSheetLayout(
        sheetContent = { Surface_Shape_Clickable_Screen() },
        sheetState = sheetState,
    ) {
        Text_Screen()
    }
}


//region IDE预览效果

@OptIn(ExperimentalMaterialApi::class)
private class MaterialModalSheetValue : PreviewParameterProvider<ModalBottomSheetValue> {
    override val values: Sequence<ModalBottomSheetValue>
        get() = sequenceOf(
            ModalBottomSheetValue.Expanded,
            ModalBottomSheetValue.Hidden,
            ModalBottomSheetValue.HalfExpanded,
        )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun MaterialPreview(@PreviewParameter(MaterialModalSheetValue::class) value: ModalBottomSheetValue) {
    MaterialModalBottomSheetUI(value)
}


@OptIn(ExperimentalMaterial3Api::class)
private class ModalSheetValue : PreviewParameterProvider<SheetValue> {
    override val values: Sequence<SheetValue>
        get() = sequenceOf(
            SheetValue.Expanded,
            SheetValue.Hidden,
            SheetValue.PartiallyExpanded,
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Material3ModalBottomSheetPreview(@PreviewParameter(ModalSheetValue::class) value: SheetValue) {
    Material3ModalBottomSheetUI(value)
}
//endregion