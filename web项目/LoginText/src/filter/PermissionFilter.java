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
import javax.servlet.jsp.jstl.core.Config;

public class PermissionFilter implements Filter {

	private String notCheckPath;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getServletPath();
		System.out.println("请求地址url-pattern:"+path);
		if (notCheckPath.indexOf(path)==-1){
			HttpSession session = request.getSession();
			if(session.getAttribute("currentUser")==null){
				request.setAttribute("info","没用访问权限");
				request.getRequestDispatcher("/error.jsp").forward(request,resp);
			}else{
				chain.doFilter(req,resp);
			}
		}else{
			chain.doFilter(req,resp);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
      notCheckPath = config.getInitParameter("notCheckPath");
	}

}
