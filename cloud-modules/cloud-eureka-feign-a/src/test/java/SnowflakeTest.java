import com.zbank.feigna.bean.SnowflakeId;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnowflakeTest {

    private static final Logger logger = LoggerFactory.getLogger(SnowflakeTest.class);

    @Test
    public void getId() {
        // 初始化：每个节点需要唯一dataCenterId和workerId
        SnowflakeId idWorker = new SnowflakeId(1, 1); // 数据中心ID=1, 机器ID=1
        // 生成ID
        long id = idWorker.nextId();
        logger.info("{} length:{}",id,String.valueOf(id).length());
    }
}
