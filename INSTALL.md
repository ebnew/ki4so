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


