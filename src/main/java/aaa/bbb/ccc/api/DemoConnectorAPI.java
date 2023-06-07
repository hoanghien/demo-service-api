package aaa.bbb.ccc.api;

import aaa.bbb.ccc.bean.RequestData;
import aaa.bbb.ccc.common.IGsonBase;
import aaa.bbb.ccc.common.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = {"/v1/service"})
@Slf4j
public class DemoConnectorAPI implements IGsonBase {

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    public ResponseData ping(HttpServletRequest request)
    {
        log.info("REPLY FROM {}", request.getRemoteAddr());
        return ResponseData.buildResponse("", "REPLY FROM " + request.getRemoteAddr());
    }

    @RequestMapping(value = "/receive_req", method = RequestMethod.POST, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    public ResponseData receiveRequest()
    {
        List<String> lstResponse = new ArrayList();
        lstResponse.add(ResponseData.SUCCESS);
        lstResponse.add(ResponseData.ERROR);

        for (int i = 0; i < 10; i++) {
            Collections.shuffle(lstResponse);
        }
        log.info("Response code = {}", lstResponse.get(0));
        return ResponseData.buildResponse(lstResponse.get(0), "Receive request success");
    }

    @RequestMapping(value = "/apply_rule", method = RequestMethod.POST, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    public ResponseData applyRule(@RequestBody String body)
    {
        log.info("Apply rule done {}", body);
        return ResponseData.buildResponse(ResponseData.SUCCESS, "Apply rule");
    }
}
