package log.config;


import log.aop.AuditLogAspect;
import log.aop.SearchLogAspect;
import log.queue.LogQueue;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author sgz
 * @date 2023/3/22 17:24
 * @Deception logAutoConfig
 */
@Import({AuditLogAspect.class, SearchLogAspect.class, LogQueue.class})
@Configuration
public class LogAutoConfig {

}
