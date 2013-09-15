function logout() {
	// 获取参数。
	jQuery.ajax({
		url : "http://localhost:8080/ki4so-web/getAppList.do",
		success : function(data) {
			// 数据不为空。
			if (data) {
				for ( var i = 0; i < data.length; i++) {
					var appObj = data[i];
					if (appObj && appObj.logoutUrl) {
						// 发送请求挨个登出各应用,发送异步请求。
						jQuery.ajax({
							url : appObj.logoutUrl,
							success : function(data) {
								alert("登出地址" + appObj.logoutUrl);
							}
						});
					}
				}
			}
		}
	});
	// 最后再登出ki4so服务器。
	jQuery.ajax({
		url : "http://localhost:8080/ki4so-web/logout.do",
		success : function(data) {
			alert("登出ki4so server");
		}
	});

	alert("登出成功");
	window.location.href = "http://localhost:8080/ki4so-web";
}