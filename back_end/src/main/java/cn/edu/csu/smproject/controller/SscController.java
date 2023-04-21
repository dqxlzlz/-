package cn.edu.csu.smproject.controller;


import cn.edu.csu.smproject.Service.*;
import cn.edu.csu.smproject.domain.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class SscController {

    @PostMapping(value = "/test", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public TicketResponse test(@RequestBody TicketRequest ticketRequest){
        TicketResponse ticketResponse=new TicketResponse();
        List<OrderResponse> orders=new ArrayList<OrderResponse>();
        OrderResponse o=new OrderResponse();
        o.setMsg("投注成功");
        orders.add(o);
        ticketResponse.setOrderList(orders);
        return ticketResponse;
    }

//    @PostMapping(value = "/test2", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
//    @ResponseBody
//    public CKResponse test2(@RequestBody UMLXML model){
//        Util.statClassAndInterface(model);
//        Util.countOwnedOperations(model);
//        Util.computeDepth(model);
//        Util.computeCBO(model);
//        //统计每个类的属性
//        Util.statAttribute(model);
//        Util.computeRFC(model);
//        Util.computeLCOM(model);
//
//        for(int i=0;i<model.getPackagedElements().size();i++){
//            //如果是我们的Class类型
//            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){
//                //String id = model.getPackagedElements().get(i).getId();
//                System.out.println("对于类别："+model.getPackagedElements().get(i).getName()
//                        +",wmc是："+model.getPackagedElements().get(i).getWmc()
//                        +"，深度是："+ model.getPackagedElements().get(i).getDepth()
//                        +",子类数量是："+model.getPackagedElements().get(i).getNoc()
//                        +",CBO数量是："+model.getPackagedElements().get(i).getCbo()
//                        +",RFC数量是："+model.getPackagedElements().get(i).getRfc()
//                        +",LCOM数量是："+model.getPackagedElements().get(i).getLcom());
//            }else if(model.getPackagedElements().get(i).getType().equals("uml:Interface")){
//                System.out.println("对于接口："+model.getPackagedElements().get(i).getName()
//                        +",wmc是："+model.getPackagedElements().get(i).getWmc()
//                        +"，深度是："+ model.getPackagedElements().get(i).getDepth()
//                        +",子类数量是："+model.getPackagedElements().get(i).getNoc()
//                        +",CBO数量是："+model.getPackagedElements().get(i).getCbo()
//                        +",RFC数量是："+model.getPackagedElements().get(i).getRfc()
//                        +",LCOM数量是："+model.getPackagedElements().get(i).getLcom());
//            }
//        }
//
//        //基本的功能我们已经实现了，接下来我们来设计一下如何返回我们的xml文件
//        CKResponse ckResponse = new CKResponse();
//        List<CKResult> ckResults = new ArrayList<CKResult>();
//        for(int i=0;i<model.getPackagedElements().size();i++){
//            //如果是我们的Class类型
//            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){
//                //String id = model.getPackagedElements().get(i).getId();
//                CKResult ckResult = new CKResult();
//                ckResult.setName(model.getPackagedElements().get(i).getName());
//                ckResult.setWmc(model.getPackagedElements().get(i).getWmc());
//                ckResult.setDit(model.getPackagedElements().get(i).getDepth());
//                ckResult.setNoc(model.getPackagedElements().get(i).getNoc());
//                ckResult.setCbo(model.getPackagedElements().get(i).getCbo());
//                ckResult.setRfc(model.getPackagedElements().get(i).getRfc());
//                ckResult.setLcom(model.getPackagedElements().get(i).getLcom());
//                ckResults.add(ckResult);
//            }
//        }
//        ckResponse.setCkResultList(ckResults);
//        return ckResponse;
//    }

    @PostMapping(value = "/CKMetrics", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CKResponse CKMetrics(@RequestBody UMLXML model){
        CKUtil.statClassAndInterface(model);
        CKUtil.countOwnedOperations(model);
        CKUtil.computeDepth(model);
        CKUtil.computeCBO(model);
        //统计每个类的属性
        CKUtil.statAttribute(model);
        CKUtil.computeRFC(model);
        CKUtil.computeLCOM(model);

        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型
            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){
                //String id = model.getPackagedElements().get(i).getId();
                System.out.println("对于类别："+model.getPackagedElements().get(i).getName()
                        +",wmc是："+model.getPackagedElements().get(i).getWmc()
                        +"，深度是："+ model.getPackagedElements().get(i).getDepth()
                        +",子类数量是："+model.getPackagedElements().get(i).getNoc()
                        +",CBO数量是："+model.getPackagedElements().get(i).getCbo()
                        +",RFC数量是："+model.getPackagedElements().get(i).getRfc()
                        +",LCOM数量是："+model.getPackagedElements().get(i).getLcom());
            }else if(model.getPackagedElements().get(i).getType().equals("uml:Interface")){
                System.out.println("对于接口："+model.getPackagedElements().get(i).getName()
                        +",wmc是："+model.getPackagedElements().get(i).getWmc()
                        +"，深度是："+ model.getPackagedElements().get(i).getDepth()
                        +",子类数量是："+model.getPackagedElements().get(i).getNoc()
                        +",CBO数量是："+model.getPackagedElements().get(i).getCbo()
                        +",RFC数量是："+model.getPackagedElements().get(i).getRfc()
                        +",LCOM数量是："+model.getPackagedElements().get(i).getLcom());
            }
        }

        //基本的功能我们已经实现了，接下来我们来设计一下如何返回我们的xml文件
        CKResponse ckResponse = new CKResponse();
        List<CKResult> ckResults = new ArrayList<CKResult>();
        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型
            if(model.getPackagedElements().get(i).getType().equals("uml:Class") || model.getPackagedElements().get(i).getType().equals("uml:Interface") ){
                //String id = model.getPackagedElements().get(i).getId();
                CKResult ckResult = new CKResult();
                ckResult.setName(model.getPackagedElements().get(i).getName());
                ckResult.setWmc(model.getPackagedElements().get(i).getWmc());
                ckResult.setDit(model.getPackagedElements().get(i).getDepth());
                ckResult.setNoc(model.getPackagedElements().get(i).getNoc());
                ckResult.setCbo(model.getPackagedElements().get(i).getCbo());
                ckResult.setRfc(model.getPackagedElements().get(i).getRfc());
                ckResult.setLcom(model.getPackagedElements().get(i).getLcom());
                ckResults.add(ckResult);
            }
        }
        ckResponse.setCkResultList(ckResults);
        return ckResponse;
    }



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

    @PostMapping(value = "/VGMetrics", consumes = { MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public int VGMetrics(@RequestBody UMLXML model){
        int vg = 0;
        VGUtil.computeVG(model);
        for(PackagedElement packagedElement : model.getPackagedElements()){
            System.out.println(packagedElement.getVg());
            vg = packagedElement.getVg();
        }
        return vg;
    }

    @PostMapping(value = "/countCode")
    public ArrayList<Code> countCode(String path){
        path = "G:\\大三下\\软件度量及应用\\实验\\testcode";
        CodeCounterUtil codeCounterUtil = new CodeCounterUtil();
        return codeCounterUtil.countCode(path);
    }

    @PostMapping(value = "/UCPMetrics", consumes = { MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public UCPResponse UCPMetrics(@RequestBody UMLXML model){
        UCPResponse ucpResponse = new UCPResponse();
        ucpResponse.setActors(UCPUtil.statActor(model));
        ucpResponse.setUsecases(UCPUtil.statUseCase(model));
        return ucpResponse;
    }

//    @PostMapping(value="/FPMetrics",consumes = { MediaType.APPLICATION_XML_VALUE })
//    @ResponseBody
//    public FPResponse FPMetrics(@RequestBody DFXML model){
//        FPResponse fpResponse = new FPResponse();
//
//        //测试Process
//        ArrayList<Process> processArrayList = model.getRootObject().getChildren().getModel().getProcessArrayList();
//        for(Process process:processArrayList){
//            System.out.println(process.getName());
//        }
//        fpResponse.setProcessArrayList(processArrayList);
//
//        //测试Flows
//        ArrayList<Flow> flowArrayList = model.getRootObject().getChildren().getModel().getFlowArrayList();
//        for(Flow flow:flowArrayList){
//            if(flow.getObject1().getProcess()!=null){
//                System.out.println(flow.getObject1().getProcess().getRef());
//            }
//        }
//        fpResponse.setFlowArrayList(flowArrayList);
//
//        //测试用户
//        ArrayList<OrganizationUnit> organizationUnitArrayList = model.getRootObject().getChildren().getModel().getOrganizationUnitArrayList();
//        for(OrganizationUnit organizationUnit:organizationUnitArrayList){
//            System.out.println(organizationUnit.getName());
//        }
//        fpResponse.setOrganizationUnitArrayList(organizationUnitArrayList);
//
//        //测试数据存储
//        ArrayList<Resource> resourceArrayList = model.getRootObject().getChildren().getModel().getResourceArrayList();
//        for(Resource resource:resourceArrayList){
//            System.out.println(resource.getName());
//        }
//        fpResponse.setResourceArrayList(resourceArrayList);
//
//        return fpResponse;
//    }
}
