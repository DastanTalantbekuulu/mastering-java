del *.class
javac *.java
jar --create --file RPN_MVC_Pattern.jar --main-class Main *.class
del *.class