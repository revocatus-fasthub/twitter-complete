package tz.co.fasthub.ona.instagram;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramApi;
import org.jinstagram.auth.exceptions.OAuthException;
import org.jinstagram.auth.model.OAuthConfig;
import org.jinstagram.auth.model.OAuthRequest;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@RequestMapping("/instagram")
public class InstagramTokenHandler extends HttpServlet {

    private static final String AUTHORIZATION_CODE = "authorization_code";

    private static final Token EMPTY_TOKEN = null;

    private static InstagramApi api;

    private final OAuthConfig config;


    public InstagramTokenHandler(InstagramApi api,OAuthConfig config){
     InstagramTokenHandler.api =api;
     this.config=config;
    }


    private static final Logger log = LoggerFactory.getLogger(InstagramTokenHandler.class);

    private static final String AUTHORIZATION_URL_prefix = "https://api.instagram.com/oauth/authorize/?client_id=";
    private static final String AUTHORIZATION_URL_middle= "&redirect_uri=";
    private static final String AUTHORIZATION_URL_suffix="&response_type=code";

    private static final String AUTHORIZATION_URL_COMPLETE = AUTHORIZATION_URL_prefix+Constants.CLIENT_ID+AUTHORIZATION_URL_middle+Constants.REDIRECT_URI+AUTHORIZATION_URL_suffix;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String code = request.getParameter("code");

        InstagramService service = (InstagramService) request.getServletContext().getAttribute(Constants.INSTAGRAM_SERVICE);

        Verifier verifier = null;
        log.info("code: "+code);

        //authorize here
      //  getAccessToken(verifier);

        Token accessToken = service.getAccessToken(new Verifier(code));
        log.info("accessToken: "+accessToken);

        getAccessToken(verifier);

        Instagram instagram = new Instagram(accessToken);
        log.info("instagram: "+instagram);

        HttpSession session = request.getSession();

        session.setAttribute(Constants.INSTAGRAM_OBJECT, instagram);

        System.out.println(request.getContextPath());
        //Get user info
        UserInfo userInfo = instagram.getCurrentUserInfo();

        // Redirect to User Profile page.
        response.sendRedirect(request.getContextPath() + "/profile.jsp");

    }

    @RequestMapping(value = "/authorizationUrl")
    public String connectInsta(){
        return "redirect:"+AUTHORIZATION_URL_COMPLETE;
    }

    @RequestMapping("/callback")
    public String authorized(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
        HttpSession session = request.getSession();

        Object objInstagram = session.getAttribute(Constants.INSTAGRAM_OBJECT);
        if (objInstagram != null) {
            return "success";
            //response.sendRedirect(request.getContextPath() + "/profile.jsp");
        }

        InstagramService service = (InstagramService) session.getServletContext().getAttribute(Constants.INSTAGRAM_SERVICE);

        String authorizationUrl = service.getAuthorizationUrl();

        return "index";
    }


    private Token getAccessToken(Verifier verifier) {
        OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
        request.addBodyParameter(Constants.CLIENT_ID, config.getApiKey());
        request.addBodyParameter(Constants.CLIENT_SECRET, config.getApiSecret());
        request.addBodyParameter(Constants.GRANT_TYPE, AUTHORIZATION_CODE);
        request.addBodyParameter(Constants.CODE, verifier.getValue());
        request.addBodyParameter(Constants.REDIRECT_URI, config.getCallback());

        if (config.hasScope()) {
            request.addBodyParameter(Constants.SCOPE, config.getScope());
        }

        if (config.getDisplay() != null) {
            request.addBodyParameter(Constants.DISPLAY, config.getDisplay());
        }

        if (config.getRequestProxy() != null) {
            request.setProxy(config.getRequestProxy() );
        }

        Response response;
        try {
            response = request.send();
        } catch (IOException e) {
            throw new OAuthException("Could not get access token", e);
        }

        return api.getAccessTokenExtractor().extract(response.getBody());

    }
}
