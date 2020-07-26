#### DataBinding知识速览

###### 一、基础使用

> DataBinding 包含 viewBinding和dataBinding

1. 使用AndroidStudio版本目前 3.6.3或 4.0.1

2. gradle启用

   ```groovy
   databinding{
       enable=true
   }
   //或者
   buildFeature{
       dataBinding = true
       viewBinding = true//只针对viewBinding
   }
   ```

3. 对应UI的xml布局使用`<layout></layout>`即可

4. ```xml
   <layout>
   	<data>
   	</data>
   </layout>
   ```

5. 导入类型import

6. 声明变量variable

7. 关联布局

   ```kotlin
   //activity
   DataBindingUtil.setContentView
   //fragment
   XXXBinding.inflate
   xxxBinding.bind(view)
   ```

8. 在xml中`@{value}`

9. `??`判空

10. `?:`三目运算

11. ````` ```反引号的使用

12. 点击`@{()->XXXX.xxx()}`

13. observableInt、observableBoolean、observableField()

###### 二、进阶用法

- include

- adapter

- @bindadapter

  ```kotlin
  	@JvmStatic
  	@BindingAdapter(value = ["bind:imgSrc"], requireAll = false)
  	fun urlImageSrc(view: AppCompatImageView, /*old: String?, */url: String) {
  		Glide.with(view)
  			.load(url)
  			.placeholder(R.drawable.img_banner)
  			.centerInside()
  			.into(view)
  	}
  ```

- @BindingConversion

  ```kotlin
  	@JvmStatic
  	@BindingConversion
  	fun converStr2Color(str: String): Drawable {
  		return when (str) {
  			"red" -> {
  				ColorDrawable(Color.RED)
  			}
  			"blue" -> ColorDrawable(Color.BLUE)
  			else -> {
  				ColorDrawable(Color.YELLOW)
  			}
  		}
  	}
  
  ```

- two-way `@={}`