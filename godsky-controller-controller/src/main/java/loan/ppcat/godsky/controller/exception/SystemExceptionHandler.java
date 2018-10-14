package loan.ppcat.godsky.controller.exception;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SystemExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = Logger.getLogger(SystemExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.warn("System has an error = {}", ex);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackArray = ex.getStackTrace();
            for (int i = 0; i < stackArray.length; i++) {
                StackTraceElement element = stackArray[i];
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+element.toString() + "</br>");
            }
            String json = "{\"error\":\"" + ex.getMessage() + "</br>" + sb.toString() + "\"}";
            out.write(json);
            out.flush();
            out.close();
        } catch (
                IOException e)

        {
        } finally

        {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }
}
