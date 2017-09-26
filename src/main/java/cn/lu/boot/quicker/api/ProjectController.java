package cn.lu.boot.quicker.api;

import cn.lu.boot.quicker.common.ResponseResult;
import cn.lu.boot.quicker.core.GeneratedFile;
import cn.lu.boot.quicker.core.GeneratedJavaEntityClass;
import cn.lu.boot.quicker.dto.GeneratedInfoDTO;
import cn.lu.boot.quicker.dto.TableDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lutiehua on 2017/9/22.
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public ResponseResult preview(@RequestBody GeneratedInfoDTO projectInfoDTO) throws Exception {
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
    public ResponseResult generate(@RequestBody GeneratedInfoDTO projectInfoDTO) throws Exception {

        // 复制属性
        projectInfoDTO.getPackageInfo().setProjectName(projectInfoDTO.getProjectInfo().getName());

        // 需要生成的文件列表
        List<GeneratedFile> generatedFileList = new ArrayList<>();

        // 实体类
        List<TableDTO> tableList = projectInfoDTO.getTables();
        if (null != tableList) {
            for (TableDTO tableDTO : tableList) {
                GeneratedJavaEntityClass javaEntityClass = new GeneratedJavaEntityClass(
                        projectInfoDTO.getDatabaseInfo(), tableDTO, projectInfoDTO.getPackageInfo());
                generatedFileList.add(javaEntityClass);
            }
        }

        // POM文件

        // 解析数据
        for (GeneratedFile generatedFile : generatedFileList) {
            generatedFile.parse();
        }

        // 生成文件
        for (GeneratedFile generatedFile : generatedFileList) {
            generatedFile.createFile();
        }

//        String fileName = generatePom(projectInfoDTO);

        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseResult.OK);
        responseResult.setData("ok");
        return responseResult;
    }

    private String generatePom(GeneratedInfoDTO projectInfoDTO) throws Exception {
        Template template = getTemplate("pom.ftl");
        String projectDir = String.format("/tmp/%s/", projectInfoDTO.getProjectInfo().getName());
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

        return pomFile;
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