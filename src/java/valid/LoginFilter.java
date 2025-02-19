/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package valid;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Acer
 */
public class LoginFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public LoginFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoginFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoginFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Lấy session hiện tại, nếu có
        HttpSession session = req.getSession(false);
        String uri = req.getServletPath();
        if (uri.endsWith(".jsp")
                && !uri.endsWith("admin.jsp")
                && !uri.endsWith("menu.jsp")
                && !uri.endsWith("order.jsp")
                && !uri.endsWith("listStaff.jsp")
                && !uri.endsWith("cashier")
                && !uri.endsWith("cashier.jsp")
                && !uri.endsWith("addAccountandStaff.jsp")
                && !uri.endsWith("createDish.jsp")
                && !uri.endsWith("createTable.jsp")
                && !uri.endsWith("cashierManage.jsp")
                && !uri.endsWith("updateDish.jsp")
                && !uri.endsWith("updateTable.jsp")
                && !uri.endsWith("updateAccountandStaff.jsp")
                && !uri.endsWith("tableID.jsp")
                && !uri.endsWith("header.jsp")
                && !uri.endsWith("footer.jsp")
                && !uri.endsWith("bill.jsp")
                && !uri.endsWith("continue.jsp")
                && !uri.endsWith("listDish.jsp")
                && !uri.endsWith("listTable.jsp")) {
             // Code xử lý khi đường dẫn không hợp lệ
                res.sendRedirect("index.html");
        }
        
        
        boolean isLoggedIn = (session != null && session.getAttribute("username") != null);
        String requestURI = req.getRequestURI();

        // Đường dẫn đến trang đăng nhập
        String loginURI = req.getContextPath() + "/index.html"; // URL của trang đăng nhập

        // Kiểm tra yêu cầu đến các trang bảo vệ
        if (isLoggedIn || requestURI.equals(loginURI)) {
            // Nếu đã đăng nhập hoặc đang truy cập trang đăng nhập, cho phép tiếp tục request
            chain.doFilter(request, response);
        } else {
            // Kiểm tra nếu yêu cầu đến các trang cần bảo vệ
            if (requestURI.endsWith("admin.jsp") || requestURI.endsWith("menu.jsp")
                    || requestURI.endsWith("order.jsp") || requestURI.endsWith("listStaff.jsp")
                    || requestURI.endsWith("cashier.jsp")
                    || requestURI.endsWith("addAccountandStaff.jsp") || requestURI.endsWith("createDish.jsp")
                    || requestURI.endsWith("createTable.jsp") || requestURI.endsWith("cashierManage.jsp")
                    || requestURI.endsWith("updateDish.jsp") || requestURI.endsWith("updateTable.jsp")
                    || requestURI.endsWith("updateAccountandStaff.jsp") || requestURI.endsWith("tableID.jsp")
                    || requestURI.endsWith("header.jsp") || requestURI.endsWith("footer.jsp")
                    || requestURI.endsWith("bill.jsp") || requestURI.endsWith("continue.jsp")
                    || requestURI.endsWith("listDish.jsp") || requestURI.endsWith("listStaff.jsp")
                    || requestURI.endsWith("listTable.jsp")) {
                // Nếu chưa đăng nhập và đang cố gắng truy cập các trang bảo vệ
                res.sendRedirect(loginURI); // Chuyển hướng đến trang đăng nhập
            } else {
                // Nếu không phải là trang bảo vệ, cho phép tiếp tục request
                chain.doFilter(request, response);
            }
        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("LoginFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("LoginFilter()");
        }
        StringBuffer sb = new StringBuffer("LoginFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
