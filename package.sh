#mvn dependency:copy-dependencies -DoutputDirectory=libs
#ant clean build
adb push bin/sshhwwstrap.jar /data/local/tmp
adb shell uiautomator runtest /data/local/tmp/sshhwwstrap.jar -c io.appium.android.bootstrap.Bootstrap