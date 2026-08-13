L2FProd.com Common Components 0.2-dev
http://common.L2FProd.com

Swing has lot of components built-in but still some are missing. This
project provides the developer community with these missing
components, components inspired (copied?!) from modern user
interfaces.

------- The Components

the JButtonBar - It is a bar made of buttons [sic], you have seen it
in Mozilla Firebird, IntelliJ.

the JOutlookBar - As seen in Outlook, it stacks components together
and allows only one of the stack to be visible at a given time. The
component extends the JTabbedPane, no surprise regarding its API.

the JTaskPane and JTaskPaneGroup - Lot of recent applications bring
contextual item lists from which you can pick tasks related to the
current selection or context. The JTaskPane and JTaskPaneGroup deliver
this feature to java applications.

the JFontChooser and JDirectoryChooser - Surprisingly Swing has no
font chooser and using the original JFileChooser to select a directory
is kind of not so user friendly...well, you know how it works.
JFontChooser and JDirectoryChooser address these two issues.

the PropertySheet - it puts together a list of properties and their
editors. Each property is given a name, a type, a description.It also
supports JavaBeans through BeanInfos and PropertyDescriptors.

------- The Distribution

In the distribution, there are several jar files, one per component
plus one including all components. This allows developers to include
only the classes for the components they want to use.

To run the demo from the distribution, use:

  java -jar lib\l2fprod-common-all.jar

Each jar embeds its own demo too. For example to view the JTaskPane
demo, use:

  java -jar lib\l2fprod-common-tasks.jar

------- Contributing

If you want to contribute to the project, see
http://common.L2FProd.com for directions.

The java source code is under src/java. Each component has its own
source directory plus the "shared" directory which is used by all
components. The goal is to be able to build separate jar files for
each component. Classes in "sandbox" are work-in-progress. Before
compiling, make sure to run the target "ant init.depend" to download
libraries used by the build.

The project requires at least JDK 1.4.2.


-fred at L2FProd.com
