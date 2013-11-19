
/**
 * 获得当前用户登录的应用列表。
 * @param appList 登录的应用列表，json对象。
 */
function fetchAppList(appList) {
	//登录的应用不为空。
	if (appList) {
		for ( var i = 0; i < appList.length; i++) {
			var appObj = appList[i];
			if (appObj && appObj.logoutUrl) {
				//发送请求挨个登出各应用。
				addScriptTag(appObj.logoutUrl);
			}
		}
	}
}

/**
 * 登出本应用，至少要登出本应用。
 * @param {Object} data 登录后的数据
 */
function logoutCurrentApp(){
	//本应用登出地址。
	var currentAppLogoutUrl = "${currentAppLogoutUrl}";
	//登出ki4so服务器。
	addScriptTag(currentAppLogoutUrl);
}

/**
 * 登出Ki4so服务器的处理函数。
 * @param {Object} data 登录后的数据
 */
function logoutKi4soServer(data){
	//登出ki4so服务器后的跳转地址。
	var logoutSuccessUrl = "${logoutSuccessUrl}";
	if(data.result){
		//登出后的处理。
		window.location.href = logoutSuccessUrl;
	}
}


/**
 * 动态添加某个javascript文件到文档中。
 * @param {Object} src js文件的路径。
 */
function addScriptTag(src){
	var script = document.createElement('script');
	script.setAttribute("type","text/javascript");
	script.src = src;
	document.body.appendChild(script);
}

function logout() {
	var ki4soServerHost = "${ki4soServerHost}";
	//获取登录的应用列表。
	addScriptTag(ki4soServerHost+"getAppList.do?callbackName=fetchAppList");
	
	//登出ki4so服务器。
	addScriptTag(ki4soServerHost+"logout.do?callbackName=logoutKi4soServer");
	
	//登出本应用地址。
	logoutCurrentApp();
}