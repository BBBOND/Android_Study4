## 安卓常用组件

### [ListViewLoad](https://github.com/BBBOND/Android_Study4/tree/master/ListViewLoad)
*   说明：这是一个可以显示上拉加载更多的ListView.
*   实现方法：
      1.  创建要上拉后要显示的底部
      2.  编写自定义的view类继承ListView

### [AndroidAnnotation](https://github.com/BBBOND/Android_Study4/tree/master/androidannotation)
*   说明：安卓开发懒人框架([官网文档](https://github.com/excilys/androidannotations/wiki))

### [Animator](https://github.com/BBBOND/Android_Study4/tree/master/animator)
*   说明：使用比较优秀的Animator来实现动画
*   常用类：
      1.  ObjectAnimator-&gt;view移动动作响应范围同时跟着移动，是ValueAnimatdor的子类
      2.  PropertyValuesHolder-&gt;相对于直接使用ObjectAnimator方法资源更优化
      3.  AnimatorSet-&gt;可以按指定的顺序执行动画，而不是同时执行(Animator不可重用)
      4.  ValueAnimator-&gt;可以完全自定义自己的动画

### [FloatMenu](https://github.com/BBBOND/Android_Study4/tree/master/floatmenu)
*   说明：可以根据自己的计算实现菜单展开的不同样式
*   实现方法：
      1.  通过ObjectAnimator实现动画
      2.  使用setInterpolator()方法实现动画的速度

### [FlowLayout](https://github.com/BBBOND/Android_Study4/tree/master/flowlayout)
*   说明：按子控件的宽度在父控件中进行填充
*   实现方法：
      1.  编写FlowLayout继承自ViewGroup
      2.  重写onMeasure()方法，计算子控件的宽高和Padding值，按行分组

### [MyViewPager](https://github.com/BBBOND/Android_Study4/tree/master/myviewpager)
*   说明：给ViewPager添加切换动画

### [Tab](https://github.com/BBBOND/Android_Study4/tree/master/tab)
*   说明：使用ViewPager、Fragment和FragmentPagerAdapter三种方法实现Tab切换

### [ThreadDownload](https://github.com/BBBOND/Android_Study4/tree/master/threaddownload)
*   说明：实现多线程下载文件
*   实现方法：
      1.  自定义一个文件适配器，在其中为ListView中Item设置内容
      2.  将单个文件按指定线程数分块
      3.  将线程信息记录来SQLite中
      4.  并使用BroadcastReceiver实现界面更新
      5.  使用Service实现通知栏显示进度

### [TreeView](https://github.com/BBBOND/Android_Study4/tree/master/treeview)
*   说明：使用ListView，通过将数据处理，添加标识符，使其产生不同的显示，从而达到目录的效果

### [Volley](https://github.com/BBBOND/Android_Study4/tree/master/volley)
*   说明：Volley网络框架(应付高并发)
*   主要实现：
      1.  Volley的get和post请求方式的使用
      2.  Volley的网络请求队列的创建和取消队列请求
      3.  Volley与Activity生命周期的联动
      4.  Volley的简单二次回调封装
