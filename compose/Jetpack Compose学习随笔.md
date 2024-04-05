## Jetpack Compose学习随笔

<font color="red">建议必要时候可以参照git的commit来熟悉学习的代码步骤</font>

### 一、基础组件

> 基础View和控件widget的使用

#### 1.Row/Column/Box/Modifier

- Text文本

- Modifier用于修饰调控管理，包含尺寸颜色位置动画和事件等

- Row是**行**水平布局，Column是**列**竖直布局；内有Arrangement管理子控件的分布方式，Alignment对齐方式；

  - Arrangement有水平和竖直的两类，top/bottom与start/end，还有center相关；
    - SpaceEvenly是空间均分；
    - SpaceAround是控件两端均分；
    - SpaceBetween是中间间隔空间均分；可参照constrantlayout的chain作用类似；
  - Alignment就是对齐方式，根据行/列，有区分的对齐top/bottom或start/end；

- Modifier修饰padding和margin

  > **比较特别Modifier**的属性作用按顺序执行的,以此实现margin的效果；
  >
  > 且Modifier的属性作用按顺序执行的；如padding与background先后不一样，则表现出来有颜色的部分就区间不同；

  如：

  ```kotlin
  val outModifier = Modifier
  .width(300.dp)
  .height(300.dp)
  .background(Color.Red)//先填充颜色，再padding，便于观察效果
  .padding(10.dp)
  Row(modifier=outModifier
      .padding(end=20.dp)
      .background(Color.Blue)//这里先padding，再填充color
     ){
    Text("文本第一个");Text("文档第一黑胡椒");Text("😂可以表情包不")
  }
  //其实就是modifier的多种属性依次执行的结果；如上代码，等效于下面
          Row(modifier=Modifier
              .width(300.dp)
              .height(300.dp)
              .background(Color.Green)
              .padding(10.dp)
              .padding(end=20.dp)
              .background(Color.Gray)
          ){
              Text("文本第一个");Text("文档第一黑胡椒");Text("😂可以表情包不")
          }
  ```

  效果则是在绿色的Row容器内有10dp的内边距，然后又与边距有20dp的右边距；多个modifier属性会依次产生作用效果（宽高则是第一次设置为准，不会被覆盖）

  <img src="/Users/guozw/Library/Application Support/typora-user-images/image-20231102150257402.png" alt="image-20231102150257402" style="zoom:50%;" />

  ```kotlin
  Modifier
              .width(200.dp)
              .height(200.dp)
              .shadow(4.dp, RoundedCornerShape(20.dp))
              .background(Color.Green)//先填充颜色，再padding，便于观察效果
              .padding(10.dp)
              .padding(end=20.dp)
              .shadow(4.dp, RoundedCornerShape(50.dp))
              .background(Color.Gray)//这里先padding，再填充color
  //这里就有shadow产生阴影效果，以及裁剪圆角作用，其和background的先后顺序，影响作用效果
  ```

  <img src="/Users/guozw/Library/Application Support/typora-user-images/image-20231102152812004.png" alt="image-20231102152812004" style="zoom:50%;" />

- Box堆叠子控件

- Modifier作用shape，weight权重；

- Spacer空白控件

#### 2. Clickable/Surface

- Modifier有clickable属性，配置onClick；有clip剪裁属性；**注意modifier所有属性设置，顺序执行生效**；
- Surface理解为面板，使用Modifier设置aspectRatio宽高比；
  - surface有shape属性，包含圆角、圆形、剪切等；
  - surface还有border，elevation等属性设置；
  - LocalContext.current获取context对象；

### 二、material组件

> 演示使用material3的设计组件

#### 1. Text

- modifier、color、fontStyle、fontWeight、letterSpacing、textDecoration、lineHeight、softWrap、overflow等属性；

- textStyle不同于fontStyle；可以有shadow;

- buildAnnotatedString用于构建spannable的文本，应用不同的style

  ```kotlin
  val string = buildAnnotatedString{
    //全文本
    append("Hello world my compose spannable string 哈哈哈")
    addStyle(style=SpanStyle(color=Color.Red,fontSize=24.sp),start=0,end=5)//设置hello这个词的颜色字号
    //文本中添加链接跳转，需要将string设置给clickableText，并处理点击事件
          addStringAnnotation(
              tag = "URL",
              annotation = "https://github.com/SmartToolFactory",
              start = startIndex,
              end = endIndex
          )
  }
  //用来处理文本中链接的点击事件跳转web
      val uriHandler: UriHandler = LocalUriHandler.current
  //然后text设置string即可
  ClickableText(
          modifier = modifier
              .padding(16.dp)
              .fillMaxWidth(),
          text = annotatedLinkString,
          onClick = {
              annotatedLinkString
                  .getStringAnnotations(it, it)
                  .firstOrNull()?.let { stringAnnotation ->
                      println("🔥 Clicked: $it, item: ${stringAnnotation.item}")
                      uriHandler.openUri(stringAnnotation.item)
                  }
          }
      )
  ```

