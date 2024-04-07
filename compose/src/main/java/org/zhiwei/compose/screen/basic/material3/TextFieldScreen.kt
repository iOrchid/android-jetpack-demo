package org.zhiwei.compose.screen.basic.material3

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrueltyFree
import androidx.compose.material.icons.sharp.AlternateEmail
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * 文本输入框，编辑框的控件
 */
@Composable
internal fun TextField_Screen(modifier: Modifier = Modifier) {

    LazyColumn(modifier.fillMaxWidth()) {
        val tfModifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
        item {
            //常用属性的演示使用
            CommonAttribute(tfModifier)
        }

        item {
            //输入类型的限定
            Title_Desc_Text(desc = "6、KeyboardOptions 键盘输入类型")
            val otf = remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = otf.value,
                onValueChange = { otf.value = it },
                modifier = tfModifier,
                label = { Text(text = "KeyboardType.Password") },
                placeholder = { Text(text = "123456789") },
                //设置输入类型，并配合显示掩码方式
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                //这里使用的是密码类型，默认给...隐藏显示效果
                visualTransformation = PasswordVisualTransformation()
            )
            //其实配置不同的输入类型，也就是限定键盘可输入的字符方式，或者配合显示的方式
            OutlinedTextField(
                value = otf.value,
                onValueChange = { otf.value = it },
                modifier = tfModifier,
                label = { Text(text = "KeyboardType.Phone") },
                placeholder = { Text(text = "5555-5555-555") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            )

            Title_Desc_Text(desc = "7、IME， 配合键盘action按钮")
            OutlinedTextField(
                value = otf.value,
                onValueChange = { otf.value = it },
                modifier = tfModifier,
                label = { Text(text = "search") },
                placeholder = { Text(text = "it's so easy") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    //imeAction的设置，会将输入法此时键盘右下角的按钮，变成对应类型
                    imeAction = ImeAction.Search
                ),
            )
            //系统提供键盘管理器
            val keyboardController = LocalSoftwareKeyboardController.current
            val context = LocalContext.current
            OutlinedTextField(
                value = otf.value,
                onValueChange = { otf.value = it },
                modifier = tfModifier,
                label = { Text(text = "Search") },
                placeholder = { Text(text = "click go will toast") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    //imeAction的设置，会将输入法此时键盘右下角的按钮，变成对应类型
                    imeAction = ImeAction.Go
                ),
                //响应上面imeAction的动作
                keyboardActions = KeyboardActions(onGo = {
                    keyboardController?.hide()
                    Toast.makeText(context, "点击了键盘的Go的操作", Toast.LENGTH_SHORT).show()
                })
            )

            Title_Desc_Text(desc = "8、VisualTransformation，自定义设置显示效果，比如实现关键信息遮蔽，掩码等。")
            OutlinedTextField(
                value = otf.value,
                onValueChange = { otf.value = it },
                modifier = tfModifier,
                label = { Text(text = "#掩码") },
                placeholder = { Text(text = "密码会被用#掩饰") },
                visualTransformation = PasswordVisualTransformation('#'),
            )
            //简单的模拟数字匹配规则
            val numberRegex = "^[0-9]+\$".toRegex()
            OutlinedTextField(
                value = otf.value,
                onValueChange = { newValue ->
                    //这里做了限定，超出字符位数和规则不匹配，都不会赋值。
                    //todo ⚠️，如果这里不做处理，visualTransformation只是显示而已，实际的值还是会受输入影响的。
                    val text = newValue.text
                    if (text.length <= 11 && numberRegex.matches(text)) {
                        otf.value = newValue
                    }
                },
                modifier = tfModifier,
                label = { Text(text = "自定义掩码和输入格式") },
                placeholder = { Text(text = "手机号输入格式") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                //⚠️ 同理，可根据需要，自定义输入过滤方式
                visualTransformation = PhoneVisualTransformation(),
            )
        }

    }

}

/**
 * 常用属性的使用演示
 */
@Composable
private fun CommonAttribute(modifier: Modifier) {
    Title_Text(title = "TextField 文本输入框/编辑框")
    Title_Desc_Text(desc = "1、文本输入与编辑，使用remember来记录mutableState，存储输入框的内容")
    //使用remember来记录一个mutable的state，存储的是textField的value;使用by 关键字的方式，下面使用字面量，如果是=等于号，那么就要.value
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val textFieldValue2 = remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        //初始默认值
        value = textFieldValue,
        //输入编辑变化的时候，⚠️必须要写这个，不然输入框不会接纳显示
        onValueChange = { textFieldValue = it },
        modifier = modifier,
        //类似于label的，占位提示，但是它在输入框内，且输入文字后会消失。
        placeholder = {
//                    Icon(imageVector = Icons.Sharp.DeliveryDining, contentDescription = "占位符号")
            Text(text = "占位符号文本")
        }
    )
    TextField(
        value = textFieldValue2.value,
        onValueChange = { textFieldValue2.value = it },
        modifier = modifier,
        //label是默认填充，但不影响实际输入内容的一个提示，输入文本后它会悬浮起来，不消失
        label = { Text(text = "悬浮的提示词", fontSize = 12.sp) }
    )
    //2、错误提示
    Title_Desc_Text(desc = "2、Error信息提示")
    val errorText = remember {
        mutableStateOf(TextFieldValue("输入为空的话会错误提示"))
    }
    //可以注意到，如果输入内容为清空，下面会显示红色error的警告色，光标也会是红色
    TextField(
        value = errorText.value,
        onValueChange = { newValue ->
            errorText.value = newValue
        },
        modifier = modifier,
        isError = errorText.value.text.isEmpty()
    )

    Title_Desc_Text(desc = "3、Colors & TextStyle 配置色和字体样式")
    TextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        modifier = modifier,
        placeholder = { Text(text = "PlaceHolder") },
        label = { Text(text = "Label") },
        colors = TextFieldDefaults.colors(
            //这里仅仅演示了配置聚焦与否的时候，文本和背景的颜色；
            //还可以有更多比如label，placeHolder，error，icon，cursor等各种颜色，不同状态下的自定义配置
            focusedContainerColor = Color(0xFFEEAA9C),
            unfocusedContainerColor = Color(0xFFF7CDBC),
            focusedTextColor = Color(0xFFD1E2DE),
            unfocusedTextColor = Color(0xFFE9F1F6),
        ),
        //通过textStyle也可以对文本样式做更多样化的设置定义
        textStyle = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Thin,
            fontFamily = FontFamily.Serif
        )
    )
    Title_Desc_Text(desc = "4、Shape 形状")
    TextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        modifier = modifier,
        placeholder = { Text(text = "PlaceHolder") },
        label = { Text(text = "Label") },
        shape = RoundedCornerShape(20.dp)
    )
    TextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        modifier = modifier,
        placeholder = { Text(text = "PlaceHolder") },
        label = { Text(text = "Label") },
        shape = CutCornerShape(topStart = 10.dp, bottomEnd = 12.dp),
        //可以对比上面的那个，这里是为了去掉输入框的底部的线
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
        ),
    )
    //material3系统提供的基础的输入控件
    Title_Desc_Text(desc = "readOnly 和 enable=false")
    //BasicTextField是基础的输入框，没有提示词和占位符之类的，似乎也看不出是输入框
    BasicTextField(
        value = textFieldValue,
        readOnly = true,//如此就是只读，不可触发界面输入
        onValueChange = { textFieldValue = it },
        singleLine = true,
        modifier = modifier
            .height(30.dp)
            .background(Color(0xFFE9D7DF)),
    )
    //也可以通过surface面板来设置一些组件的样式
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFD294D3),
        contentColor = Color.Red,
        shadowElevation = 5.dp,
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            singleLine = true,
            //enable只是管住自身控件不可用，不可交互。但是onValueChange，如果和别的共用了，还是可以看到变化显示的
            enabled = false,
            modifier = modifier.height(30.dp)
        )
    }
    Title_Desc_Text(desc = "5、Outline样式")
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        modifier = modifier,
        label = { Text("Label") },
        placeholder = { Text("Placeholder") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Sharp.AlternateEmail,
                contentDescription = "首图标"
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.CrueltyFree,
                contentDescription = "尾图标"
            )
        },
        prefix = { Text(text = "Prefix是") },
        suffix = { Text(text = "Suffix") },
        supportingText = { Text(text = "Supporting Text是底部提示词") }
    )

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        modifier = modifier,
        label = { Text("SingleLine") },
        singleLine = true
    )

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        modifier = modifier,
        label = { Text("maxLines 2") },
        placeholder = { Text("占位输入符") },
        maxLines = 2//可设定最大行数
    )
}


