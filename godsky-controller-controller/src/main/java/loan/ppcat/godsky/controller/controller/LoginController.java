package loan.ppcat.godsky.controller.controller;

import loan.ppcat.godsky.controller.util.InputStreamUtil;
import loan.ppcat.godsky.controller.util.SystemUtil;
import loan.ppcat.godsky.controller.util.TTSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Controller
public class LoginController {
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "app/login";
    }

    private Properties properties = new Properties();

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String username, String password, HttpServletRequest httpServletRequest) throws IOException {
        InputStream inputStream = null;
        String usernameConfig = "";
        String passwordConfig = "";
        try {
            inputStream = LoginController.class.getResourceAsStream("/server.properties");
            properties.load(inputStream);
            usernameConfig = properties.getProperty("username");
            passwordConfig = properties.getProperty("password");

        } finally {
            InputStreamUtil.closeInputStream(inputStream);
        }

        HttpSession session = httpServletRequest.getSession();
        if (username.equals(usernameConfig) && password.equals(passwordConfig)) {
            session.setAttribute("username", username);
        } else {
            return "redirect:/login";
        }
        return "redirect:/resources/app/index.html";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("username");
        return "redirect:/login";
    }
}