- text通过buildAnnotatedString中withStyle设置SpanStyle的baselineShift，可实现上下角标的设置；

  ```kotlin
      val subscript = SpanStyle(
          baselineShift = BaselineShift.Subscript,
          fontSize = 14.sp, // font size of subscript
          color = Color.Blue // color
      )
      val superscript = SpanStyle(
          baselineShift = BaselineShift.Superscript,
          fontSize = 14.sp, // font size of superscript
          color = Color.Red // color
      )
      // create first text
      Text(
          modifier = Modifier.padding(16.dp),
          fontSize = 20.sp,
          text = buildAnnotatedString {
              // instead of directly passing
              // string value to text
              // use append
              append("E = mc")
              withStyle(superscript) {
                  append("2")
              }
          }
      )
  ```

- 通过SelectionContainer容器来实现文本的可选择复制；
- Divider分割线
- modifier的background设置color/shape，可以是渐变色Gradient类型，有Brush生成GradientBrush等；

#### 2. Button

> 按钮Button，material风格的，包含许多新特性和属性用法；

- Compose的Button可以理解为新的组合surface或row容器控件，其content可以是多个控件组合；

- Button、TextButton和OutlinedButton；

- Icon理解为简化版的Image，没有colorFilter等；

- Button有colors属性，通过ButtonDefaults.buttonColors/textButtonColors/outlineButtonColors等可设置对应属性；

  ```kotlin
  Column(
              modifier = modifier01
                  .clip(RoundedCornerShape(20))
                  .background(brush = horizontalGradientBrush)
                  .clickable(onClick = { })
                  .then(modifier)
          ) {
              Text(text = "Horizontal Gradient")
          }
  //这里的modifier是modifier01通过clip/background等作用后，有一个效果，通过then，又再次使用了最初的modifier01的设置属性，再次作用于后续；
  ```

- IconButton其实也就是个Box，里面可以设置Icon，也可层叠其他composable的控件；Icons.Filled等类似的可提供material的系统图标icon；

- remember可提供作用域内的状态缓存；

  ```kotlin
          var checked by remember { mutableStateOf(false) }
  				//icon点击可变化状态，
          IconToggleButton(
              checked = checked,
            //这里是点击check的变换
              onCheckedChange = { checked = it },
              modifier = modifier
          ) {
  						//不同的tint颜色，根据check状态，设置icon的颜色
              val tint by animateColorAsState(
                  targetValue = if (checked) Color(0xffE91E63) else Color(0xffB0BEC5),
                  animationSpec = tween(durationMillis = 400), label = ""
              )
  
              Icon(
                  Icons.Filled.Favorite, tint = tint,
                  contentDescription = null
              )
          }
  ```

  - modifier的clickable中有indication属性，可用于配置点击ripple；
  - rememberRipple内有bounded属性，配置点击ripple是否越界；

- material还有其他button如floatingButton/ExtendedFloatingButton等；

- Chip控件包含border，colors（ChipDefaults生成），leadingIcon等；

#### 3. TextField

- 输入变化及时响应使用remember mutableStateOf TextFieldValue；
- 有属性colors对应TextFieldDefaults创建，label，value，readOnly等；
- Badge控件，只能是icon或者简短文字，用于**红点提示**；
- 可以组合进surface或者其他容器内设置布局风格样式；
- outlineTextField；其maxlines只**表示控件的高度显示的行数**，实际内容输入则不限制；
- keyboardOptions可设置输入样式，比如密码，电话号，数字以及键盘回车键设置等；对应设置visualTransformation的显示密码的样式/掩码方式等（自定义实现）；
- keyboardActions中可以响应对应键盘设置，使用LocalSoftwareKeyboardController.current可控制键盘的hide显示；
- BasicTextField是compose系统提供的，功能简陋；

#### 4. Image

- 实现方式painter=painterResource加载drawable的resource；

