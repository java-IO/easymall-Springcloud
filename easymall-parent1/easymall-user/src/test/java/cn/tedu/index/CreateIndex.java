package cn.tedu.index;

import cn.tedu.IKAnalyzer6x;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateIndex {
    @Test
    public void createIndex() throws Exception {
        //指定一个文件夹给lucene使用
        Path path = Paths.get("d://index01");
        FSDirectory dir=FSDirectory.open(path);
        //获取输出流对象writer
        //1.准备一个配置对象,封装使用的分词器
        IndexWriterConfig config=new IndexWriterConfig(
                new StandardAnalyzer());
        //2.创建索引的方式
            //create 每次创建索引,如果文件名相同,都会覆盖
            //append 追加,每次都会在同名索引下追加这次的数据
            //append_or_create 有则追加,无则创建
        config.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
        IndexWriter writer=new IndexWriter(dir,config);
        //准备输出的对象数据 大量的document对象集合
        Document doc1=new Document();
        Document doc2=new Document();
        //1.准备一批数据
            /*
                doc1: 视频网站吃相难看(title)
                      双重收费惹怒观众(content)
                      娱乐早知道(publisher)
                      4800 点击量(clicks)
                      webadd:http://www.zaozhidao.com/1.html
                doc2: 视频网站疯狂敛财
                      收费标准无上限,大家踊跃买盗版
                      娱乐八卦站
                      5000 点击量(clicks)
                      webadd:http://www.baguazhan.com/2.html
             */
        //在处理document中不同的域field属性时,要考虑类型,和存储
        //考虑计算分词的逻辑.
        /*
            name:域名称,按照业务逻辑自定义
            value:该属性的具体指
            Store:是否存储在索引文件中??????????
         */
        doc1.add(new TextField("title",
                "视频网站吃相难看", Field.Store.YES));
        doc1.add(new TextField("content",
                "双重收费惹怒观众", Field.Store.YES));
        doc1.add(new TextField("publisher",
                "娱乐早知道",Field.Store.YES));
        doc1.add(new IntPoint("price",4800));
        doc1.add(new StringField("priceView","48.00元", Field.Store.YES));
        doc1.add(new TextField("webaddr",
                "http://www.zaozhidao.com/1.html", Field.Store.YES));
        //price 既需要使用数值类型,计算索引,范围搜索
        //也需要存储到document中
        //第一种,创建索引时一个IntPoint 设置搜索的范围数字
        //单独设置一个新的field做显示
        doc2.add(new TextField("title",
                "视频网站疯狂敛财", Field.Store.YES));
        doc2.add(new TextField("content",
                "收费标准无上限,大家踊跃买盗版", Field.Store.YES));
        doc2.add(new TextField("publisher",
                "娱乐八卦网",Field.Store.YES));
        doc2.add(new IntPoint("price",5000));
        doc2.add(new StringField("webaddr",
                "haha.kaka.wawa", Field.Store.YES));
        //DOC设计field类型怎么使用?Store.YES/NO怎么使用
        //writer输出,倒排索引计算过程,也是在输出时完成
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.commit();
    }
}
