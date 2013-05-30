package com.github.ebnew.ki4so.web.action;

import org.springframework.web.servlet.ModelAndView;

import com.github.ebnew.ki4so.core.authentication.Authentication;

/**
 * 该接口实现了将验证结果数据转换为视图响应。
 * @author Administrator
 *
 */
public interface AuthenticationToView {
	
	public ModelAndView authenticationToView(ModelAndView mv, Authentication authentication);

}
