# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-dontskipnonpubliclibraryclassmembers
-dontoptimize
#-optimizationpasses 5 # 指定代码的压缩级别
#-dontusemixedcaseclassnames  # 指定代码的压缩级别
#-dontpreverify  # 混淆时是否做预校验
#-verbose    # 混淆时是否记录日志
#-renamesourcefileattribute SourceFile
#-keepattributes SourceFile,LineNumberTable,Signature

-keepattributes *Annotation* # 假如项目中有用到注解 应用加入
-keepattributes Signature #保留泛型

-ignorewarnings                # 抑制警告



-dontwarn org.apache.**
-dontwarn android.support.**

#基础配置
# 保持哪些类不被混淆
# 系统组件
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep class * extends android.app.Dialog

#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
##js交互
#-keepattributes *JavascriptInterface*
#-keep class android.webkit.JavascriptInterface {*;}
#-keepclassmembers class com.gsl.speed.ui.web.WebActivity$WebJavaScriptFunction{
#  public *;
#}
#自定义View
-keep public class * extends android.view.View
-keep class com.speed.master.data.** { *; }
-keep class com.speed.vpnsocks.**{*;}
# V4,V7
-keep class android.support.v4.**{ *; }
-keep class android.support.v7.**{ *; }
-keep class android.webkit.**{*;}
-keep interface android.support.v4.app.** { *; }
#保持 本化方法及其类声明
-keepclasseswithmembers class * {
    native <methods>;
}
#保持view的子类成员： getter setter
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
#保持Activity的子类成员：参数为一个View类型的方法   如setContentView(View v)
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持枚举类的成员：values方法和valueOf  (每个enum 类都默认有这两个方法)
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#保持Parcelable的实现类和它的成员：类型为android.os.Parcelable$Creator 名字任意的 属性
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#保持Serializable的实现类和它的成员
-keep public class * implements java.io.Serializable {*;}
-keepclassmembers class * implements java.io.Serializable {
   static final long serialVersionUID;
   private static final java.io.ObjectStreamField[]   serialPersistentFields;
   private void writeObject(java.io.ObjectOutputStream);
   private void readObject(java.io.ObjectInputStream);
   java.lang.Object writeReplace();
   java.lang.Object readResolve();
}
#保持 任意包名.R类的类成员属性。  即保护R文件中的属性名不变
-keepclassmembers class **.R$* {
    public static <fields>;
}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}

#gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** {*;}

# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

#butternife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}