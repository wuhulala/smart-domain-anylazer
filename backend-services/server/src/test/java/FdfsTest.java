package org.csource.fastdfs;

import org.csource.common.NameValuePair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;

/**
 * @author chengdu
 * @date 2019/7/13.
 */
public class FdfsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FdfsTest.class);

    private static final String CONF_NAME = "fastdfs-client.properties";

    private StorageClient storageClient;

    private TrackerServer trackerServer;

    @Before
    public void initStorageClient() throws Exception {
        ClientGlobal.initByProperties(CONF_NAME);
        LOGGER.info("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
        LOGGER.info("charset=" + ClientGlobal.g_charset);
        TrackerClient tracker = new TrackerClient();
        trackerServer = tracker.getTrackerServer();
        StorageServer storageServer = null;
        storageClient = new StorageClient(trackerServer, storageServer);
    }

    @After
    public void closeClient() {
        LOGGER.info("close connection");
        if(storageClient != null){
            try {
               storageClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
    }

    public void writeByteToFile(byte[] fbyte, String fileName) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = new File(fileName);
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(fbyte);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    @Test
    public void upload() throws Exception{

        NameValuePair[] metaList = new NameValuePair[1];
        String local_filename = "15ef91c03bd3d3e152565b624f766749.jpg";
        String suffix = ""; //后缀名
        try{
            suffix = local_filename.substring(local_filename.lastIndexOf(".")+1);
        }catch (Exception e) {
            throw new RuntimeException("参数filename不正确!格式例如：a.png");
        }
        metaList[0] = new NameValuePair("fileName", local_filename);
        File file = new File("C:\\Users\\user\\Downloads\\15ef91c03bd3d3e152565b624f766749.jpg");
        InputStream inputStream = new FileInputStream(file);
        int length = inputStream.available();
        byte[] bytes = new byte[length];
        inputStream.read(bytes);
        String[] result = storageClient.upload_file(bytes, suffix, metaList);
        LOGGER.info("result {}", Arrays.asList(result));
        LOGGER.info("result is http://192.168.1.101:18888/{}/{}", result[0], result[1]);
        Assert.assertEquals(2, result.length);
    }

    @Test
    public void download() throws Exception {
        String[] uploadresult = {"group1", "M00/00/00/wKgBZV57b32ALkG5AALdrLG50Og9907009"};
        byte[] result = storageClient.download_file(uploadresult[0], uploadresult[1]);
        String local_filename = "15ef91c03bd3d3e152565b624f766749.jpg";
        writeByteToFile(result, local_filename);
        File file = new File(local_filename);
        Assert.assertTrue(file.isFile());
    }

    @Test
    public void testUploadDownload() throws Exception {
        NameValuePair[] metaList = new NameValuePair[1];
        String local_filename = "build.PNG";
        metaList[0] = new NameValuePair("fileName", local_filename);
        File file = new File("C:/Users/chengdu/Desktop/build.PNG");
        InputStream inputStream = new FileInputStream(file);
        int length = inputStream.available();
        byte[] bytes = new byte[length];
        inputStream.read(bytes);
        String[] result = storageClient.upload_file(bytes, null, metaList);
        Assert.assertTrue(storageClient.isConnected());
        // pool testOnborrow  isAvaliable
        Assert.assertTrue(storageClient.isAvaliable());
        LOGGER.info("result {}", Arrays.asList(result));
        byte[] resultbytes = storageClient.download_file(result[0], result[1]);
        writeByteToFile(resultbytes, local_filename);
        File downfile = new File(local_filename);
        Assert.assertTrue(downfile.isFile());
    }

}
