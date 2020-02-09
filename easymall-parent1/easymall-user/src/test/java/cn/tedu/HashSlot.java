package cn.tedu;

import org.junit.Test;

public class HashSlot {
    @Test
    public void test(){
        //某个节点中,某一个byte数组第一个元素的值
        //表示0-7槽道号
        //打印槽道对应的管理权 1 管,0不管
        byte a=122;
        byte[] bytes={122,112,123,12};
        System.out.println(Integer.toBinaryString(a));
        for(int j=0;j<bytes.length;j++){
            for(int i=0;i<8;i++){
                System.out.println("当前槽道号:"+(i+(j*8)));
                //获取下标i对应的二进制
                int result=(bytes[j]>>(7-i))&1;
                System.out.println("管理权为:"+(result==1));
            }
        }

    }
}
