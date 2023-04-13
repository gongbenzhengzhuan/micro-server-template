package utils.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sgz
 * @date 2023/3/22 15:45
 * @Deception Ip工具类
 */
public class IpUtil {

    private static final String LOCAL_IP = "127.0.0.1";

    /**
     * 获取IP地址
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? LOCAL_IP : ip;
    }

    /**
     * 获取mac地址
     *
     * @param ip ip地址
     * @return mac地址
     * @throws Exception e
     */
    public static String getMacByIP(String ip) throws Exception {
        String result = commond("ping -n 3 " + ip);
        if (result.contains("TTL")) {
            result = commond("arp -a " + ip);
        }
        String regExp = "([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(result);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            String temp = matcher.group();
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    public static String commond(String cmd) throws IOException {
        Process process = Runtime.getRuntime().exec(cmd);
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    /**
    * @Author       Eric·Wang[王承]
    * @Date         2023-03-28 20:03:31
    * @Description  根据IP获取mac地址
    */
    @SuppressWarnings("all")
    public static String getMacAddressByIp(String ip) throws Exception {
        if(ping(ip)){
            String result = command("arp -a " + ip);
            String regExp = "([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}";
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(result);
            StringBuilder mac = new StringBuilder();
//            对字符串进行匹配,匹配到的字符串可以在任何位置
            while(matcher.find()){
//                返回匹配到的子字符串
                String temp = matcher.group();
                mac.append(temp);
            }
            return mac.toString();
        }
        return null;
    }

    /**
    * @Author       Eric·Wang[王承]
    * @Date         2023-03-28 20:03:40
    * @Description  ping IP地址
    * @Param[0] java.lang.String ip
    * @Return       boolean
    */
    @SuppressWarnings("all")
    private static boolean ping(String ip) throws Exception {
        String os = getOsName();
        String ping = "";
        if(os.startsWith("Windows")){
            ping = "ping " + ip + " -n 2";
        }else if(os.startsWith("Linux")){
            ping = "ping " + ip + " -c 2";
        }
        String result = command(ping);
        if(result.contains("TTL") || result.contains("ttl")){
            return true;
        }
        return false;
    }

    private static String command(String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        InputStream in = process.getInputStream();
        StringBuilder result = new StringBuilder();
        byte[] data = new byte[256];
        while(in.read(data) != -1){
//            操作系统中的编码方式
            String encoding = System.getProperty("sun.jnu.encoding");
            result.append(new String(data,encoding));
        }
        return result.toString();
    }

    private static String getOsName() {
        return System.getProperty("os.name");
    }

}
