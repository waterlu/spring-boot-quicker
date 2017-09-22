package cn.lu.boot.quicker.api;

import cn.lu.boot.quicker.common.ResponseResult;
import cn.lu.boot.quicker.dto.ProjectInfoDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by lutiehua on 2017/9/22.
 */
@RestController
//@Controller
@RequestMapping("/api/project")
public class ProjectController {

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public ResponseResult preview(@RequestBody ProjectInfoDTO projectInfoDTO) throws Exception {
        Template template = getTemplate("pom.ftl");
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        template.process(projectInfoDTO, writer);
        writer.flush();
        writer.close();

        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseResult.OK);
        responseResult.setData(stringWriter.toString());
        return responseResult;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody ProjectInfoDTO projectInfoDTO) throws Exception {
        Template template = getTemplate("pom.ftl");
        String projectDir = String.format("/tmp/%s/", projectInfoDTO.getName());
        String fileName = "pom.xml";
        String pomFile = projectDir + fileName;

        File dir = new File(projectDir);
        if (dir.exists()) {
            dir.delete();
        }
        dir.mkdir();

        File file = new File(pomFile);
        file.createNewFile();

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        template.process(projectInfoDTO, writer);
        writer.flush();
        writer.close();

        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseResult.OK);
        responseResult.setData(fileName);
        return responseResult;
    }

//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public void save(@RequestBody ProjectInfoDTO projectInfoDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Template template = getTemplate("pom.ftl");
//        String projectDir = String.format("/tmp/%s/", projectInfoDTO.getName());
//        String fileName = "pom.xml";
//        String pomFile = projectDir + fileName;
//
//        File dir = new File(projectDir);
//        if (dir.exists()) {
//            dir.delete();
//        }
//        dir.mkdir();
//
//        File file = new File(pomFile);
//        file.createNewFile();
//
//        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
//        template.process(projectInfoDTO, writer);
//        writer.flush();
//        writer.close();
//
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        response.setContentLength((int) file.length());
//
//        InputStream inputStream = new FileInputStream(file);
//        IOUtils.copy(inputStream, response.getOutputStream());
//        response.flushBuffer();
//    }

    private Template getTemplate(String name) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
        cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = cfg.getTemplate(name);
        return template;
    }
}