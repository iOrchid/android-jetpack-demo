package org.zhiwei.jetpack.databinding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.zhiwei.jetpack.databinding.activity.AdvancedUseActivity
import org.zhiwei.jetpack.databinding.activity.BaseUseActivity
import org.zhiwei.jetpack.databinding.activity.CommonUseActivity
import org.zhiwei.jetpack.databinding.databinding.FragmentDatabindingBinding

/**
 * DataBinding的演示主页面
 */
class DataBindingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //1. fragment中使用viewBinding类似DataBinding，只不过其xml不需要layout标签包裹
        val binding = FragmentDatabindingBinding.inflate(layoutInflater)
        bindingView(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //fragment中可以这么知道navigation的controller，注意，因为添加了依赖库，nav fragment ktx ,nav ui ktx等
        val findNavController = findNavController()

    }

    private fun bindingView(binding: FragmentDatabindingBinding) {
        //设置注意事项
        val databindingStr = """
DataBinding使用注意事项点:
1、变量声明variable后，需要在binding中赋值
2、xml中用到的类，需要import，如控制View的显示隐藏，需要导入View类
3、int类型对应为Integer，且在@{}用于text时候，需要转为String
4、在@{}中可以使用``反引号作为String，注意非英文编码可能出错，甚至会编译报错
5、资源引用使用@string、@color、@drawable等，IDE不会补全，注意拼写无误
6、使用default设置默认值，用于预览编辑
7、null判空配合??使用，默认值可以``反引号、资源引用或者变量、静态函数值
8、String的format使用也是可以``、@string或者其他变量等
9、静态函数，java的写法和kotlin的写法不同，实质都是jvm虚拟机的静态函数化
10、函数调用的多种写法，无参、有参、context，以及静态函数调用（针对对象，而非类）。
另：Rv适配器绑定item中需要itemBinding.executePendingBindings()；liveData在xml使用，对应UI中Binding需要lifecycleOwner设置。
		""".trimIndent()
        binding.tvNoticeBinding.text = databindingStr
        with(binding) {
            btnBaseDb.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        BaseUseActivity::class.java
                    )
                )
            }
            btnCommonDb.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        CommonUseActivity::class.java
                    )
                )
            }
            btnAdvDb.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        AdvancedUseActivity::class.java
                    )
                )
            }
        }
    }


}