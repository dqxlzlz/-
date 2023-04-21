package org.csu.metrics.web;

import cn.edu.csu.smproject.Service.LKUtil;
import cn.edu.csu.smproject.domain.LKResponse;
import cn.edu.csu.smproject.domain.LKResult;
import cn.edu.csu.smproject.domain.PackagedElement;
import cn.edu.csu.smproject.domain.UMLXML;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LKXml {
    @PostMapping(value = "/LKMetrics", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public LKResponse LKMetrics(@RequestBody UMLXML model){
        //1. 存储映射<类名,类>,<接口名，接口>
        LKUtil.statClassAndInterface(model);

        //2. 记录一个类有多少直接的方法，即不包含父类的方法数
        LKUtil.countOwnedOperations(model);

        //3. 记录一个类有多少直接的属性，即不包含父类的属性
        LKUtil.statAttribute(model);

        //4. 进行深度优先搜索，得到CS
        LKUtil.countCS(model);

        //5. 计算继承的深度
        LKUtil.computeDepth(model);

        //6. 计算特征化指数
        LKUtil.computeSi(model);

        LKResponse lkResponse = new LKResponse();
        List<LKResult> lkResults = new ArrayList<LKResult>();
        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型
            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){
                PackagedElement packagedElement = model.getPackagedElements().get(i);
                LKResult lkResult = new LKResult();
                lkResult.setName(packagedElement.getName());
                lkResult.setTotalNumberOfMethod(packagedElement.getTotalNumberOfMethod());
                lkResult.setTotalNumberOfAttr(packagedElement.getTotalNumberOfAttr());
                lkResult.setNoo(packagedElement.getNoo());
                lkResult.setNoa(packagedElement.getNoa());
                lkResult.setSi(packagedElement.getSi());
                lkResults.add(lkResult);
            }
        }
        lkResponse.setLkResultList(lkResults);
        return lkResponse;
    }
}
