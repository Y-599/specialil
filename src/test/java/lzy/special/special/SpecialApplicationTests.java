package lzy.special.special;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest
class SpecialApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads()  {
        System.out.println(dataSource.getClass());
     
    }

}
