# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Program Files (x86)\Android_SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5

-dontusemixedcaseclassnames

-dontskipnonpubliclibraryclasses

-dontpreverify



-verbose

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*



-keep public class * extends android.app.Activity

-keep public class * extends android.app.Application

-keep public class * extends android.app.Service

-keep public class * extends android.content.BroadcastReceiver

-keep public class * extends android.content.ContentProvider

-keep public class * extends android.app.backup.BackupAgentHelper

-keep public class * extends android.preference.Preference

-keep public class com.android.vending.licensing.ILicensingService



-keep public class * extends android.view.View {

    public <init>(android.content.Context);

    public <init>(android.content.Context, android.util.AttributeSet);

    public <init>(android.content.Context, android.util.AttributeSet, int);

    public void set*(...);

    public void get*(...);

}



-keepclasseswithmembers class * {

    public <init>(android.content.Context, android.util.AttributeSet);

}



-keepclasseswithmembers class * {

    public <init>(android.content.Context, android.util.AttributeSet, int);

}



-keepclassmembers class * implements android.os.Parcelable {

    static android.os.Parcelable$Creator CREATOR;

}



-keepclassmembers class **.R$* {

    public static <fields>;

}



-keepclasseswithmembernames class * {

    native <methods>;

}



#-keepnames class * implements java.io.Serializable



-keep public class * implements java.io.Serializable {

        public *;

}



-keepclassmembers class * implements java.io.Serializable {

    static final long serialVersionUID;

    private static final java.io.ObjectStreamField[] serialPersistentFields;

    private void writeObject(java.io.ObjectOutputStream);

    private void readObject(java.io.ObjectInputStream);

    java.lang.Object writeReplace();

    java.lang.Object readResolve();

}



-dontwarn android.support.**

-dontwarn com.alibaba.fastjson.**

-keep class com.alibaba.fastjson.** { *; }


-dontwarn cn.smssdk.**
-dontwarn me.nereo.multi_image_selector.**
-dontwarn com.example.listview.**
-dontwarn com.squareup.okhttp.**
-dontwarn javax.annotation.**
-dontwarn com.alipay.android.app.**
-dontwarn com.alipay.mobile.**
-dontwarn com.alipay.android.phone.**
-dontwarn com.taobao.dp.**

-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses


-keepclassmembers class * {
public <methods>;
}

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.mobile.**{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

-keep class cn.smssdk.** {  *;}
-keep class me.nereo.multi_image_selector.** {  *;}
-keep class com.example.listview.** {  *;}


-keep class com.smapley.choujiang.Activity.Login {  *;}

# com.facebook.android.pro: Facebook SDK.
-keepattributes Signature,*Annotation*,EnclosingMethod
-keep class com.facebook.android.**{*;}

-keep class javax.annotation.**{*;}
-keep class com.squareup.okhttp.**{*;}