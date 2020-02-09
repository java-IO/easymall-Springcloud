package cn.tedu.index;

import cn.tedu.IKAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SearcherIndex {
    /*
    词项搜索,从一个域中,对比词项,匹配则返回数据
     */
    @Test
    public void termQuery() throws Exception {
        //指向d:/index01
        Path path = Paths.get("d://index01");
        FSDirectory dir=FSDirectory.open(path);
        //搜索对象创建searcher 包装reader
        IndexReader reader= DirectoryReader.open(dir);
        IndexSearcher search=new IndexSearcher(reader);
        //创建查询条件query对象
        Term term=new Term("title","疯狂");
        Query query=new TermQuery(term);
        //调用api做浅查询
        TopDocs topDocs = search.search(query, 10);//query构造的查询条件,10查询前10条
        //浅查询 分页查询有关,topDocs对分词词项计算的结果 主要包含了id集合
        System.out.println("查询总条数:"+topDocs.totalHits);
        //遍历循环topDocs中封装了id值的对象数据
        //每个元素封装了查询数据的所有document的id值
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //page=2 rows=5
        int count=0;
        for(ScoreDoc score:scoreDocs){
            count++;
            System.out.println("查询的docid有:"+score.doc);
            //读取document数据
           /* if(count<5){
                continue;
            }*/
            Document doc = search.doc(score.doc);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("webaddr:"+doc.get("webaddr"));
            System.out.println("clicks:"+doc.get("clicks"));
            System.out.println("publisher:"+doc.get("publisher"));
        }
    }

    /*
    多域查询,对文本进行分词解析,排列组合词项,底层
    生成多个termQuery,查询结果的并集返回
     */
    @Test
    public void multiFieldQuery() throws Exception, ParseException {
        Path path = Paths.get("d://index01");
        FSDirectory dir=FSDirectory.open(path);
        IndexSearcher search=new IndexSearcher
                (DirectoryReader.open(dir));
        //多域查询的query对象
        //多域查询解析器,指定使用IKAnalyzer
        //多域,域名数组
        String[] fields={"title","content"};
        MultiFieldQueryParser parser=
                new MultiFieldQueryParser(fields,new IKAnalyzer6x());
        Query query=parser.parse("疯狂观众");
        //ik解析分词 疯狂,青蛙,title-疯狂,title-青蛙,content-疯狂
        //content-青蛙
        //获取浅查询结果,遍历读取数据
        TopDocs topDocs = search.search(query, 10);
        System.out.println("查询总条数:"+topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for(ScoreDoc sd:scoreDocs){
            Document doc = search.doc(sd.doc);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("webaddr:"+doc.get("webaddr"));
            System.out.println("clicks:"+doc.get("clicks"));
            System.out.println("publisher:"+doc.get("publisher"));
        }
    }
    /*
    布尔查询,形成需要的结果集
     */
    @Test
    public void booleanQuery() throws Exception, ParseException {
        Path path = Paths.get("d://index01");
        FSDirectory dir=FSDirectory.open(path);
        IndexSearcher search=new IndexSearcher
                (DirectoryReader.open(dir));
        //构造一个boolean查询条件.
            //子条件1
        Query query1=new TermQuery(new Term("title","疯狂"));
        BooleanClause bc1=new BooleanClause
                (query1, BooleanClause.Occur.FILTER);
            //子条件2
        Query query2=new TermQuery(new Term("content","视频"));
        BooleanClause bc2=new BooleanClause
                (query2, BooleanClause.Occur.MUST_NOT);
        //2个子条件创建boolean查询
        Query query = new BooleanQuery
                .Builder().add(bc1).add(bc2).build();
        //获取浅查询结果,遍历读取数据
        TopDocs topDocs = search.search(query, 10);
        System.out.println("查询总条数:"+topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for(ScoreDoc sd:scoreDocs){
            Document doc = search.doc(sd.doc);
            System.out.println("该document评分值:"+sd.score);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("webaddr:"+doc.get("webaddr"));
            System.out.println("clicks:"+doc.get("clicks"));
            System.out.println("price:"+doc.get("priceView"));
            System.out.println("publisher:"+doc.get("publisher"));
        }
    }
    /*
    范围搜索,查询price的范围值
     */
    /*
    布尔查询,形成需要的结果集
     */
    @Test
    public void rangeQuery() throws Exception, ParseException {
        Path path = Paths.get("d://index01");
        FSDirectory dir=FSDirectory.open(path);
        IndexSearcher search=new IndexSearcher
                (DirectoryReader.open(dir));
        //对应**Point类型的数据都可以实现范围查询条件获取
        Query query = IntPoint.newRangeQuery("price", 4700, 5200);
        //获取浅查询结果,遍历读取数据
        TopDocs topDocs = search.search(query, 10);
        System.out.println("查询总条数:"+topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for(ScoreDoc sd:scoreDocs){
            Document doc = search.doc(sd.doc);
            System.out.println("该document评分值:"+sd.score);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("webaddr:"+doc.get("webaddr"));
            System.out.println("clicks:"+doc.get("clicks"));
            System.out.println("price:"+doc.get("priceView"));
            System.out.println("publisher:"+doc.get("publisher"));
        }
    }

}
