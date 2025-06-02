# Add project specific ProGuard rules here.
# By default, the flags in this file are applied to all build types.

# ProGuard syntax is documented at http://proguard.sourceforge.net/manual/usage.html

# Add any project specific keep rules here:

# If you use reflection or JNI in your code, typically you need to instruct ProGuard not to remove actual methods or classes in your code.
# Example:
# -keep class com.example.MyClass
# -keepclassmembers class com.example.MyClass {
#   public <fields>;
#   public <methods>;
# }

# If your project uses WebView with JavaScript, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to obfuscate the original source file name.
#-renamesourcefileattribute SourceFile
