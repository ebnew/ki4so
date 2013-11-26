ki4so服务器配置详细说明
=====

# 基本场景介绍 #

当您决定使用ki4so作为贵公司的sso服务之后，那么必然需要与自己的用户信息系统进行集成，因为ki4so本身并没有包含用户信息管理系统，因为ki4so要保持自己小巧的身段，以后也不会将用户管理包含在ki4so里面。那么本文主要是介绍如何与自身的用户信息管理系统如何进行集成。使用自己开发的用户信息管理系统实现用户登录。


# 实现认证处理器 #

需要集成企业内部的用户信息管理系统，需要实现自己的认证管理器类，最重要的一个方法就是识别用户名和密码是否匹配。

对于一般的场景，即通过用户名和密码登录认证的场景，我们可以参考ki4so-core中默认的一个测试用的认证处理器实现类的方法实现自己的认证处理器类，该类全名是：com.github.ebnew.ki4so.core.authentication.handlers.SimpleTestUsernamePasswordAuthenticationHandler,它位于项目ki4so-core中。它的代码很简单，如下所示。

    public final class SimpleTestUsernamePasswordAuthenticationHandler extends
		AbstractUsernamePasswordAuthenticationHandler {

	public SimpleTestUsernamePasswordAuthenticationHandler() {

	}

	@Override
	public boolean authenticateUsernamePasswordInternal(
			final UsernamePasswordCredential credential) {
		final String username = credential.getUsername();
		final String password = credential.getPassword();

		if (StringUtils.hasText(username) && StringUtils.hasText(password)
				&& username.equals(getPasswordEncoder().encode(password))) {
			return true;
		}
		throw UsernameOrPasswordInvalidException.INSTANCE;
	}
}


你自己的认证处理器也可以继承自类AbstractUsernamePasswordAuthenticationHandler，然后实现你自身的认证逻辑，从数据库中查询用户名对应的密码，或者通过远程方法调用查询对应的密码，密码一般会通过加密算法进行加密，则你需要实现合适的加密算法。

如果上述结构不符合您的具体使用场景，则可以通过实现更高参差的接口来实现认证处理器，可以从AbstractUsernamePasswordAuthenticationHandler网上追溯更高层次的接口来实现你的处理器，层次越高越抽象，灵活性也越高，应该能够绝大多数使用场景的。

# 实现密码加密器 #
出于安全考虑，对于密码我们一般都会通过加密算法加密之后进行存储，那么在比较密码的时候，我们一般要先进行加密之后再进行比较，那么ki4so默认的实现是使用明文算法进行比较，因此你需要实现自己的加密器以符合自身的需求。


如果是MD5或者SHA1加密的话则在ki4so-core中有一个默认的实现类，即类：com.github.ebnew.ki4so.core.authentication.handlers.DefaultPasswordEncoder,如果该类能够满足您的需求，则直接配置使用该类即可。

如果不能满足您的需求，则可以参考该类实现接口com.github.ebnew.ki4so.core.authentication.handlers.PasswordEncoder即可，该接口只有一个方法需要实现，非常简单，只需要实现该方法即可。

        /**
     * Method that actually performs the transformation of the plaintext
     * password into the encrypted password.
     * 
     * @param password the password to translate
     * @return the transformed version of the password
     */
    String encode(String password);

该方法有详细的注释说明，值需要将密码进行加密换算即可，输出是加密后的密文。


# 配置认证处理器 #

对于了解j2EE开发的同学来说，应该对于Spring不会陌生的，同样为了提高ki4so的代码灵活性和通用性，ki4so也使用了Spring的IOC来配置bean，对于自己实现的认证处理器可以通过Spring配置文件注入到ki4so中。