- bitmap=ImageBitmap.imageResource,其中context可食用localContext.current;

- painter可以自定义实现类

  ```kotlin
  val customPainter:Painter = object:Painter(){
    override val intrinsicSize:Size
    override fun DrawScope.onDraw()//在onDraw中可以绘制，如drawImage，drawLine等；
  }
  ```

- 可通过DrawScope的函数，结合compose的Canvas来在图片上绘制图层；

- modifier的shadow中可以直接设置clip的shape，自定义实现裁剪形状GenericShape实现；

- Image有colorFilter属性，给图片过滤颜色，BlendMode决定过滤方式；

- Modifier有graphicsLayer属性，可设置图像的变形;

- contentScale属性设置图片填充方式

#### 5. LazyColumn/LazyRow

- 可动态加载items的行列容器，content中有items加载数据list对象；
- rememberLazyListstate记录状态scrollState.animateScrollToItem(0)；rememberCoroutineScope 异步协程操作；
- 行列容器可组合成嵌套布局；
- 有stickyHeader可实现悬浮header的列表，结合group分组更便利；
- **LazyVerticalGrid**容器，columns可分列，GridCells.fixed(3)的形式，实现grid布局；
- ListItem容器，可用做多样化的设置item的实现；Divider实现分隔线；
  - text/secondaryText，trailing可用作icon或者其他composable的；
  - overlineText是标题上的小标题
  - 可配合switch/checkbox等设置；
- LocalDensity屏幕分辨尺寸相关类；
- Slider就是seekbar
- deriveStateOf创建可remember的列表位置信息状态；

#### 6. TopAppBar

- 即toolbar，title/navigationIcon
- actions闭包内可设置类似menu的item及其点击事件；
  - DropdownMenu配合DropdownMenuItem实现菜单；
- TabRow/ScrollableTabRow

#### 7. BottomNavigation

- bottomNavigationItem
- material的组件，scafflod面板用于主题控制；
  - `isFloatingActionButtonDocked`用于挖孔bottomBar；
- AppDrawer/ModalDrawer,也可以使用scaffold配合；
- BottomSheet/ModalBottomSheet
- BottomDrawer区别于sheet，带有背景变暗；
- BackdropScaffold前后面板；

#### 8. 其他控件

- 进度条progressIndicator分为linear和circular
- switch，checkbox，radiobutton
- 通过容器rememberState来实现记录切换，单选组内按钮的功能；
- Slider 就是seekbar；有rangeSlider；
- TriStateCheckbox组合checkbox状态；多个子状态，联合控制父状态；
- **CompositionLocalProvider**用于将数据作用域限定在local范围内；
- SwipeToDismiss用于compose的滑动删除，左右都可滑动；

### 三、布局Layout

> 介绍布局layout相关

#### 1. 自定义modifier

- Modifier的compose会在内存中记录使用的modifier当前作用；then则是生成新的继续作用；
-

### 注意：

- 模块一定要配置

  ```kotlin
  //kotlin项目需要
      id("org.jetbrains.kotlin.android")
  //compose需要下面两个
      buildFeatures {
          compose = true
      }
      composeOptions {
          kotlinCompilerExtensionVersion = "1.5.3"
      }
  ```

  **`Composable`的函数，带返回值的命名小写字母开头，无返回值的，尤其是用于`UI`的，都是大写字母开头。**

## 官方文档随笔[^1]

### 一、基础

