rem mvn dependency:copy-dependencies -DoutputDirectory=libs
rem ant build
adb push bin/AppiumBootstrap.jar /data/local/tmp
adb shell uiautomator runtest AppiumBootstrap.jar -c io.appium.android.bootstrap.Bootstrap