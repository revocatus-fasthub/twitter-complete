package tz.co.fasthub.ona.instagram.listener;


import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.oauth.InstagramService;
import tz.co.fasthub.ona.instagram.Constants;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DemoAppContextListener implements ServletContextListener {

    private static final Token EMPTY_TOKEN = null;

    public void contextInitialized(ServletContextEvent sce) {

        String clientId = System.getenv(Constants.CLIENT_ID);
        String clientSecret = System.getenv(Constants.CLIENT_SECRET);
        String callbackUrl = System.getenv(Constants.REDIRECT_URI);


        InstagramService service = new InstagramAuthService()
                .apiKey("f53a95ed9ba34e4bab51fdd2d588b984")
                .apiSecret("db2aea5867bb4d97a48dc21e94dc5dfb")
                .callback("http://127.0.0.1:8080/instagram/callback")
                .build();

        sce.getServletContext().setAttribute(Constants.INSTAGRAM_SERVICE, service);


    }

    public void contextDestroyed(ServletContextEvent sce) {

        sce.getServletContext().removeAttribute(Constants.INSTAGRAM_SERVICE);

    }

}