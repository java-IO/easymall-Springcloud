package cn.tedu.service;

import com.jt.common.utils.UploadUtil;
import com.jt.common.vo.PicUploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class PicService {
    public PicUploadResult picUpload(MultipartFile pic) {
        PicUploadResult result=new PicUploadResult();
        /*
            1.修改文件名称,让图片不重名(存放在不同的文件目录,同一个目录不出现
            多个图片),uuid
            2.判断后缀是否合法
                2.1合法 jpg gif png... uuid+ext
                2.2不合法,return error=1的result
            3.生成多级目录 /upload/a/b/4/d/3/e/d/6/, ab4d3e6 hashHex字符串
                特点是图片名称相同 计算的8位字符串
            4.存储路径 d:/img+/upload/a/b/4/d/3/e/d/6/
                File对象创建 mkdirs(不存在才创建)
            5.图片输出到d:/img+/upload/a/b/4/d/3/e/d/6/+文件名称
            6.拼接url http://image.jt.com/+/upload/a/b/4/d/3/e/d/6/+文件名称
         */
        //重命名 暂时无后缀
        String uuid= UUID.randomUUID().toString();
        //解析后缀合法,原名获取
        String oName=pic.getOriginalFilename();//25363.jpg
        //截取后缀
        String extName =
            oName.substring(oName.lastIndexOf("."));
        //判断合法,正则
        boolean ok = extName.matches(".(jpg|gif|png)$");
        if(!ok){//非法
            result.setError(1);
            return result;
        }
        //重整文件名称uuid+ext
        String nName=uuid+extName;//f577f9f9-159e-4aaf-9332-fd7b294bc208.jpg
        //生成路径,d:/img/upload/3/d/3/d/3/f/5/f,3d3d3f5f
        //调用工具类中一个UploadUtil 创建这个路径
        //nName是文件名称,会根据文件名称创建多级目录,upload是前缀
        String path = UploadUtil.getUploadPath(nName, "upload");
        //创建d盘下的img/upload/****
        String dir="d:/img/"+path+"/";
        //创建多级目录
        File _dir=new File(dir);//目录文件夹
        if(!_dir.exists()){//说明文件夹不存在
             _dir.mkdirs();}
        //流输出文件到该目录,创建nName名称图片
        try{
            pic.transferTo(new File(dir+nName));
        }catch(Exception e){
            e.printStackTrace();
            result.setError(1);
            return result;
        }
        //拼接一个url地址返回 http://image.jt.com/+path+"/"+nName
        String url="http://image.jt.com/"+path+"/"+nName;
        result.setUrl(url);
        return result;//{"error":0,"url":"http://image**"}
    }
}

















