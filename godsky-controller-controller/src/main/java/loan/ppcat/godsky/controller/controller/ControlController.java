package loan.ppcat.godsky.controller.controller;

import loan.ppcat.godsky.controller.service.implement.SystemInitService;
import loan.ppcat.godsky.controller.util.TTSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@RequestMapping("/controller")
public class ControlController {
    @Inject
    private SystemInitService systemInitService;

    @RequestMapping("/list")
    @ResponseBody
    public loan.ppcat.godsky.controller.model.Controller getDevices() {
        return systemInitService.getController();
    }
}
