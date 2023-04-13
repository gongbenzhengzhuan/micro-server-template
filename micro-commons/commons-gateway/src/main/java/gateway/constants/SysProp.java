package gateway.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SysProp {


//    //server
//    @Value("${server.servlet.context-path:/bs}")
//    private String serverpath;

    //sso
//    @Value("${sso.casService}")
    private String ssoCasService = "";
//    @Value("${sso.apiService}")
//    private String ssoApiService;
//
//    //minio
//    @Value("${minio.endPoint}")
//    private String obsendPoint;
//    @Value("${minio.accessKey}")
//    private String obsaccessKey;
//    @Value("${minio.secretKey}")
//    private String obssecretKey;
//    @Value("${minio.bucket}")
//    private String obsbucket;
//    @Value("${minio.address:}")
//    private String obsaddress;

    //格尔网关
    @Value("${gatetway.geer.isUse}")
    private boolean geerIsUse;//是否使用格尔网关,true为启用，false为未启用
    @Value("${gatetway.geer.userKey:KOAL_CERT_CN}")
    private String geerUserKey;//格尔网关cookie中用户编码 key
    @Value("${gatetway.geer.ipKey:KOAL_CLIENT_IP}")
    private String geerIpKey;//格尔网关cookie中客户端ip key
    @Value("${gatetway.geer.loginExpireTime:7200000}")
    private long geerLoginExpireTime;//格尔网关用户登录信息过期时间 过期后 后台自动更新信息

}
