ki4so客户端配置详细说明
=====

在ki4so中已经默认实现了一个java web应用的客户端开发包，即maven模块ki4so-java-client，开发者可以基于该模块进行配置，集成到自己的web应用中，即可与ki4so进行集成。

集成ki4so-java-client模块也是比较简单的，具体的配置详细说明请参考如下。

#1.安装ki4so-java-client
首先第一步是将ki4so-java-client模块集成到自己的web工程中。分为以下两种情况。
##普通java web工程。

若非maven工程，则需要手动将ki4so-java-client模块的jar包添加到工程的依赖中去。先讲ki4so-java-client打成jar包，同时将jar包的依赖添加到工程的classpath中去。

##maven工程

对于maven工程来说，则只需要通过添加ki4so-java-client模块的maven依赖即可，配置如下。

    <dependency>
			<groupId>com.github.ki4so</groupId>
			<artifactId>ki4so-java-client</artifactId>
			<version>${ki4so.version}</version>
	</dependency>

其中在上述的参数${ki4so.version}是表示ki4so使用的版本号，可以根据使用的版本号进行调整。

#2.配置ki4so-java-client

安装完毕ki4so-java-client包之后，接下来需要进行简单的配置即可使用ki4so实现单点登录。

##配置web.xml

对于java web项目，遵守sevlet规范的话，都应该有一个web.xml文件，web工程描述文件，在该文件中要进行相应的配置。

###配置过滤器Ki4soClientFilter
该过滤器负责拦截所有的访问资源，对于需要受保护的资源则需要经过该过滤器过滤，该过滤器实现了统一登录。

首先需要在web.xml中配置一个过滤器，该过滤器的类名是com.github.ebnew.ki4so.client.web.filters.Ki4soClientFilter，是在模块ki4so-java-client实现的一个过滤器。
在配置该过滤器的时候有一些参数需要进行配置，这些参数的详细说明如下。

- ki4soServerHost。该参数表示是ki4so服务器主机地址，必须。示例：http://localhost:8080/ki4so-web/
- ki4soServerLoginUrl。ki4so服务器登录地址，必填。示例：http://localhost:8080/ki4so-web/login.do 这个地址是ki4so的登录地址。
- ki4soServerFetchKeyUrl。ki4so服务器获取应用秘钥信息的URL地址，必填。示例：http://localhost:8080/ki4so-web/fetchKey.do
- ki4soClientAppId。本应用在ki4so服务器上的应用ID值。该值需要在ki4so服务器上配置之后确定该值。如：1001
- appClientLoginHandlerClass。登录本应用处理器类，该类需要在本web应用中实现一个接口，将实现类配置在该地址中。例如：com.github.ebnew.ki4so.app.custom.Ki4soAppClientLoginHandlerImpl


配置信息参考一个完整的示例，见ki4so工程中的配置文件 /ki4so-app/src/main/webapp/WEB-INF/web.xml
该文件中的配置内容如下。

    <!-- ki4so集成的过滤器，必须集成该过滤器，放在最前面的位置。 -->
	<filter>
		<filter-name>ki4soClientFilter</filter-name>
		<filter-class>com.github.ebnew.ki4so.client.web.filters.Ki4soClientFilter</filter-class>
		<init-param>
			<description>ki4so服务器主机地址</description>
			<param-name>ki4soServerHost</param-name>
			<param-value>http://localhost:8080/ki4so-web/</param-value>
		</init-param>
		
		<init-param>
			<description>ki4so服务器登录地址</description>
			<param-name>ki4soServerLoginUrl</param-name>
			<param-value>http://localhost:8080/ki4so-web/login.do</param-value>
		</init-param>
		
		<init-param>
			<description>ki4so服务器获取应用秘钥信息的URL地址</description>
			<param-name>ki4soServerFetchKeyUrl</param-name>
			<param-value>http://localhost:8080/ki4so-web/fetchKey.do</param-value>
		</init-param>
		
		<init-param>
			<description>本应用在ki4so服务器上的应用ID值</description>
			<param-name>ki4soClientAppId</param-name>
			<param-value>1001</param-value>
		</init-param>
		
		<init-param>
			<description>登录本应用处理器类</description>
			<param-name>appClientLoginHandlerClass</param-name>
			<param-value>com.github.ebnew.ki4so.app.custom.Ki4soAppClientLoginHandlerImpl</param-value>
		</init-param>
	</filter>
	
	<filter-mapping>
		<filter-name>ki4soClientFilter</filter-name>
		<url-pattern>*.do</url-pattern>
	</filter-mapping>


