package cn.tedu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class TransportClientTest {
    /*
    索引的管理
    文档的管理
    搜索(看到丰富的搜索query对象)
     */
    //初始化创建一个连接对象client
    private TransportClient client;
    @Before//test运行之前运行代码方法
    public void init() throws Exception {
        //springboot 初始化逻辑
        //setting保存集群基本信息 cluster.name
        //EMPTY默认值,如果集群名称不是elasticsearch

        /*Settings setting = Settings.builder().put("cluster.name"
                , "big1909").build();*/
        client=new PreBuiltTransportClient(Settings.EMPTY);
        //连接节点ip port(Tcpip协议客户端,9300)
        //在client对象中,添加3个节点的信息,准备好每一个节点
        //socket连接地址 ip port
        InetSocketTransportAddress addr1=
            new InetSocketTransportAddress(
                InetAddress.getByName("10.9.39.13"),9300);
        InetSocketTransportAddress addr2=
            new InetSocketTransportAddress(
                InetAddress.getByName("10.9.100.26"),9300);
        InetSocketTransportAddress addr3=
            new InetSocketTransportAddress(
                InetAddress.getByName("10.9.159.20"),9300);
        //client接收这些地址
        client.addTransportAddress(addr1);
        client.addTransportAddress(addr2);
        client.addTransportAddress(addr3);
    }
    //索引的操作
    //需要通过这个client连接对象获取一个es中提供的admin对象
    //操作管理索引
    @Test
    public void indexManage(){
        //获取管理索引的对象admin
        AdminClient admin = client.admin();
        /*ClusterAdminClient cluster = admin.cluster();*/
        IndicesAdminClient indices = admin.indices();
        //PreBuitClient的api调用过程中,总是先实现需要的request对象
        //然后在调用prepare**的方法实现请求的发送
        //判断索引是否存在
        //构造request器
        IndicesExistsRequestBuilder request = indices.prepareExists("index01");
        //request发送,命令执行到es
        IndicesExistsResponse reponse = request.get();
        //response对象接收es返回结果 解析json
        System.out.println(reponse.isExists());
        //DeleteIndexRequestBuilder index01 = indices.prepareDelete("index01");
        /*indices.prepareDelete();
        indices.prepareCreate();*/
        //等等
    }
    //文档数据管理
    @Test
    public void docManage() throws Exception {
        //增,删,查
        //查使用client 查看某个文档数据
        GetRequestBuilder request = client.prepareGet("index01", "article", "1");
        GetResponse reponse = request.get();
        //从reponse拿到数据
        System.out.println(reponse.getSourceAsString());
        /*client.prepareDelete("","","")*/
        //新增文档数据时,通过获取一个资源链接,通过request将
        //参数添加到对象中,最终发送到es执行新增文档
        IndexRequestBuilder request1 = client.prepareIndex("index010", "prod", "1");
        Product p=new Product();
        p.setProductName("文档测试商品数据");
        p.setProductId("1");
        p.setProductCategory("1");
        p.setProductDescription("1");
        //转化为json
        String json = MapperUtil.MP.writeValueAsString(p);
        IndexResponse response1 = request1.setSource(json).get();
    }
    //搜索功能
    @Test
    public void search(){
        //底层封装了lucene搜索功能
        //获取需要的查询条件query
        //termQuery
        TermQueryBuilder query = QueryBuilders.termQuery("title", "简");
       /* BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(query1).mustNot(query2)*/
        //搜索数据
        SearchRequestBuilder request = client.prepareSearch("index01", "index010");
        //request发送之前填写一些必备参数 搜索一定要实现分页
        //底层分页查询 lucene浅查询,封装了分页逻辑
        //start起始位置,rows条数
        SearchResponse response = request.setQuery(query).setFrom(0).setSize(10).get();
        //topDocs-->scoreDoc hits(hits)
        SearchHits topDocs = response.getHits();
        System.out.println("总查询条数:"+topDocs.totalHits);
        //遍历循环
        SearchHit[] hits = topDocs.getHits();
        for (SearchHit hit:hits){
            //获取数据 _index_type _version _id _source
            System.out.println("id:"+hit.getId());
            System.out.println("doc数据:"+hit.getSourceAsString());

        }
    }

}
