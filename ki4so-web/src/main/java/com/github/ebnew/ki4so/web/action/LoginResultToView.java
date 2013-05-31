package com.github.ebnew.ki4so.web.action;

import org.springframework.web.servlet.ModelAndView;

import com.github.ebnew.ki4so.core.service.LoginResult;

/**
 * 该接口实现了将登录结果转换为视图响应的功能。
 * @author Administrator
 *
 */
public interface LoginResultToView {
	
	/**
	 * 将登录结果对象相应到模型和视图中。
	 * @param mv 模型视图对象。
	 * @param result 登录结果信息。
	 * @return 更新后的模型视图对象。
	 */
	public ModelAndView loginResultToView(ModelAndView mv, LoginResult result);

}
