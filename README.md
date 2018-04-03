# DragScrollView
一个可以下拉拖拽的ScrollView控件

 ![img](https://github.com/liuleshuai/DragScrollView/raw/dev1/screenshots/GIF.gif)

## Gradle
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
 
dependencies {
	compile 'com.github.liuleshuai:DragScrollView:1.1'
}
```

## 布局中使用

 ```
     <com.liuleshuai.draglibrary.OuterLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.liuleshuai.draglibrary.InnerScrollView
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            
            ... ...
            
        </com.liuleshuai.draglibrary.InnerScrollView>
    </com.liuleshuai.draglibrary.OuterLayout>
 ```
###注意 OuterLayout内只能有一个子控件，即InnerScrollView。

## 代码中使用

```
   @BindView(R.id.layout)
   OuterLayout outerLayout;
   
   outerLayout.setDragListener(new DragListener() {
            @Override
            public void close() {
                ... ...
            }
        });
```

## 属性设置

```
   <declare-styleable name="OuterLayout">
        <!--下拉触发close的比例-->
        <attr name="dray_radio" format="float" />
        <!--下拉边框缩小的比例-->
        <attr name="dray_down_dimen" format="float" />
    </declare-styleable>
```