- 编程思想

  - 声明式编程范式

    1. `Compose`中的`UI`层`widget`是无状态的；
    2. `UI`提供状态`state`通知逻辑层处理，逻辑层通过`event`向上刷新`UI`

    ![数据与状态下传](https://developer.android.google.cn/static/images/jetpack/compose/mmodel-flow-data.png?hl=zh-cn)

    ![逻辑事件向上](https://developer.android.google.cn/static/images/jetpack/compose/mmodel-flow-events.png?hl=zh-cn)

    ![compose single data flow](https://developer.android.google.cn/static/images/jetpack/compose/state-unidirectional-flow.png?hl=zh-cn)

  - 需要注意的特性点

    - `Compose`可以按任何顺序执行。
    - `Compose`可以并行执行。
    - 重组会跳过尽可能多的`Compose`和 `lambda`。
    - 重组是**乐观**的操作，可能会被取消。
    - `Compose`可能会像动画的每一帧一样**非常频繁**地运行。

  - `Compose`可并行，则要求不应带有副作用等，应确保始终`UI`线程上执行`onClick`等回调来触发附带效应。注意线程安全问题；

- 生命周期

  - `Composable`的生命周期指：进入组合–执行0次或多次重组–退出组合

    ![composable lifecycle](https://developer.android.google.cn/static/images/jetpack/compose/lifecycle-composition.png?hl=zh-cn)

    重组通常由 对 `State<T>`状态对象的修改 而触发；进而对所有读取该`State<T>`的`Composable`
    无法忽略变化的对象执行重组。

    ```kotlin
    @Composable
    fun MyComposable() {
        Column {
          //这里有两个Text的实例对象
            Text("Hello")
            Text("World")
        }
    }
    ```

    ![Composable会实例化多个对象](https://developer.android.google.cn/static/images/jetpack/compose/lifecycle-hierarchy.png?hl=zh-cn)
  
    如上图，一个`Composable`的`Text`多次调用，会是多个对象；

- 重组可能是触发列表位置变动，造成多位置重组，可通过使用`Key`标记，降低重组范围，提升性能；

  ```kotlin
  @Composable
  fun MovieScreen(movies:List<Movie>){
    Column{
      for(movie in movies){
        key(movie.id){//如果不使用key这个标记，movies如果在尾部添加数据变更，重组范围较小，但如果是中间或者前部修改增添，则整个list的item的位置都会变动，重组范围就大。使用key可以标记item的，减少recompose的范围。这里的key对应的id要求是唯一的，在作用于范围内唯一即可。
          MovieOverview(movie)
        }
      }
    }
  }
  ```

- 修饰符

  - 规范建议`modifier`作为`Composable`函数的第一属性参数，可配置。

  - 没有`margin`
    只有`padding`；[说明](https://developer.android.google.cn/jetpack/compose/modifiers?hl=zh-cn)

    > **注意**
    ：明确的顺序可帮助您推断不同的修饰符将如何相互作用。您可以将这一点与基于视图的系统进行比较。在基于视图的系统中，您必须了解框模型，在这种模型中，在元素的“外部”应用外边距，而在元素的“内部”应用内边距，并且背景元素将相应地调整大小。
    *
    *修饰符设计使这种行为变得明确且可预测，并且可让您更好地进行控制，以实现您期望的确切行为。这也说明了为什么没有外边距修饰符，而只有 `padding`
    修饰符。**

  - 修饰符函数顺序作用，请注意顺序。

  - 容器默认`wrapcontent`，也可通过`modifier`指定`size`
    但是不一定总是有效；其内部的控件使用`requiredSize`会优先级更高。

    ```kotlin
    @Composable
    fun A(){
      Row(modifier=Modifier.size(width=100.dp,height = 80.dp)){
        //则最终该容器会150dp大小，requiredSize优先级高于父容器的size
        Image(modifier=Modifier.requiredSize(150.dp))
      }
    }
    ```

    **父容器应设法避免子控件这类不规范行为，通过传递requiredSize值或者其他替代布局的方式**

  - `paddingFromBaseline`用于保持文本基线的边距控制

  - `offset`也可用于调节位置，区别于`padding`，其不会影响控件`measure`的尺寸。

  - 特定`Compose`的修饰符子项，通过作用域限定,例如：

    ```kotlin
    BoxScope.matchParentSize//则仅可用于Box相关的修饰符使用
    ColumnScope/RowScope//有weight权重的配置
    ```

  - 注意`modifier`的参数化和复用，可抽取封装成对象。

- 附带效应Side-Effect

  > Side-Effect附带效应，指：发生在`Composable`函数作用域之外的应用状态变化。**理想状态是单一封装，不带额外效应
  **，应用状态变更，建议使用`Effect Api`中的函数，同时注意不必过度使用`Effect`，仅在与`UI`相关场景使用。

  - `LaunchEffect`可应用启动协程`coroutines`及挂起函数`suspend`，这也是一个`composable`
    函数，内部的协程跟随自身`compose`生命周期创建和取消。
    - `LaunchEffect`是组合函数，只能在组合函数内调用，为了在`composable`
      之外调用，同时也能限定作用域并跟随`compose`的生命周期来取消协程，可使用`rememberCoroutineScope`
    - 一般`Composable`函数在接收的参数发生变化后，会`recompose`
      重组，有些场景，需要数据变化，但不需要重组，可使用`rememberUpdatedState`
  - `DisposableEffect`对应于上面的`LaunchEffect`
    ，这里是用于退出组合的时候，调用的生命周期回调；内部有`onDispose`中可回调，且需要放在闭包最后调用。
  - `SideEffect`，由于非组合函数不能直接调用组合函数，使用`SideEffect`可用于将`composable`
    内的对象状态，共享出去给非`Composable`函数使用。
  - `produceState`与`sideEffect`相反，这里用来将非`Composable`
    的状态转化为组合函数可用的。自身会启动协程，观察`livedata/flow`时，重复数据不会多次触发。
    - 也可自启动内部协程 ，通过`awaitDispose`观察和取消。
  - `derivedStateOf`用于转换其他状态。
  - `remember`可以传多个相关字段
  - `snapshotFlow`可将`Compose`的`State`转化为`Flow`

- 相位

  > 类似于View的渲染分`measure\layout\draw`，在`Compose`中类似，分为`Composition\layout\drawing`

  - 组合：分析可组合函数并创建`UI`相关说明
  - 布局：分为测量和放置
  - 绘制：渲染元素

  ![compose show UI](https://developer.android.google.cn/static/images/jetpack/compose/phases-3-phases.svg?hl=zh-cn)

  一般遵循如上步骤，单项数据流，但在`BoxWithConstraints/LazyColumn/LazyRow`这类容器内，子控件的状态取决于父容器的阶段。

  `Compose`会最低限度的重绘。

  - `state`一般`mutableStateOf`创建，可用`.value`访问，如果是`by `委托的`state`可直接使用字段。

  - 状态`State`可根据`Compose`的三个阶段，分阶段读取不同的值。

    - `MeasureScope.measure`属于测量阶段，`Modifier.offset`属于放置阶段的数值
    - `Canvas/Modifier.drawBehind/Modifier.drawWithContent`的状态变化感知在绘制阶段

    ![compose state phases](https://developer.android.google.cn/static/images/jetpack/compose/phases-state-read-draw.svg?hl=zh-cn)


- 架构

  > 分为`material\fundation\ui\runtime`

  - 控制降级，例如

    ```kotlin
    val color = animateColorAsState(if(condition)Color.Green else Color.Red)
    //可以更灵活的,实现初始就有颜色，api降级。
    val color = remember{Animatable(Color.Gray)}
    LaunchEffect(condition){
      color.animateTo(if(condition)Color.Green else Color.Red)
    }
    ```

- 最佳实践

  1. 巧用`remember`用于缓存，减少计算重绘

  2. 列表组合可使用`key`关键字避免列表的非必要重绘耗能

     ```kotlin
     LazyColumn{
       //如此，列表插入尾部的单个变化，就不会引起所有item都重绘
       items(notes,key={it.id}){note->
         NoteRow(note)
       }
     }
     ```

  3. `derivedStateOf`分派状态管理，可只关注指定条件的状态变化，避免频繁冗余的触发重绘。

  4. 使用`lambda`调用提升后的状态数据，在最小最必要的地方使用触发变更。

  5. 避免在`Composable`
     读取状态后直接修改状态变更，如此可能会触发频繁循环绘制。应通过事件响应和`lambda`触发状态变化。

- `Navigation`适配`compose`的版本

  ```kotlin
  val navController = rememberNavController()//navController是核心
  ```

  如果使用`fragment+navigatin`就不用使用`navHost`的组合函数了。

  1. **NavHost**与**NavController**结合使用，配合`NavGraph`路线图，`Route`是唯一的`string`的标记路线的。

     ```kotlin
     NavHost(navController,startDestination="homeScreen"){
       composable("homeScreen"){}//类似于jetpack的navigation中的fragment配合navhost
     }
     navController.navigate("routePath")//导航跳转
     ```

  2. `NavArgument`封装了传参

     ```kotlin
     NavHost(startdestination = "homeScreen/{useId}"){
       //定义参数，navArgument接收参数字段，并type指定类型，可以设置defaultValue默认值
       composable("homeScreen/{useId}",arguments = listOf(navArgument("useId"){type=NavType.StringType}))
     }
     //传参
     navController.navigate("homeScreen/use889")
     //从NavBackStackEntry中获取参数
     backStackEntry.arguments?.getString("useId")
     ```

  3. `savedStateHandle`可用于存储复杂数据，类似`Intent`传参,`NavController`也避免传递复杂数据对象。

  4. 支持`deepLink`,可嵌套`navGraph`

[^1]:(htttps://developer.android.google.cn/jetpack/compose/mental-model?hl=zh-cn)

