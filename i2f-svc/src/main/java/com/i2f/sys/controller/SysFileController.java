package com.i2f.sys.controller;

import i2f.core.check.CheckUtil;
import i2f.core.codec.CodecUtil;
import i2f.core.digest.CodeUtil;
import i2f.core.exception.BoostException;
import i2f.core.io.file.FileUtil;
import i2f.core.io.file.core.FileMime;
import i2f.core.j2ee.web.ServletFileUtil;
import i2f.core.security.file.FileAccessFirewall;
import i2f.core.security.file.FileUploadDownloadGuarder;
import i2f.core.security.jce.digest.MessageDigestUtil;
import i2f.core.security.jce.digest.sha.ShaType;
import i2f.core.std.api.ApiResp;
import i2f.springboot.secure.util.SecureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ice2Faith
 * @date 2023/7/13 13:48
 * @desc
 */
@Slf4j
@RestController
@RequestMapping(SysFileController.BASE_PATH)
public class SysFileController {
    public static final String BASE_PATH ="/api/sys/file";
    public static final String DOWNLOAD_PATH="/download";
    public static final String BASE_DOWNLOAD_PATH= BASE_PATH +DOWNLOAD_PATH;
    public static final String DOWNLOAD_PATH_PATTEN=DOWNLOAD_PATH+"/**";

    public static final String HMAC_KEY="念";

    public static final String STORE_PATH="../store";

    @Value("${i2f.springboot.file.downloadHost:localhost}")
    private String downloadHost="localhost";

    @Autowired
    private Environment environment;

    public void saveFile(InputStream is,String signName) throws IOException {
        File storeDir = new File(STORE_PATH);
        File saveFile=new File(storeDir,signName);
        boolean ok = FileAccessFirewall.isSafePath(storeDir.getAbsolutePath(), saveFile.getAbsolutePath());
        if(!ok){
            throw new BoostException("非法访问");
        }
        FileUtil.useParentDir(saveFile);
        ok = FileUploadDownloadGuarder.DEFAULT_INSTANCE.upload(is, storeDir, saveFile);
        if(!ok){
            throw new BoostException("文件上传失败");
        }
    }

    public void loadFile(String signName,OutputStream os) throws IOException {
        File storeDir = new File(STORE_PATH);
        File saveFile=new File(storeDir,signName);
        boolean ok = FileAccessFirewall.isSafePath(storeDir.getAbsolutePath(), saveFile.getAbsolutePath());
        if(!ok){
            throw new BoostException("非法访问");
        }
        FileUtil.useParentDir(saveFile);
        ok = FileUploadDownloadGuarder.DEFAULT_INSTANCE.download( storeDir, saveFile,os);
        if(!ok){
            throw new BoostException("文件下载失败");
        }
    }

    public String getDownloadUrl(String signName,HttpServletRequest request){
        String port=environment.getProperty("server.port");
        String ret="http://"+downloadHost+":"+port;
        String contextPath=environment.getProperty("server.servlet.context-path");
        if(CheckUtil.isEmptyStr(contextPath)){
            contextPath=request.getContextPath();
        }
        if(CheckUtil.isEmptyStr(contextPath)){
            contextPath="/";
        }
        ret= SecureUtils.combinePath(ret,contextPath,"/");
        ret=SecureUtils.combinePath(ret,BASE_DOWNLOAD_PATH, "/");
        ret=SecureUtils.combinePath(ret,signName,"/");
        return ret;

    }

    public String trimSignNameFromUri(HttpServletRequest request){
        String contextPath=environment.getProperty("server.servlet.context-path");
        if(CheckUtil.isEmptyStr(contextPath)){
            contextPath=request.getContextPath();
        }
        if(CheckUtil.isEmptyStr(contextPath)){
            contextPath="/";
        }
        String baseUri= SecureUtils.combinePath(contextPath,BASE_DOWNLOAD_PATH,"/");
        if(!baseUri.endsWith("/")){
            baseUri=baseUri+"/";
        }
        String requestURI = request.getRequestURI();
        int idx = requestURI.lastIndexOf(baseUri);
        if(idx<0){
            return null;
        }
        String signName=requestURI.substring(idx+baseUri.length());
        return signName;
    }

    @RequestMapping("upload")
    public ApiResp<?> upload(MultipartFile file,HttpServletRequest request) throws Exception{
        String uuid= CodeUtil.makeUUID();
        String fileName = file.getOriginalFilename();
        if(CheckUtil.isEmptyStr(fileName)){
            fileName="upload.data";
        }
        String suffix = FileUtil.getSuffix(fileName);
        String nameOnly = FileUtil.getNameOnly(fileName);

        String baseName=nameOnly+"-"+uuid+suffix;
        String sign= MessageDigestUtil.hmac(ShaType.SHA256, CodecUtil.toUtf8(HMAC_KEY)).mdsText(CodecUtil.toUtf8(baseName));

        String signName=sign+"-"+baseName;

        saveFile(file.getInputStream(),signName);

        String url = getDownloadUrl(signName,request);

        Map<String,Object> ret=new HashMap<>();
        ret.put("url",url);
        ret.put("filename",signName);

        return ApiResp.success(ret);
    }

    @RequestMapping("/download/{signName:.+}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("signName")String signName) throws Exception{

        if(CheckUtil.isEmptyStr(signName)){
            ServletFileUtil.responseNotFileFound(signName,response);
        }

        String[] arr = signName.split("-", 2);
        if(arr.length!=2){
            ServletFileUtil.responseNotFileFound(signName,response);
        }

        String sign=arr[0];
        String baseName=arr[1];

        String verifySign= MessageDigestUtil.hmac(ShaType.SHA256, CodecUtil.toUtf8(HMAC_KEY)).mdsText(CodecUtil.toUtf8(baseName));

        if(!verifySign.equals(sign)){
            ServletFileUtil.responseNotFileFound(signName,response);
        }


        responseAsFileAttachment((os)->{
            loadFile(signName,os);
        },signName,null,response);
    }

    @FunctionalInterface
    public interface OutputStreamWriter{
        void write(OutputStream os) throws IOException;
    }

    public static void responseAsFileAttachment(OutputStreamWriter writer, String virtualFileName, String mimeType, HttpServletResponse response) throws IOException {
        if (null != mimeType) {
            response.setContentType(mimeType + ";charset=UTF-8");
        } else {
            response.setContentType(FileMime.getMimeType(virtualFileName) + ";charset=UTF-8");
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(virtualFileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        OutputStream os = response.getOutputStream();
        writer.write(os);
        os.close();

    }


}
