# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/damien/Documents/android-sdk-macosx/tools/proguard/proguard-android.txt
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

#Error:Execution failed for task ':onelittleangel:processProdxxxhdpinormalArmeabiDebugManifest'.
#> Manifest merger failed : uses-sdk:minSdkVersion 11 cannot be smaller than version 14 declared in library [com.google.firebase:firebase-core:11.4.2] /Users/damien/Documents/OnelittleAngel/fr/onelittleangel/onelittleangel-fr-android-java/onelittleangel/build/intermediates/exploded-aar/com.google.firebase/firebase-core/11.4.2/AndroidManifest.xml
#  	Suggestion: use tools:overrideLibrary="com.google.firebase.firebase_core" to force usage


#-keepattributes InnerClasses
#-dontnote android.net.http.*
#-dontnote org.apache.commons.codec.**
#-dontnote org.apache.http.**

# Add this global rule
#-keepattributes Signature

# Proguard Configuration for Realm (http://realm.io)
# For detailed discussion see: https://groups.google.com/forum/#!topic/realm-java/umqKCc50JGU
# Additionally you need to keep your Realm Model classes as well
# For example:
#-keep class com.sc.onelittleangel.realm.objects.** { *; }

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# will keep line numbers and file name obfuscation
#-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *
-dontwarn javax.**
-dontwarn io.realm.**
-dontwarn okio.**

#-keep class com.google.**
-dontwarn com.google.**
-dontwarn sun.misc.**
-dontwarn java.lang.invoke.*

#-keepclassmembers class * extends
#    de.greenrobot.event.util.ThrowableFailureEvent {*;}


-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keep class android.support.v4.view.** { *; }

-ignorewarnings

-keep class net.htmlparser.jericho.** { *; }
-keep class org.apache.logging.log4j.** { *; }
#-keep class android.support.v7.widget.** { *; }
#-keepclassmembers class android.support.v4.view.** { *; }

# rxjava
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}
