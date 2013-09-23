
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
 * 登出Ki4so服务器的处理函数。
 * @param {Object} data 登录后的数据
 */
function logoutKi4soServer(data){
	if(data.result){
		alert("登出ki4so server");
		//登出后的处理。
		window.location.href = "http://localhost:8080/ki4so-app/";
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
	//获取登录的应用列表。
	addScriptTag("http://localhost:8080/ki4so-web/getAppList.do?callbackName=fetchAppList");
	
	//登出ki4so服务器。
	addScriptTag("http://localhost:8080/ki4so-web/logout.do?callbackName=logoutKi4soServer");
}