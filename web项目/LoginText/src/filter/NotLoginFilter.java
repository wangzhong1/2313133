package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Cookie;

import vo.User;
import Dao.UserDao;

public class NotLoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest res, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html;charset=UTF-8");
		UserDao userDao = new UserDao();
		HttpServletRequest request = (HttpServletRequest) res;
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			String userName = null;
			String password = null;
			for(Cookie cookie : cookies){
				if("userName".equals(cookie.getName())){
					userName = cookie.getValue();
				}else if ("password".equals(cookie.getName())){
					password = cookie.getValue();
				}
			}
			if(null != userName && null != password){
				User user = userDao.login(userName);
				if(null != user){
					// 若是用户名正确则比较密码是否验证成功
					if(password.equals(user.getPassword())){
						// 密码正确
						request.setAttribute("user", user);
						session.setAttribute("loginUser", user);
						request.getRequestDispatcher("/main.jsp").forward(request, resp); 
					}else{
						chain.doFilter(request, resp);			
					}
				}
			}else{
				chain.doFilter(request, resp);			
			}
		}else{
			chain.doFilter(request, resp);			
		}
	}
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