打开配置文件ki4so-web\src\main\resources\spring\spring-beans.xml。 找到配置beanauthenticationManager的那行信息，修改该bean中属性的配置信息，请参考如下示例进行设置。


    <!-- 认证管理器对象。 -->
	<bean id="authenticationManager" class="com.github.ebnew.ki4so.core.authentication.AuthenticationManagerImpl">
		<property name="authenticationHandlers">
			<list>
				<bean class="com.github.ebnew.ki4so.core.authentication.handlers.jdbc.QueryDatabaseAuthenticationHandler">
					<property name="dataSource" ref="dataSource"></property>
					<property name="sql">
						<value>select PASSWORD from zt_user where account=?</value>
					</property>
					<property name="passwordEncoder" ref="defaultPasswordEncoder"></property>
				</bean>
				<bean class="com.github.ebnew.ki4so.core.authentication.handlers.EncryCredentialAuthenticationHandler" autowire="byName"/>
			</list>
		</property>
		
		<property name="credentialToPrincipalResolvers">
			<list>
				<bean class="com.github.ebnew.ki4so.core.authentication.resolvers.UsernamePasswordCredentialToPrincipalResolver"></bean>
				<bean class="com.github.ebnew.ki4so.core.authentication.resolvers.EncryCredentialToPrincipalResolver" autowire="byName"></bean>
			</list>
		</property>
		
		<property name="authenticationPostHandler">
			<bean class="com.github.ebnew.ki4so.core.authentication.DefaultAuthenticationPostHandler" autowire="byName"></bean>
		</property>
	</bean>
	
	<!-- 密码加密器 -->
	<bean class="com.github.ebnew.ki4so.core.authentication.handlers.DefaultPasswordEncoder" id="defaultPasswordEncoder">
		<constructor-arg index="0" value="MD5"></constructor-arg>
		<property name="characterEncoding" value="UTF-8"></property>
	</bean>
	
	<!-- 数据源配置 -->
	<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close"> 
	      <!-- 基本属性 url、user、password -->
	      <property name="url" value="${jdbc_url}" />
	      <property name="username" value="${jdbc_user}" />
	      <property name="password" value="${jdbc_password}" />
	
	      <!-- 配置初始化大小、最小、最大 -->
	      <property name="initialSize" value="1" />
	      <property name="minIdle" value="1" /> 
	      <property name="maxActive" value="20" />
	
	      <!-- 配置获取连接等待超时的时间 -->
	      <property name="maxWait" value="60000" />
  	</bean>


###配置详细说明
1.authenticationManager对象配置。该对象是认证管理器，有一个属性authenticationHandlers是认证处理器列表，是一个List类型的集合。在该属性中添加自己定义的认证处理器，请去除类为com.github.ebnew.ki4so.core.authentication.handlers.SimpleTestUsernamePasswordAuthenticationHandler的认证处理器对象配置。

2.defaultPasswordEncoder是密码编码器。用于对密码进行编码。这里配置的是默认的MD5算法的编码器，可以配置为其它算法或者自己定义的密码编码器实现类。

3.dataSource。数据库数据源对象。示例中使用了阿里巴巴开源的数据库连接池druid（见官网 [https://github.com/alibaba/druid](https://github.com/alibaba/druid "druid")）。具体配置方法也可以参考druid，你可以设置为自己的数据库连接池。



# 配置客户端列表 #
在ki4so服务端需要统一配置一下单点登录客户端应用列表，包括应用的标识，秘钥等信息。具体配置方法如下。

##配置spring-beans.xml
配置ki4so-web项目中的spring配置文件，目录是/ki4so-web/src/main/resources/spring/spring-beans.xml。
找到appService和keyService的两个bean的配置位置，配置为如下所示的配置信息。

    <bean id="appService" class="com.github.ebnew.ki4so.core.app.AppServiceImpl">
		<property name="externalData">
			<value>C:\\apps.js</value>
		</property>
	</bean>
	
	<bean id="keyService" class="com.github.ebnew.ki4so.core.key.KeyServiceImpl">
		<property name="externalData">
			<value>C:\\keys.js</value>
		</property>
	</bean>

上述配置信息中的属性externalData表示外部配置文件的路径。
appService是表示客户端应用信息的配置，externalData是表示客户端应用的配置信息的文件路径。具体文件的配置格式如下。

    [
	 {appId:"1", appName:"ki4so", ki4soServer:true},
	 {appId:"1001", appName:"ki4so-app", host:"http://localhost:8080/ki4so-app", logoutUrl:"http://localhost:8080/ki4so-app/logout.do"}
	]

其中上述配置格式的含义是，每一个数据项表示一个客户端应用信息，其中appId为1的是ki4so服务器的应用信息。各字段的含义如下：

- appId，表示应用标识。必填。是一个字符串，长度不超过32个字符，应该以字符和数字组成。如：1001
- appName，应用名称，必填。应用的名称，可以是中文或者英文字符。如：ki4so
- host,表示客户端应用的URL地址，是客户端应用的前缀地址，比如：http://localhost:8080/ki4so-app，可选。若是ki4so服务器则不需要填写。
- logoutUrl,表示客户端应用的登出地址，可选。若是ki4so服务器则不需要填写。


keyService是表示客户端秘钥信息的配置，externalData是表示客户端秘钥信息的配置信息的文件路径。具体文件的配置格式如下。

    [
 	{keyId:"1", appId:"1", value:"d#@$%Dfdsadadf"},
 	{keyId:"2", appId:"1001", value:"a4323##@0D#@"}
	]

其中上述配置格式的含义是，每一个数据项表示一个客户端应用秘钥的配置信息，各字段的含义如下：

- appId，表示应用标识。必填。是一个字符串，长度不超过32个字符，应该以字符和数字组成。如：1001
- keyId，秘钥标识，必填。是一个字符串，长度不超过32个字符，应该以字符和数字组成。如：2
- value,秘钥值，必填，英文字符加字母组成，32位以内的字符。如：a4323##@0D#@

