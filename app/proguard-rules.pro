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
# RxHttp
-keep class rxhttp.**{*;}
# OkHttp
-keep class okhttp3.**{*;}
-keep class okio.**{*;}
#ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
-keep public class com.gengcon.www.jcprintersdk.**{*;}
-keep public class com.jingchen.jcimagesdk.**{*;}
-keep public class com.dothantech.**{*;}
-keep public class zpSDK.zpSDK.**{*;}
-keep public class com.snbc.sdk.**{*;}
-keep public class android_serialport_api.**{*;}
-dontwarn com.gengcon.www.jcprintersdk.**
-dontwarn com.jingchen.jcimagesdk.**
-dontwarn com.dothantech.**
-dontwarn zpSDK.zpSDK.**
-dontwarn com.snbc.sdk.**
-dontwarn android_serialport_api.**
