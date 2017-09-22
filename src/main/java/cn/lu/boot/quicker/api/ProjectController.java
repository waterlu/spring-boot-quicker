package cn.lu.boot.quicker.api;

import cn.lu.boot.quicker.dto.ProjectInfoDTO;
import cn.lu.boot.quicker.entity.ProjectDependecy;
import com.alibaba.fastjson.JSON;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Created by lutiehua on 2017/9/22.
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    String preview(@RequestBody ProjectInfoDTO projectInfoDTO) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
        cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template pom = cfg.getTemplate("pom.ftl");
        Writer out = new OutputStreamWriter(System.out);
        pom.process(projectInfoDTO, out);
        return "ok";
    }

}