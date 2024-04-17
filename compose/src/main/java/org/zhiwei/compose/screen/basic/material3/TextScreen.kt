package org.zhiwei.compose.screen.basic.material3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Text
import org.zhiwei.jetpack.R

/**
 * 演示material3的Text文本框的使用
 * 颜色值采取自[中国传统色](https://colors.masantu.com/#/)
 * 这个也不错[中国传统色](https://www.zhongguose.com/)
 */
@OptIn(ExperimentalTextApi::class)
@Composable
internal fun Text_Screen(modifier: Modifier = Modifier) {
    LazyColumn(modifier.fillMaxSize()) {
        item {
            //1～9
            CommonTextProperty()
        }
        item {
            //10、给Text绘制背景渐变色,其实也就是通过Modifier的属性设置的，不只是Text，所有Compose组件都可以
            Title_Desc_Text(desc = "10、Background 背景色")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = "素馨黄",
                    modifier = Modifier
                        .background(
                            Color(0xFFFCCB16),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp),
                    color = Color.White
                )
                Text(
                    text = "竹篁绿",
                    modifier = Modifier
                        .background(
                            Color(0xFFB9DEC9),
                            //指定切角
                            shape = CutCornerShape(topStart = 8.dp)
                        )
                        .padding(10.dp),
                    color = Color.White
                )
                //背景色可配置brush实现渐变色效果
                Text(
                    text = "碧青桃红蕈紫",
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF5CB3CC),
                                    Color(0xFFF0ADA0),
                                    Color(0xFF815C94),
                                )
                            ), shape = CircleShape
                        )
                        .padding(10.dp),
                    color = Color.White
                )
            }
            //11、给Text绘制边框类似于背景色
            Title_Desc_Text(desc = "11、Border 边框")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = "素馨黄",
                    modifier = Modifier
                        .border(
                            2.dp,
                            Color(0xFFFCCB16),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp),
                    color = Color(0xFFFCCB16)
                )
                Text(
                    text = "竹篁绿",
                    modifier = Modifier
                        .border(
                            border = BorderStroke(
                                5.dp,
                                Color(0xFFB9DEC9),
                            ),
                            //指定切角
                            shape = CutCornerShape(topStart = 8.dp)
                        )
                        .padding(10.dp),
                    color = Color(0xFFB9DEC9)
                )
                //边框可配置brush实现渐变色效果，但是似乎text字本身不能渐变色
                Text(
                    text = "碧青桃红蕈紫",
                    modifier = Modifier
                        .border(
                            3.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF5CB3CC),
                                    Color(0xFFF0ADA0),
                                    Color(0xFF815C94),
                                )
                            ), shape = CircleShape
                        )
                        .padding(10.dp),
                    color = Color(0xFF815C94)
                )
            }
            //12、shadow 阴影
            Title_Desc_Text(desc = "12、Shadow 投影")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = "墨灰",
                    color = Color(0xFF758A99),
                    fontSize = 28.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            //投影色
                            Color(0xFF493131),
                            blurRadius = 15f,//高斯模糊的半径参数，越大投影越模糊，越小投影越清晰
                            //投影的偏移量
                            offset = Offset(10f, 10f)
                        )
                    )
                )
                Text(
                    text = "湖水蓝",
                    color = Color(0xFFB0D5DF),
                    fontSize = 28.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            Color(0xFFBACCD9),
                            blurRadius = 5f,
                            //投影的偏移量,也就是控制光源位置，改变投影
                            offset = Offset(-10f, -10f)
                        )
                    )
                )
                Text(
                    text = "霜色",
                    color = Color(0xFFE9F1F6),
                    fontSize = 28.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            Color(0xFFFFFBF0),
                            blurRadius = 20f,
                            offset = Offset(5f, 5f)
                        )
                    )
                )
            }
            //13、spannable text 就是文本修饰，一个字符串可配置不同颜色字号风格等。
            Title_Desc_Text(desc = "13、Spannable Text 修饰文本字符丰富多样化")
            Row {
                val str = buildAnnotatedString {
                    append("青青河边草，悠悠到海角。野火烧不尽，相思情未了。")
                    //左闭右开区间，[start,end),SpanStyle可设置字体，颜色，大小，字重，风格等多种属性
                    addStyle(SpanStyle(Color(0xFF00E079), fontSize = 22.sp), start = 0, end = 2)
                    addStyle(
                        SpanStyle(
                            Color(0xFF22A2C3),
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        ), start = 2, end = 4
                    )
                    addStyle(
                        SpanStyle(
                            Color(0xFFD6ECF0), fontSize = 16.sp,
                            textDecoration = TextDecoration.LineThrough,
                            background = Color.Magenta
                        ), start = 8, end = 11
                    )
                    addStyle(
                        SpanStyle(
                            Color(0xFFFEBA07), fontSize = 32.sp, shadow = Shadow(
                                Color.Red,
                                Offset(5f, 5f)
                            )
                        ), start = 12, end = 14
                    )
                    //段落的样式配置，spanStyle作用于字符级别，这个是段落级别。类似于插入分段符号
                    addStyle(ParagraphStyle(), 18, 20)
                }

                Text(
                    text = str, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            //14、spannable 配合ClickableText构建文本点击
            Title_Desc_Text(desc = "14、Clickable Text 可点击的文本")
            Row {
                val str = buildAnnotatedString {
                    append("Github链接可点击")
                    addStyle(
                        SpanStyle(
                            Color(0xFF2578B5), fontSize = 22.sp,
                        ), start = 0, end = 6
                    )
                    //添加可点击，给Github这几个字
                    addUrlAnnotation(UrlAnnotation("https://github.com/iOrchid"), 0, 6)
                }
                //用于处理uri跳转的
                val uriHandler: UriHandler = LocalUriHandler.current
                ClickableText(text = str) {
                    str.getUrlAnnotations(it, it).firstOrNull()?.let { asr ->
                        uriHandler.openUri(asr.item.url)
                    }
                }
            }
            //15、subscript和superscript上下角标
            Title_Desc_Text(desc = "15、subscript和superscript上下角标")
            Row {
                val subscript =
                    SpanStyle(Color.Red, fontSize = 14.sp, baselineShift = BaselineShift.Subscript)
                val superscript = SpanStyle(
                    Color.Green,
                    fontSize = 14.sp,
                    baselineShift = BaselineShift.Superscript
                )

                //质能方程
                Text(text = buildAnnotatedString {
                    append("E=mc")
                    withStyle(superscript) {
                        append("2")
                    }
                }, fontSize = 26.sp)
                Spacer(modifier = Modifier.width(20.dp))
                //化学公式
                Text(text = buildAnnotatedString {
                    append("CH")
                    withStyle(subscript) {
                        append("4")
                    }
                    append("+H")
                    withStyle(subscript) {
                        append("2")
                    }
                    append("O=CO+3H")
                    withStyle(subscript) {
                        append("2")
                    }
                }, fontSize = 26.sp)
            }
            //16、可选择文本，其实就是用SelectionContainer作为容器后，内部就可被选择
            Title_Desc_Text(desc = "16、Selectable Text 可自由选择文本")
            SelectionContainer {
                val poetry = """君不见，黄河之水天上来，奔流到海不复回。
                |君不见，高堂明镜悲白发，朝如青丝暮成雪！
                |人生得意须尽欢，莫使金樽空对月。
                |天生我材必有用，千金散尽还复来。
                |烹羊宰牛且为乐，会须一饮三百杯。
                |岑夫子，丹丘生，将进酒，杯莫停。
                |与君歌一曲，请君为我倾耳听。
                |钟鼓馔玉不足贵，但愿长醉不复醒。
                |古来圣贤皆寂寞，惟有饮者留其名。
                |陈王昔时宴平乐，斗酒十千恣欢谑。
                |主人何为言少钱，径须沽取对君酌。
                |五花马、千金裘，呼儿将出换美酒，与尔同销万古愁！""".trimMargin()//默认是英文符号的|，可以指定
                Text(text = poetry, color = Color(0xFF96C24E), fontSize = 14.sp)
            }

            //加个底部边距,便于可以操作上面的文本
            HorizontalDivider(thickness = 20.dp)
        }

    }
}

