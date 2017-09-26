package cn.lu.boot.quicker.core;

import cn.lu.boot.quicker.core.model.DataModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by lutiehua on 2017/9/26.
 */
public abstract class GeneratedFile {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

//    /**
//     * 模板
//     */
//    protected Template template;
//
//    /**
//     * 文件名称
//     */
//    protected String fileName;
//
//    /**
//     * 文件内容
//     */
//    protected String content;

    /**
     * 模板
     *
     * @return
     */
    public abstract String getTemplateName();

    /**
     * 变量数据
     *
     * @return
     */
    public abstract DataModel getDataModel();

    /**
     * 文件名称
     *
     * @return
     */
    public abstract String getFileName();

    /**
     * 解析
     *
     * @return
     */
    public abstract boolean parse() throws Exception;

    /**
     * 生成文件
     *
     * @return
     * @throws Exception
     */
    public boolean createFile() throws Exception {

        // 读取模板
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
        cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = cfg.getTemplate(this.getTemplateName());

        // 创建空文件
        String fileName = this.getFileName();
        int pos = fileName.lastIndexOf("/");
        String fileDir = fileName.substring(0, pos + 1);

        File dir = new File(fileDir);
        if (dir.exists()) {
            dir.delete();
        }
        boolean flag = dir.mkdirs();

        File file = new File(fileName);
        file.createNewFile();

        // 根据模板生成文件
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        template.process(this.getDataModel(), writer);
        writer.flush();
        writer.close();

        return true;
    }

}
