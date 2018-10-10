package com.e3mall.fast;

import com.e3mall.commom.utils.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/6 16:20
 */
public class FastDfs {
    @Test
    public void FastDfsTest() throws IOException, MyException {
        //创建一个文件,文件名任意,内容是tracker(图片)服务器地址
        //使用全局对象加载配置文件
        ClientGlobal.init("E:/e3parent/e3manager-web/src/main/resources/conf/client.conf");
        //创建TrackerClient对象
        TrackerClient client=new TrackerClient();
        //通过TrackerClient获得TrackerServer
        TrackerServer trackerServer = client.getConnection();
        //创建一个StrorageServer的引用,可以是null
        StorageServer storageServer=null;
        //创建一个StorageClient,参数需要TrackerServer和StorageServer
        StorageClient storageClient=new StorageClient(trackerServer,storageServer);
        //使用storageClient上传文件
        String[] jpgs = storageClient.upload_appender_file("C:\\Users\\acer\\Desktop\\123.jpg", "jpg", null);
        for (String jpg:jpgs) {
            System.out.println(jpg);
        }
    }
    @Test
    public void  FastDfsClientTest() throws Exception {
        FastDFSClient client=new FastDFSClient("E:/e3parent/e3manager-web/src/main/resources/conf/client.conf");
        String file = client.uploadFile("C:\\Users\\acer\\Desktop\\123.jpg");
        System.out.println(file);
    }

}
