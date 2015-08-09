package net.martinmine.jsp.mvc;

import net.martinmine.jsp.mvc.annotations.Verb;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Receives and routes HTTP calls for the website.
 */
public class SiteRouter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(SiteRouter.class.getSimpleName());

    private final Map<String, PageController> pages;
    private ServletContext context;

    /**
     * Creates a new site router.
     */
    public SiteRouter() {
        this.pages = new HashMap<>();
    }

    /**
     * Initializes a new site router and loads all controllers based on the pages.xml descriptor file.
     * Each controller-node in this XML-document specifies which view (JSP file) belongs to which
     * controller in addition to what URI each of those maps against.
     * Note that this implementation only allows one URI per controller and view, which is something
     * that can be further extended later.
     * @param filterConfig Configuration.
     * @throws ServletException Something really bad happened.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ServletException(e);
        }

        try (InputStream is = classLoader.getResourceAsStream("pages.xml")) {
            final Document doc = dBuilder.parse(is);
            final NodeList controllerList = doc.getElementsByTagName("controller");

            for (int i = 0; i < controllerList.getLength(); i++) {
                final Element controllerDescriptor = (Element) controllerList.item(i);
                final String jspPath = controllerDescriptor.getAttribute("viewUri");
                final String classPath = controllerDescriptor.getAttribute("class");
                final String resourceUri = controllerDescriptor.getAttribute("resourceUri");

                final Class<?> clazz = Class.forName(classPath);
                final PageController instance = (PageController) clazz.newInstance();
                instance.setViewPath(jspPath);

                this.pages.put(resourceUri, instance);
                LOGGER.info("Registered controller for page " + jspPath + " with class " + classPath);
            }
        } catch (IOException | SAXException | ClassNotFoundException
                | IllegalAccessException | InstantiationException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e);
        }
    }

    /**
     * Receives an HTTP call and routes the call to its respective controller and forwards the call
     * to the view associated with the controller. If no controller is found for the request, the
     * call will be redirected to the 404.jsp file. Unless the controller has specified the HTTP
     * status code to be anything else than HTTP 200 (which is the default one), the call will be
     * forwarded to the view associated with the controller.
     * @param servletRequest Incoming request.
     * @param servletResponse Outgoing response.
     * @param filterChain The filter chain.
     * @throws IOException Error reading from the connection.
     * @throws ServletException Something wrong happened while routing the call to its view.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String uri = request.getRequestURI();
        final PageController pageController = pages.get(uri);

        if (pageController == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            forwardToView(request, response, "/404.jsp");
        } else {
            for (Method method : pageController.getClass().getMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    Verb httpVerb = annotation.annotationType().getAnnotation(Verb.class);
                    // We must go deeper ...
                    if (httpVerb != null && httpVerb.value().equals(request.getMethod())) {
                        try {
                            // TODO: Invoke methods that doesn't have both request/response
                            method.invoke(pageController, request, response);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new ServletException(e);
                        }
                    }
                }
            }

            pageController.handleRequest(request, response);

            if (response.getStatus() == HttpServletResponse.SC_OK) {
                forwardToView(request, response, pageController.getViewPath());
            }
        }
    }

    private void forwardToView(final ServletRequest request, final ServletResponse response, final String viewPath)
            throws ServletException, IOException {
        final RequestDispatcher dispatcher = context.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        pages.clear();
    }
}
