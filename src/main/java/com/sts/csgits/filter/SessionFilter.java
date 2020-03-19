package com.sts.csgits.filter;

import com.sts.csgits.inc.Const;
import com.sts.csgits.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤
 */
@Component
@Slf4j
public class SessionFilter extends OncePerRequestFilter {

	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
  
        // 请求的uri
        String uri = request.getRequestURI();
        log.info("uri:" + uri);

        if (uri.contains(Const.SESSION_FILTER_CONTAINS_URL) || uri.contains(".")){
			filterChain.doFilter(request, response);
			return;
		}

//		request.getSession().setAttribute("id", 1);
//        request.getSession().setAttribute("no", "admin");
//		request.getSession().setAttribute("realName", "gaobin");

		String no = (String) request.getSession().getAttribute("no");

		if(StringUtils.isEmpty(no)){
			response.sendRedirect("/admin/loginPage");
			return;
		}

		// 不过滤，则继续
		filterChain.doFilter(request, response);
    }
}