/**
 * Text控件的常规属性用法
 */
@Composable
private fun CommonTextProperty() {
    Title_Text(title = "Text")
    //1、字体颜色
    Title_Desc_Text(desc = "1、Font Color 字体颜色")
    TextRow {
        Text(text = "玄色", color = Color(0XFF622A1D))
        Text(text = "景泰蓝", color = Color(0XFF8ABCD1))
        Text(text = "雪白", color = Color(0XFFF2FDFF))
        Text(text = "木槿紫", color = Color(0XFFA381BA))
        Text(text = "朱红", color = Color(0XFFED5126))
        Text(text = "苍青", color = Color(0XFF7397AB))
        Text(text = "雁灰", color = Color(0XFF80766E))
        Text(text = "玉簪绿", color = Color(0XFFA4CAB6))
        Text(text = "雌黄", color = Color(0XFFFFC64B))
    }
    //2、字体大小
    Title_Desc_Text(desc = "2、FontSize 字号大小")
    TextRow {
        //字号单位有三种，sp，em，Unspecified，为定义就是默认sp；compose提供了扩展函数，所以可以使用.sp,.em,等
        Text(text = "14sp", fontSize = 14.sp)
        Text(text = "18em", fontSize = 18.em)
        Text(text = "24sp", fontSize = TextUnit(24f, TextUnitType.Sp))
        Text(text = "36sp", fontSize = 36.sp)
    }
    //3、字体样式
    Title_Desc_Text(desc = "3、FontStyle 字的样式，端正(Normal)或倾斜(Italic)")
    TextRow {
        Text(text = "端正Normal", fontStyle = FontStyle.Normal)
        Text(text = "倾斜Italic", fontStyle = FontStyle.Italic)
    }
    //4、字重
    Title_Desc_Text(desc = "4、FontWeight 字重，理解为笔画粗细")
    TextRow {
        Text(text = "Black", fontWeight = FontWeight.Black)
        Text(text = "Bold", fontWeight = FontWeight.Bold)
        Text(text = "ExtraBold", fontWeight = FontWeight.ExtraBold)
        Text(text = "ExtraLight", fontWeight = FontWeight.ExtraLight)
        Text(text = "Light", fontWeight = FontWeight.Light)
        Text(text = "Medium", fontWeight = FontWeight.Medium)
    }
    TextRow {
        Text(text = "Normal", fontWeight = FontWeight.Normal)
        Text(text = "SemiBold", fontWeight = FontWeight.SemiBold)
        Text(text = "Thin", fontWeight = FontWeight.Thin)
        Text(text = "W100", fontWeight = FontWeight.W100)
        Text(text = "W200", fontWeight = FontWeight.W200)
        Text(text = "W300", fontWeight = FontWeight.W300)
    }
    TextRow {
        Text(text = "W400", fontWeight = FontWeight.W400)
        Text(text = "W500", fontWeight = FontWeight.W500)
        Text(text = "W600", fontWeight = FontWeight.W600)
        Text(text = "W700", fontWeight = FontWeight.W700)
        Text(text = "W800", fontWeight = FontWeight.W800)
        Text(text = "W900", fontWeight = FontWeight.W900)
    }
    //5、字体类型
    Title_Desc_Text(desc = "5、FontFamily 字体，就是对应不同字体文件，如隶体，草体，篆体等。")
    TextRow {
        Text(text = "Cursive", fontFamily = FontFamily.Cursive)
        Text(text = "Default", fontFamily = FontFamily.Default)
        Text(text = "Monospace", fontFamily = FontFamily.Monospace)
        Text(text = "SansSerif", fontFamily = FontFamily.SansSerif)
        Text(text = "Serif", fontFamily = FontFamily.Serif)
        //可以使用自定义的字体，R.font资源引用形式，或者assets中或者file形式都可以。
//                val context = LocalContext.current
//                Text(text = "Serif", fontFamily = FontFamily(Typeface.createFromAsset(context.assets,"fontNameInAssets")))
        Text(
            text = "繁体字",
            fontFamily = FontFamily(Font(R.font.hongkong))
        )
    }
    //6、字间距，单个字符彼此间的空隙
    Title_Desc_Text(desc = "6、Letter Spacing ，就是字符间距。")
    TextRow {
        Text(text = "LS:0.4sp", letterSpacing = 0.4.sp)
        Text(text = "LS:1sp", letterSpacing = 1.sp)
        Text(text = "LS:2sp", letterSpacing = 2.sp)
        Text(text = "LS:4sp", letterSpacing = 4.sp)
    }
    //注意汉字字符本身就占有两个字节位，感知起来就不明显
    TextRow {
        Text(text = "甲辰:0.4sp", letterSpacing = 0.4.sp)
        Text(text = "乙巳:1sp", letterSpacing = 1.sp)
        Text(text = "丙午:2sp", letterSpacing = 2.sp)
        Text(text = "丁未:4sp", letterSpacing = 4.sp)
    }
    //7、字面装饰，也就是下划线，删除线
    Title_Desc_Text(desc = "7、Text Decoration，即字面装饰，如删除线，下划线样式。")
    TextRow {
        Text(text = "None", textDecoration = TextDecoration.None)
        Text(text = "LineThrough", textDecoration = TextDecoration.LineThrough)
        Text(text = "Underline", textDecoration = TextDecoration.Underline)
        Text(
            text = "下划与删除",
            textDecoration = TextDecoration.combine(
                listOf(
                    TextDecoration.LineThrough,
                    TextDecoration.Underline
                )
            )
        )
    }
    //8、行高，一般使用textUnit都是sp，这里可以看出不同行高的效果；可以用em单位，但是不常用。
    Title_Desc_Text(desc = "8、Line Height ，即行高。")
    TextColumn {
        Text(
            text = "清明时节雨纷纷，路上行人欲断魂。借问酒家何处有？牧童遥指杏花村。-- 唐·杜牧 《清明》",
            lineHeight = 15.sp
        )
        HorizontalDivider(thickness = 2.dp, color = Color.White)
        Text(
            text = "无花无酒过清明，兴味萧然似野僧。昨日邻家乞新火，晓窗分与读书灯。-- 宋·王禹偁 《清明》",
            lineHeight = 20.sp
        )
        HorizontalDivider(thickness = 2.dp, color = Color.White)
        Text(
            text = "帝里重清明，人心自愁思。车声上路合，柳色东城翠。花落草齐生，莺飞蝶双戏。空堂坐相忆，酌茗聊代醉。-- 唐·孟浩然 《清明即事》",
            lineHeight = 25.sp
        )
        HorizontalDivider(thickness = 2.dp, color = Color.White)
        Text(
            text = "好风胧月清明夜，碧砌红轩刺史家。独绕回廊行复歇，遥听弦管暗看花。-- 唐·白居易 《清明夜》",
            lineHeight = 30.sp
        )
    }
    //9、overflow，超出范围的处理，即处理超长字符的策略，尤其是单行模式下。
    // 注意⚠️：使用maxLines来限定 则到行尾会字符换行而根据TextOverflow显示其余效果；
    // softWrap=false就是不会触发换行，长文本感知不到边界；默认softWrap是true
    Title_Desc_Text(desc = "9、Overflow ，限定行数模式下超长文本的处理。")
    //这里使用fillMaxWidth可以传参来设置实际占比，而非默认的完全
    TextColumn(Modifier.fillMaxWidth(0.8f)) {
        //先看看maxLine = 1和softWrap=false的效果对比
        Text(
            text = "碧玉妆成一树高，万条垂下绿丝绦。不知细叶谁裁出，二月春风似剪刀。-- 唐·贺知章 《咏柳》",
            maxLines = 1,
        )
        Text(
            text = "碧玉妆成一树高，万条垂下绿丝绦。不知细叶谁裁出，二月春风似剪刀。-- 唐·贺知章 《咏柳》",
            softWrap = false
        )
        //todo 下面对比不同的方式来显示长文本
        HorizontalDivider(thickness = 2.dp, color = Color.White)
        //同样是clip，softWrap=false和maxLines=1的效果也不同，⚠️softWrap真的字会被砍半，它感知不到边界了。
        Text(
            text = "胜日寻芳泗水滨， 无边光景一时新。等闲识得东风面， 万紫千红总是春。-- 宋·朱熹 《春日》",
            overflow = TextOverflow.Clip,//clip就是直接裁剪掉，UI上就看不到超出的文字，也无某种提示效果。
            maxLines = 1,//这里使用maxLines限定，来让文本超出
        )
        Text(
            text = "胜日寻芳泗水滨， 无边光景一时新。等闲识得东风面， 万紫千红总是春。-- 宋·朱熹 《春日》",
            overflow = TextOverflow.Clip,//clip就是直接裁剪掉，UI上就看不到超出的文字，也无某种提示效果。
            softWrap = false
        )
        //Ellipsis方式下，softWrap和maxLines效果一致
        Text(
            text = "人间四月芳菲尽，山寺桃花始盛开。长恨春归无觅处，不知转入此中来。-- 唐·白居易 《大林寺桃花》",
            overflow = TextOverflow.Ellipsis,//Ellipsis会在末尾...形式让UI看起来知道文本超长了。
            softWrap = false
        )
        Text(
            text = "人间四月芳菲尽，山寺桃花始盛开。长恨春归无觅处，不知转入此中来。-- 唐·白居易 《大林寺桃花》",
            overflow = TextOverflow.Ellipsis,//Ellipsis会在末尾...形式让UI看起来知道文本超长了。
            maxLines = 1
        )
        //TextOverflow.Visible只有配合softWrap=false才看出它的效果,且此时maxLines设置无效
        Text(
            text = "去年今日此门中，人面桃花相映红。人面不知何处去，桃花依旧笑春风。-- 唐·崔护 《题都城南庄》",
            overflow = TextOverflow.Visible,//作用就是可以无视外部容器的边界，只要屏幕还有空间，就径直去显示给用户；⚠️需要softWrap=false才有效。
            softWrap = false
        )
        Text(
            text = "去年今日此门中，人面桃花相映红。人面不知何处去，桃花依旧笑春风。-- 唐·崔护 《题都城南庄》",
            overflow = TextOverflow.Visible,
            maxLines = 2,//⚠️⚠️一旦有softWrap = false，maxLines即失效
            softWrap = false,
        )
    }
}

//region 内部封装的compose组件

/**
 * 简单封装的，用于text一行展示的row控件
 */
@Composable
private fun TextRow(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        content()
    }
}

/**
 * 简单封装的，用于清晰展示text演示的column
 */
@Composable
private fun TextColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

//endregion

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun TextScreen_Preview() {
    Text_Screen()
}