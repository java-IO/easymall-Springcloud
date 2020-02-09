package cn.tedu;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
/*        //判断当前请求中，是否存在一个logined参数
        //存在则登录了，不会进入run执行拦截，false
        //不存在则没登录，进入run执行拦截，true
        //1从zuul的工程环境中获取request对象
        // RequestContext 请求链上下文对象 获取请求和响应对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //2 拿到参数 logined
        String logined = request.getParameter("logined");
        return logined==null||"".equals(logined);//只要是null或者是空字符串
        //该方法中都判断是需要拦截run的执行，*/
return false;
    }

    @Override
    public Object run() {
/*        //System.out.println("执行了当前过滤器MyFilter的过滤逻辑");
        //进入run说明should已经判断过参数是否存在，进入就拦截
        //获取response对象，添加statusCode状态码 403(未授权)
        //获取response 阻止向后调用微服务
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        response.setStatus(403);//未授权
        //通过上限文对象，阻止响应转发
        currentContext.setSendZuulResponse(true);//SendZuulResponse
        //值是true正常调用后端微服务，false则不调用
        return null;*/
return null;
    }
}
