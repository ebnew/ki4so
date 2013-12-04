ki4so安装使用说明
=====
# 1.下载源代码

从github上下载最新版本的ki4so,从如下URL地址

[https://github.com/ebnew/ki4so/releases](https://github.com/ebnew/ki4so/releases "github版本下载地址")

下载最新发布的ki4so版本代码。


# 2.编译源代码

## 2.1 安装编译环境
ki4so是使用java编写的，工程使用maven进行管理，因此进行编译需要使用jdk1.6+以上版本，maven2.0+以上版本。

jdk安装。

参考oracle下载地址：[http://www.oracle.com/technetwork/java/javase/downloads/index.html](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

请下载jdk1.6以上版本。

maven安装。

参考maven官方网站，参考地址：[http://maven.apache.org/](http://maven.apache.org/)

## 2.2 编译源代码

1. 解压源代码

对于下载的代码使用解压工具进行解压，如下图在windows xp系统下使用winrar进行解压。

![源代码解压文件](http://github.com/ebnew/ki4so/raw/master/images/install/code_zip.jpg)

解压之后如下所示情况：

![源代码解压后文件](http://github.com/ebnew/ki4so/raw/master/images/install/code_unzip.jpg)

2. 编译源代码

使用命令行进入到源代码目录,如下图所示。

![源代码命令行](http://github.com/ebnew/ki4so/raw/master/images/install/code_cmd.jpg)

在命令行中根目录下，输入如下命令。

    mvn install

确保maven 和jdk已经安装好了。



编译成功后，在命令行看到如下画面。

![源代码编译成功](http://github.com/ebnew/ki4so/raw/master/images/install/install_sucess.jpg)


在maven工程ki4so-web的target目录下有一个打包好的ki4so工程。如下图所示就是。


![ki4so服务器打包](http://github.com/ebnew/ki4so/raw/master/images/install/packaged.jpg)

图中所示的ki4so-web-1.0.0-SNAPSHOT.war文件就是打好的ki4so的war包。


3. 安装ki4so服务器

将打好的war包直接安装到Tomcat服务器中即可。Tomcat服务器如何安装请参考Tomcat官网说明。[http://tomcat.apache.org/](http://tomcat.apache.org/)

最简单的安装方法直接将war包放入到Tomcat安装根目录的webapps目录下，修改war包名称为ki4so-web即可（修改一个更加简短的的目录，访问的时候使用该目录），如下图所示。


![ki4so服务器打包](http://github.com/ebnew/ki4so/raw/master/images/install/install_in_server.jpg)

安装好之后启动Tomcat服务器，点击Tomcat安装目录下的bin目录中的 startup.bat命令行即可。

启动好之后会出现如下命令行提示。

![ki4so服务器打包](http://github.com/ebnew/ki4so/raw/master/images/install/server_startup.jpg)

则服务器从8080端口启动，启动成功之后，直接在浏览器里访问ki4so服务。

4. 访问ki4so-web服务

在浏览器中输入地址： [http://localhost:8080/ki4so](http://localhost:8080/ki4so-web)

这个地址是假设你安装的Tomcat服务器的端口是8080，ki4so的war包改名为ki4so-web之后的结果。则访问的结果如下图。

![ki4so服务器打包](http://github.com/ebnew/ki4so/raw/master/images/install/visit_ki4so.jpg)

5. 登录ki4so服务

访问ki4so通过"登录”链接进入登录页面，则显示如下页面。

![ki4so服务器打包](http://github.com/ebnew/ki4so/raw/master/images/install/login_ki4so.jpg)

在登录页面中输入相同的用户名密码，例如用户名输入：test，密码也输入:test，点击登录则可以登录成功，登录成功后显示的页面内容如下所示。


![ki4so服务器打包](http://github.com/ebnew/ki4so/raw/master/images/install/login_sucess_ki4so.jpg)

