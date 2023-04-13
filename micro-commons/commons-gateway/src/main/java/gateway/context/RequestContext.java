package gateway.context;



/**
 * @author DELL
 */
public class RequestContext {
    public static final ThreadLocal<String> threadLocal = new ThreadLocal();

    public RequestContext() {
    }

    public static void setRequestId(String requestId) {
        threadLocal.set(requestId);
    }

    public static String getRequestId() {
        return (String)threadLocal.get();
    }

    public static void clearRequestId() {
        threadLocal.remove();
    }
}
