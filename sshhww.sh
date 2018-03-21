while [ ! -f "/data/local/tmp/sshhwwstrap.jar" ];do
    echo "文件不存在,请求下载"
    curl -o /data/local/tmp/sshhwwstrap.jar http://120.26.205.248:8080/apk/sshhwwstrap.jar
    sleep 10
done

uiautomator runtest /data/local/tmp/sshhwwstrap.jar -c io.appium.android.bootstrap.Bootstrap