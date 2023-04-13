package swagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Swagger默认路径屏蔽
 *
 * @author lizhongpeng
 * @date 2021/07/23 17:27
 */
@Controller
@ApiIgnore
public class SwaggerRedirectController {

    /**
     * 对Swagger默认地址进行重定向
     *
     * @return java.lang.String
     * @author lizhongpeng
     * @date 2021/07/23 17:07
     * @params []
     */
    @GetMapping({"swagger-ui/index.html", "index.html", "index.htm", ""})
    public String swaggerRedirectReq() {
        return "redirect:/doc.html";
    }
}
