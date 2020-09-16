package lzy.special.special.admin.controller;

import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lzy.special.special.common.security.JwtAuthenticatioToken;
import lzy.special.special.admin.popj.UserPopj;
import lzy.special.special.utils.JsonResult;
import lzy.special.special.utils.Md5Util;
import lzy.special.special.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//路由控制台
@Controller
@Api(tags = "我是路由控制类")
@RequestMapping("/admin/")
public class RoutesController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value ="我是首页")
    public  JsonResult  plaseLogin(@RequestBody UserPopj userPopj, HttpServletRequest request){
        String username = userPopj.getUsername();
        String password = userPopj.getPassword();

        int  codeResult = userPopj.getCodeResult();

        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
        return JsonResult.ok(200,token);
    }
    /*
     验证码
     */
    @RequestMapping("/captcha")
    @ResponseBody
    public JsonResult captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 算术类型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 40);
        captcha.setLen(2);  // 几位数运算，默认是两位
        captcha.getArithmeticString();  // 获取运算的公式：3+2=?.
       //captcha.out(response.getOutputStream());  // 输出验证码sout
        System.out.println(captcha.text());
        return JsonResult.ok(Md5Util.md5AsCaptcha(captcha.text(),""+System.currentTimeMillis()),captcha.toBase64());
    }
}
