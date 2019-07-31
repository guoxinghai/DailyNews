# DailyNews
根据知乎日报api用Android开发一个简易版的知乎日常推送app

------

* **记录一个未解决的问题**

> 当用OKHTTP发送请求的时候报了一个错误:
>
> `System.err: java.net.SocketException: socket failed: EPERM (Operation not permitted)`

在网上搜了挺长时间有几种解决方案，都没能解决我的问题，最终换了一个`AVD`解决了，不知道原因何在。

* **记录一个小bug**

  > 用`CircleIndicator`绑定的`ViewPager`当数据更新之后圆点消失了

  **解决:**当更新了数据之后再绑定一次

  ```java
  circleIndicator.removeAllViews();
  circleIndicator.setViewPager(viewPager);
  ```

* **修改CircleIndicator圆点的颜色**

  1. 在`res/drawable`中创建`Drawable resource file`并设置颜色

     ```xml
     <?xml version="1.0" encoding="utf-8"?>
     <shape xmlns:android="http://schemas.android.com/apk/res/android"
         android:shape="oval">
         <solid
             android:color="#2196F3"/>
     </shape>
     ```

  2. 在`CircleIndicator布局中设置属性`

     ```xml
     app:ci_drawable="@drawable/yellow_radius" //设置所有圆点颜色
     app:ci_drawable_unselected="@drawable/blue_radius"/> //设置被选中圆点的颜色
     ```

     