###配置过滤器ki4soClientLogoutFilter

为了实现统一登出，需要配置该过滤器ki4soClientLogoutFilter。该过滤器类在模块ki4so-java-client实现，类名是com.github.ebnew.ki4so.client.web.filters.Ki4soClientLogoutFilter。

该过滤器需要进行一些简单的配置，需要配置的参数是：
- appClientLogoutHandler。登出本应用处理器类名，对于处理本应用的登出请求，则需要将本地的登录信息清楚，实现本应用的登录相关操作，需要实现一个接口，该实现类在这里配置，如：com.github.ebnew.ki4so.app.custom.Ki4soAppClientLogoutHandlerImpl

配置信息参考一个完整的示例，见ki4so工程中的配置文件 /ki4so-app/src/main/webapp/WEB-INF/web.xml
该文件中的配置内容如下。

    <!-- ki4so处理本地应用登出逻辑的过滤器。 -->
	<filter>
		<filter-name>ki4soClientLogoutFilter</filter-name>
		<filter-class>com.github.ebnew.ki4so.client.web.filters.Ki4soClientLogoutFilter</filter-class>
		
		<init-param>
			<description>登出本应用处理器类</description>
			<param-name>appClientLogoutHandler</param-name>
			<param-value>com.github.ebnew.ki4so.app.custom.Ki4soAppClientLogoutHandlerImpl</param-value>
		</init-param>
	</filter>
	
	<filter-mapping>
		<filter-name>ki4soClientLogoutFilter</filter-name>
		<url-pattern>/logout.do</url-pattern>
	</filter-mapping>

###配置过滤器ki4soLogoutJavascriptFilter

ki4so是在客户端实现所有应用退出的，这样实现方式减轻了ki4so服务器的压力，由客户端对已经登录的所有应用逐一退出。因此ki4so-java-client模块提供了一个js文件，该文件中的函数能够实现统一登录。因此需要配置该过滤器。

这个过滤器有如下参数需要配置。
- ki4soServerHost。该参数表示是ki4so服务器主机地址，必须。示例：http://localhost:8080/ki4so-web/
- currentAppLogoutUrl。该参数表示当前这个应用过的登出地址，比如：http://localhost:8080/ki4so-app/logout.do
- logoutSuccessUrl。该参数表示当前应用登出后跳转的地址，必填。示例：http://localhost:8080/ki4so-app

配置信息参考一个完整的示例，见ki4so工程中的配置文件 /ki4so-app/src/main/webapp/WEB-INF/web.xml
该文件中的配置内容如下。

    	<!-- ki4so处理登出的javascript文件请求的过滤器。 -->
	<filter>
		<filter-name>ki4soLogoutJavascriptFilter</filter-name>
		<filter-class>com.github.ebnew.ki4so.client.web.filters.Ki4soLogoutJavascriptFilter</filter-class>
		<init-param>
			<description>ki4so服务器主机地址</description>
			<param-name>ki4soServerHost</param-name>
			<param-value>http://localhost:8080/ki4so-web/</param-value>
		</init-param>
		
		<init-param>
			<description>当前应用的登出地址</description>
			<param-name>currentAppLogoutUrl</param-name>
			<param-value>http://localhost:8080/ki4so-app/logout.do</param-value>
		</init-param>
		
		<init-param>
			<description>登出后跳转地址</description>
			<param-name>logoutSuccessUrl</param-name>
			<param-value>http://localhost:8080/ki4so-app</param-value>
		</init-param>
	</filter>
	
	<filter-mapping>
		<filter-name>ki4soLogoutJavascriptFilter</filter-name>
		<url-pattern>/logout.js</url-pattern>
	</filter-mapping>



