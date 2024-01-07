import com.javaquiz.beans.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final String[] ALLOWED_PATHS = { "/login.jsp", "/registration.jsp", "/login", "/register" };

    private static final String[] ADMIN_ALLOWED_PATHS = {
            "/quizzes.jsp", "/addQuiz.jsp", "/editQuiz.jsp", "/addQuiz", "/editQuiz", "/deleteQuiz",
            "/questions.jsp", "/editQuestion.jsp", "/addQuestion.jsp", "/addQuestion", "/deleteQuestion",
            "/userProfile.jsp", "/editUserProfile"
    };

    private static final String[] USER_ALLOWED_PATHS = {
            "/userProfile.jsp","/editUserProfile", "/takeQuiz.jsp", "/quizHistory.jsp"
    };

    private static final String[] RESOURCE_DIRS = {
            "/css/", "/fonts/", "/images/", "/js/", "/scss/", "/assets/"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
        System.out.println("AuthFilter: init method STARTED");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()).replaceAll("[/]+$", "");

        if (isResourcePath(path)) {
            System.out.printf("Resource path: %s\n", path);
            // Allow access to resources without authentication
            chain.doFilter(request, response);
            System.out.println("Resource path: chain.doFilter method FINISHED. Access to resources without authentication");
        } else if (isAdminPath(path)) {
            System.out.printf("Admin path: %s\n", path);
            // Admin path, check admin privileges
            checkAdminAccess(httpRequest, httpResponse, chain);
            System.out.println("Admin path: checkAdminAccess method FINISHED. User is logged in as an admin");
        } else if (isUserPath(path)) {
            System.out.printf("User path: %s\n", path);
            // User path, check user privileges
            checkUserAccess(httpRequest, httpResponse, chain);
            System.out.println("User path: checkUserAccess method FINISHED. User is logged in as a user");
        } else if (isAllowedPath(path)) {
            System.out.printf("Allowed path: %s\n", path);
            // Allow access to the allowed paths without authentication
            chain.doFilter(request, response);
            System.out.println("Allowed path: chain.doFilter method FINISHED. User is not logged in");
        } else {
            System.out.println();
            // Check authentication for other paths
            HttpSession session = httpRequest.getSession(false);

            if (session != null && session.getAttribute("user") != null) {
                // User is logged in, allow access to the requested page
                chain.doFilter(request, response);
            } else {
                // User is not logged in, redirect to the login page
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            }
        }
    }

    private boolean isResourcePath(String path) {
        for (String resourceDir : RESOURCE_DIRS) {
            if (path.startsWith(resourceDir)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdminPath(String path) {
        for (String adminPath : ADMIN_ALLOWED_PATHS) {
            if (path.equals(adminPath) || path.startsWith(adminPath + "/")) {
                return true;
            }
        }
        return false;
    }

    private boolean isUserPath(String path) {
        for (String userPath : USER_ALLOWED_PATHS) {
            if (path.equals(userPath) || path.startsWith(userPath + "/")) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllowedPath(String path) {
        for (String allowedPath : ALLOWED_PATHS) {
            if (path.equals(allowedPath) || path.startsWith(allowedPath + "/")) {
                return true;
            }
        }
        return false;
    }

    private void checkAdminAccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("AuthFilter: checkAdminAccess method STARTED");
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("AuthFilter: checkAdminAccess method: session is not null");
            User user = (User) session.getAttribute("user");

            if (user != null && user.isAdmin()) {
                System.out.println("AuthFilter: checkAdminAccess method: user is not null and is admin");
                // User is logged in as an admin, allow access to the requested page
                chain.doFilter(request, response);
            } else {
                System.out.println("AuthFilter: checkAdminAccess method: user is not null and is not admin");
                throw new ServletException("Access denied");
                // User is not logged in as an admin, redirect to the login page
//                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } else {
            // User is not logged in, redirect to the login page
            System.out.println("AuthFilter: checkAdminAccess method: session is null. No user is logged in");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    private void checkUserAccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("AuthFilter: checkUserAccess method STARTED");
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("AuthFilter: checkUserAccess method: session is not null");
            User user = (User) session.getAttribute("user");

            if (user != null && !user.isAdmin() ) {
                System.out.println("AuthFilter: checkUserAccess method: user is not null and is a simple user. Not ADMIN");
                // User is logged in, allow access to the requested page
                chain.doFilter(request, response);
            } else {
                System.out.println("AuthFilter: checkUserAccess method: user is not null and is not a simple user. ADMIN");
                // User is not logged in, redirect to the login page
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } else {
            System.out.println("AuthFilter: checkUserAccess method: session is null. No user is logged in");
            // User is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        System.out.println("AuthFilter: destroy method STARTED");
        // Cleanup code, if needed
    }
}
