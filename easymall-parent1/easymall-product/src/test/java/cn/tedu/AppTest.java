package cn.tedu;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public static void main(String[] args) {
        for(int i=0;i<10;i++) {
            System.out.println(UUID.randomUUID().toString());
        }
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
