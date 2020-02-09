package cn.tedu.search.controller;

import cn.tedu.search.service.IndexService;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search/manage")
public class IndexController {
    @Autowired
    private IndexService is;
    //创建索引
    @RequestMapping("create")
    public String createIndex(String indexName) throws Exception {
        is.createIndex(indexName);
        return "success";
    }
    //实现搜索功能
    @RequestMapping("query")
    public List<Product> search(String query,
            Integer rows, Integer page){
        return is.search(query,rows,page);
    }
}
