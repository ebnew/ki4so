package com.github.ebnew.ki4so.web.action;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.github.ebnew.ki4so.core.authentication.Authentication;
import com.github.ebnew.ki4so.core.authentication.AuthenticationPostHandler;
import com.github.ebnew.ki4so.core.message.MessageUtils;
import com.github.ebnew.ki4so.core.service.LoginResult;
import com.github.ebnew.ki4so.web.utils.WebConstants;

/**
 * 默认的实现类。
 * 
 * @author burgess yang
 * 
 */
public class DefaultLoginResultToView implements LoginResultToView {

	@Override
	public ModelAndView loginResultToView(ModelAndView mv, LoginResult result, HttpServletRequest request,
			HttpServletResponse response) {
		// 若登录成功，则返回成功页面。
		if(mv==null){
			mv = new ModelAndView();
		}
		if(result==null || request==null || response==null){
			return mv;
		}
		if (result.isSuccess()) {
			//登录结果对象。
			Authentication authentication = result.getAuthentication();

			//清除session中的状态信息service值。
			request.getSession().removeAttribute(WebConstants.KI4SO_SERVICE_KEY_IN_SESSION);
			
			// 如果有加密凭据信息，则写入加密凭据值到cookie中。
			if (authentication != null
					&& authentication.getAttributes() != null) {
				Map<String, Object> attributes = authentication.getAttributes();
				// ki4so服务端加密的凭据存在，则写入cookie中。
				if (attributes
						.get(AuthenticationPostHandler.KI4SO_SERVER_EC_KEY) != null) {
					response.addCookie(new Cookie(
							WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY,
							attributes
									.get(AuthenticationPostHandler.KI4SO_SERVER_EC_KEY)
									.toString()));
				}
				// ki4so客户端加密的凭据和参数service存在，则跳转到对应的页面中。
				if (attributes
						.get(AuthenticationPostHandler.KI4SO_CLIENT_EC_KEY) != null
						&& !StringUtils.isEmpty(attributes.get(WebConstants.SERVICE_PARAM_NAME))) {
					mv.getModel().put("authentication", authentication);
					mv.setView(this
							.buildRedirectView(
									attributes.get(WebConstants.SERVICE_PARAM_NAME).toString(),
									attributes
											.get(AuthenticationPostHandler.KI4SO_CLIENT_EC_KEY)
											.toString()));
					return mv;
				}
			}
			mv.getModel().put("authentication", authentication);
			mv.setViewName("loginSucess");
		} else {
			//删除以前不合法的凭据信息。
			//清除cookie值。
			Cookie[] cookies = request.getCookies();
			if(cookies!=null && cookies.length>0){
				for(Cookie cookie:cookies){
					if(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY.equals(cookie.getName())){
						//设置过期时间为立即。
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
			mv.getModel().put("code", result.getCode());
			mv.getModel().put("msg",
					MessageUtils.getMessage(result.getMsgKey()));
		}
		return mv;
	}

	/**
	 * 构造跳转的URL地址。
	 * 
	 * @return
	 */
	private RedirectView buildRedirectView(String service,
			String encryCredential) {
		StringBuffer sb = new StringBuffer(service);
		if (service.contains("?")) {
			sb.append("&");
					
		} else {
			sb.append("?");
		}
		sb.append(WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY)
		.append("=").append(encryCredential);
		return new RedirectView(sb.toString());
	}

}
