rm -r out

rm -r Stylepad.jar

mkdir out

xcopy viewer\resources out\viewer\resources /E /I /Y
xcopy viewer\images out\viewer\images /E /I /Y
xcopy server\resources out\server\resources /E /I /Y

javac  -Xlint  -d ./out -sourcepath . Main.java  -verbose

cd out

jar --create --file ../Stylepad.jar --main-class Main .

pause