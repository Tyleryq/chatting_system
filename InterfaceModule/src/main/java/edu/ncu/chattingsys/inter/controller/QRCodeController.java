package edu.ncu.chattingsys.inter.controller;

import edu.ncu.chattingsys.inter.service.FriendService;
import edu.ncu.chattingsys.inter.utils.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
public class QRCodeController {
    @Autowired
    private FriendService friendService;
    @RequestMapping(value = "/createUserQRCode")
    public void createCommonQRCode(HttpServletResponse response, String uid) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            //使用工具类生成二维码
            QRCodeUtil.encode(uid, stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    @RequestMapping(value = "/decodeQRCode")
    public void decodeQRCode(@RequestParam(value = "file") MultipartFile file,String uid) throws Exception {
        String[] fileName=file.getOriginalFilename().split(".");
        File jfile=File.createTempFile(uid,"png");
        file.transferTo(jfile);
        String fid = QRCodeUtil.decode(jfile);
        log.info(fid);
        jfile.deleteOnExit();
        friendService.addFriend(uid,fid);
    }
}