/**
 * 模拟自定义的转换方式，将输入的数字，格式化改为xxx-xxx-xxx格式
 * ```
 * XXX-XXX-XXXXX
 * ```
 */
private class PhoneVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        //手机号默认最多11位，电话号可能8位，7位，
        val trimmed = if (text.text.length > 11) text.text.substring(0..10) else text.text

        var output = ""
        //按照xxx-xxx-xxxxx的手机号格式，给数字对应位置后 插入-
        for (i in trimmed.indices) {
            output += trimmed[i]
            if (i % 3 == 2 && i != 8) output += "-"
        }
        return TransformedText(AnnotatedString(output), phoneOffsetMap)
    }

    //转化需要的偏移规则
    private val phoneOffsetMap = object : OffsetMapping {
        //这里是实际输入的字符，跟显示效果的转化
        override fun originalToTransformed(offset: Int): Int {
            return when (offset) {
                // XXX 前三个数，不需要偏移，因为也没有插入符号,
                in 0..2 -> offset
                // XXX-XXX 4到6的数字，前面有一个插入符，所以+1
                in 3..5 -> offset + 1
                // XXX-XXX-XXXXX 7到11的数字，前面有两个插入符，所以+2
                in 6..10 -> offset + 2
                else -> 13//总的字符就是13，
            }
        }

        //这里是显示的符号，跟实际输入的转化
        override fun transformedToOriginal(offset: Int): Int {
            return when (offset) {
                // XXX 前三个数，不需要偏移，因为也没有插入符号,
                in 0..2 -> offset
                // XXX-XXX 4到6的数字，前面有一个插入符，所以-1
                in 3..5 -> offset - 1
                // XXX-XXX-XXXXX 7到11的数字，前面有两个插入符，所以-2
                in 6..10 -> offset - 2
                else -> 11//去除插入符的数字就是11个，
            }
        }

    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun TextFieldScreenPreview() {
    TextField_Screen()
}