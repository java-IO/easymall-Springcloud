package cn.tedu;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.User;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public static void main(String[] args) throws JsonProcessingException {
        User user=new User();
        user.setUserPassword("123");
        user.setUserName("wanglaoshi");
        ObjectMapper mapper=new ObjectMapper();
        System.out.println(mapper.writeValueAsString(user));
    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
