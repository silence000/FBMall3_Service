import com.excmmy.RunUAAServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {RunUAAServer.class})
public class DBTest {
    @Test
    public void test1() {
        System.out.println("测试输出");
    }
}
