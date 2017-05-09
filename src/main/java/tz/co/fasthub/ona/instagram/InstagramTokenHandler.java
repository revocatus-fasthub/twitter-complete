package tz.co.fasthub.ona.instagram;

import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@RequestMapping("/instagram")
public class InstagramTokenHandler extends HttpServlet {

    private ConnectionRepository connectionRepository=null;

    private static final Logger log = LoggerFactory.getLogger(InstagramTokenHandler.class);

    private static final String AUTHORIZATION_URL_prefix = "https://api.instagram.com/oauth/authorize/?client_id=";
    private static final String AUTHORIZATION_URL_middle= "&redirect_uri=";
    private static final String AUTHORIZATION_URL_suffix="&response_type=code";

    private static final String AUTHORIZATION_URL_COMPLETE = AUTHORIZATION_URL_prefix+Constants.CLIENT_ID+AUTHORIZATION_URL_middle+Constants.REDIRECT_URI+AUTHORIZATION_URL_suffix;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String code = request.getParameter("code");

        InstagramService service = (InstagramService) request.getServletContext().getAttribute(Constants.INSTAGRAM_SERVICE);


     //   String oauth_verifier = (String) request.getSession().getAttribute(String.valueOf(verifier));
       // log.info("oauth_verifier: "+oauth_verifier);

        //authorize here

        Token reqToken = service.getRequestToken();
        log.info("reqToken: "+reqToken);

        service.getAuthorizationUrl();
        request.getParameter("oauth_token");
        request.getParameter("oauth_verifier");

        Verifier verifier = new Verifier(request.getParameter("oauth_verifier"));
      //  log.info("code: "+code);
        Token gAccToken = service.getAccessToken(verifier);

//////////////////
        Token accessToken = service.getAccessToken(verifier);
        log.info("accessToken: "+accessToken);

        Instagram instagram = new Instagram(accessToken);
        log.info("instagram: "+instagram);

        HttpSession session = request.getSession();

        session.setAttribute(Constants.INSTAGRAM_OBJECT, instagram);

        System.out.println(request.getContextPath());
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



}
