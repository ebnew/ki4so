package com.github.ebnew.ki4so.web.action;

import org.springframework.web.servlet.ModelAndView;

import com.github.ebnew.ki4so.core.service.LoginResult;

/**
 * 默认的实现类。
 * @author burgess yang
 *
 */
public class DefaultLoginResultToView implements LoginResultToView {

	@Override
	public ModelAndView loginResultToView(ModelAndView mv, LoginResult result) {
		return mv;
	}

}
