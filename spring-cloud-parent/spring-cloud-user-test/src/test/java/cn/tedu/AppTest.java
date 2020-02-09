package cn.tedu;

import static org.junit.Assert.assertTrue;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void test07() {
        Map<String,String> m = new HashMap(  );
        m.put( "1","a");
        m.put( "2","b");
        m.put( "3","c");

        //将map中的key存在set集合中
        Set<String> set = m.keySet();
        //迭代这个set集合
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String skey = iterator.next();
            System.out.println(skey);
        }
    }

}
