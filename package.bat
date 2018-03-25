rem mvn dependency:copy-dependencies -DoutputDirectory=libs
rem ant build
adb push bin/sshhwwstrap.jar /data/local/tmp
adb shell uiautomator runtest sshhwwstrap.jar -c io.appium.android.bootstrap.Bootstrap