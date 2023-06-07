package aaa.bbb.ccc.controller;

import aaa.bbb.ccc.common.IGsonBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@Slf4j
public class HomeController implements IGsonBase {

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        String transId = String.valueOf(System.currentTimeMillis());
        log.info(transId + ";END:index");
        return "index";
    }
}
