package cn.tedu;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

public class AnalyzerTest {
    /*
    准备一批不同的分词器实现类,对同一个字符文本进行
    分词计算,获取打印词项文本属性
     */
    //做一个根据不同分词器实现的打印文本代码
    public void printChars(Analyzer a,String msg) throws IOException {
        //对msg进行分词计算,打印文本
        //获取StringReader
        StringReader reader=new StringReader(msg);
        //获取分词器对象的分词流tokenStream
        TokenStream tokenStream = a.tokenStream("title", msg);
        //tokenStream计算弄完分词的流数据,获取每一个token的文本,需要
        //将指针跳到首位
        tokenStream.reset();
        //获取每一个token的文本属性,打印
        //获取当前指针位置的token文本数据
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        //循环获取后一个词项的文本
        while(tokenStream.incrementToken()){
            System.out.println(attribute.toString());
        }
    }
    @Test
    public void run() throws IOException {
        //准备不同的分词器对象
        Analyzer a1=new StandardAnalyzer();//将中文按字处理,英文词word
        Analyzer a2=new WhitespaceAnalyzer();//句,诗词,英文词
        Analyzer a3=new SimpleAnalyzer();//句,英文词
        Analyzer a4=new SmartChineseAnalyzer();//中文语句的词语切分
        Analyzer a5=new IKAnalyzer6x();
        String msg="java编程思想";
        System.out.println("*********标准********");
        printChars(a1,msg);
        System.out.println("*********空格********");
        printChars(a2,msg);
        System.out.println("*********简单********");
        printChars(a3,msg);
        System.out.println("*********中文智能********");
        printChars(a4,msg);
        System.out.println("*********ik中文********");
        printChars(a5,msg);
    }
}
