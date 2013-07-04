package com.github.ebnew.ki4so.web.action;

import org.springframework.web.servlet.ModelAndView;

import com.github.ebnew.ki4so.core.message.MessageUtils;
import com.github.ebnew.ki4so.core.service.LoginResult;

/**
 * 默认的实现类。
 * @author burgess yang
 *
 */
public class DefaultLoginResultToView implements LoginResultToView {

	@Override
	public ModelAndView loginResultToView(ModelAndView mv, LoginResult result) {
		//若登录成功，则返回成功页面。
		if(result.isSuccess()){
			mv.getModel().put("authentication", result.getAuthentication());
			mv.setViewName("loginSucess");
		}
		else{
			mv.getModel().put("code", result.getCode());
			mv.getModel().put("msg", MessageUtils.getMessage(result.getMsgKey()));
		}
		return mv;
	}

}
