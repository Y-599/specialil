package lzy.special.special.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//路由控制台
@Controller
@Api(tags = "我是路由控制类")
public class RoutesController {

    @GetMapping("/index")
    @ResponseBody
    @ApiOperation(value ="我是首页")
    public  String  toIndex(){

        return "index";
    }
}
