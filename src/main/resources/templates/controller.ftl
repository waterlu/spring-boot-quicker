package ${classPackage};

<#list imports as import>
import ${import.name};
</#list>
import cn.zjhf.kingold.common.constant.ResponseCode;
import cn.zjhf.kingold.common.exception.BusinessException;
import cn.zjhf.kingold.common.result.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* ${classRemark}接口
*
* Create by ${author} on ${date}
*
*/
@RestController
@RequestMapping(value = "/{className?lower_case}")
public class ${className}Controller {

    private final static Logger LOGGER = LoggerFactory.getLogger(${className}Controller.class);

    @Autowired
    private ${className?cap_first}Service ${className?lower_case}Service;

    /**
     * 创建${classRemark}
     *
     * @param rechargeOrderVO
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseResult create${className?cap_first}(@Valid @RequestBody RechargeOrderVO rechargeOrderVO) throws BusinessException {

    }

}