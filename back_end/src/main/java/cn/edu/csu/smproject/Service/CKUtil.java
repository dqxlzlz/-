package cn.edu.csu.smproject.Service;

import cn.edu.csu.smproject.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CKUtil {
    private static HashMap<String,Integer> mpToInt;
    private static HashMap<Integer,String> mpToString;
    private static HashMap<Integer, PackagedElement> mpToPE;
    //建立id到对象的映射
    private static HashMap<String,PackagedElement> mpIdToPE;
    //建立id到cbo数量的映射
    private static HashMap<String,Integer> mpIdToCbo;
    private static int cnt;
    private static ArrayList<Integer>[] adj;
    private static int[] depths;

    public static void countOwnedOperations(UMLXML model){
        //对于model里面的每一个PackagedElement进行操作
        for(int i=0;i<model.getPackagedElements().size();i++){
            PackagedElement packagedElement = model.getPackagedElements().get(i);
            if(packagedElement.getOwnedOperations()!= null ){
                int wmc = packagedElement.getOwnedOperations().size();
                model.getPackagedElements().get(i).setWmc(wmc);
                for(int j=0;j<wmc;j++){
                    packagedElement.getMethodSet().add(packagedElement.getOwnedOperations().get(j).getName());
                }

            }else{  //表示没有方法
                model.getPackagedElements().get(i).setWmc(0);
            }
        }
    }

    public static void computeDepth(UMLXML model){
        //1. 对所有的uml:Class类型进行hash，需要存储两个map一个是<string,int>，一个是<int,string>
        mpToInt = new HashMap<String,Integer>();
        mpToString = new HashMap<Integer, String>();
        mpToPE = new HashMap<Integer, PackagedElement>();
        cnt = 0;    //有多少个Class类型

        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型
            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){

                String id = model.getPackagedElements().get(i).getId();
                mpToInt.put(id,cnt);
                mpToString.put(cnt,id);
                mpToPE.put(cnt,model.getPackagedElements().get(i));
                cnt++;
            }
        }

        //2. 构建我们的邻接表,对于每一个有generalization的Class，将当前结点加入到我们的general的邻接表中
        adj = new ArrayList[cnt];
        for(int i=0;i<cnt;i++){
            adj[i] = new ArrayList<Integer>();
        }
        depths = new int[cnt];

        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型，并且拥有generalization这个对象，那么我们要将这个结点加入到邻接表中
            if(model.getPackagedElements().get(i).getType().equals("uml:Class") && model.getPackagedElements().get(i).getGeneralization() != null){
                int father = mpToInt.get(model.getPackagedElements().get(i).getGeneralization().getGeneral());
                int child = mpToInt.get(model.getPackagedElements().get(i).getId());

                if(adj[father] == null){
                    System.out.println("True");
                }
                adj[father].add(child);
            }
        }

        //3. 根据邻接表，记录直接子类的数量
        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型
            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){
                //如果当前结点没有父亲结点，说明它是根，那么对它进行深度优先搜索
                int cur = mpToInt.get(model.getPackagedElements().get(i).getId());
                int noc = adj[cur].size();
                mpToPE.get(cur).setNoc(noc);
            }
        }

        //4. 上面已经构建了邻接表，下面对于每一个结点没有父亲的class进行深度优先搜索
        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型
            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){
                //如果当前结点没有父亲结点，说明它是根，那么对它进行深度优先搜索
                if(model.getPackagedElements().get(i).getGeneralization()== null){
                    int root = mpToInt.get(model.getPackagedElements().get(i).getId());
                    int depth = 0;
                    DFS(root,depth);
                }
            }
        }

        //5. 遍历完毕后，先打印测试一下结果
        for(int i=0;i<cnt;i++){
            //System.out.println("当前的数字是："+i+",它的深度是："+depths[i]);
            //设置元素的depth
            mpToPE.get(i).setDepth(depths[i]);
        }
    }

    public static  void DFS(int root,int depth){
        depths[root] = depth;

        //如果当前的root没有孩子那么直接返回
        if(adj[root].size() == 0){
            return;
        }else{//如果有子节点，那么进行深度优先搜索
            for(int i=0;i<adj[root].size();i++){
                DFS(adj[root].get(i),depth+1);
            }
        }
    }

    //将所有的关系，比如依赖关系，关联关系，实现关系，聚合关系，组合关系分别放到一个集合之中，便于统一管理
    //首先，将关联关系放到同一个集合里面
//    public static void gatherAssociation(UMLXML model){
//        //对于model里面的每一个PackagedElement进行操作,判断它们是不是关联关系
//        for(int i=0;i<model.getPackagedElements().size();i++){
//            if(model.getPackagedElements().get(i).getOwnedOperations()!= null ){
//                model.getPackagedElements().get(i).setWmc(model.getPackagedElements().get(i).getOwnedOperations().size());
//                //System.out.println("这个PackedElement："+model.getPackagedElements().get(i).getName()+"的方法数为："+model.getPackagedElements().get(i).getOwnedOperations().size());
//            }else{  //表示没有方法
//                model.getPackagedElements().get(i).setWmc(0);
//            }
//        }
//    }

    public static void computeCBO(UMLXML model){
        mpIdToPE = new HashMap<String,PackagedElement> ();
        mpIdToCbo = new HashMap<String,Integer>();

        //存储创建id到cbo和object的映射
        for(int i=0;i<model.getPackagedElements().size();i++){
            String id = model.getPackagedElements().get(i).getId();
            mpIdToPE.put(id,model.getPackagedElements().get(i));
            mpIdToCbo.put(id,0);
        }

        //对于model里面的每一个PackagedElement进行操作
        for(int i=0;i<model.getPackagedElements().size();i++){

            //如果是关联关系
            if(model.getPackagedElements().get(i).getType().equals("uml:Association")){
                //那么需要遍历其所有的ownedEnd
                for(int j=0;j<model.getPackagedElements().get(i).getOwnedEnds().size();j++){
                    String id = model.getPackagedElements().get(i).getOwnedEnds().get(j).getType();
                    mpIdToCbo.put(id,mpIdToCbo.get(id)+1);
                }
            }else if(model.getPackagedElements().get(i).getType().equals("uml:Dependency")){
                String supplier = model.getPackagedElements().get(i).getSupplier();
                String client = model.getPackagedElements().get(i).getClient();

                mpIdToCbo.put(supplier,mpIdToCbo.get(supplier)+1);
                mpIdToCbo.put(client,mpIdToCbo.get(client)+1);
            }else if(model.getPackagedElements().get(i).getType().equals("uml:InterfaceRealization")){
                String supplier = model.getPackagedElements().get(i).getSupplier();
                String client = model.getPackagedElements().get(i).getClient();

                mpIdToCbo.put(supplier,mpIdToCbo.get(supplier)+1);
                mpIdToCbo.put(client,mpIdToCbo.get(client)+1);
            }
        }

        //将cbo写入到各个类中
        for (String key : mpIdToCbo.keySet()) {
            mpIdToPE.get(key).setCbo(mpIdToCbo.get(key));
        }
    }

    /**
     * 统计一个UML类图里面有哪些类，有哪些接口。
     * @param model
     */
    public static void statClassAndInterface(UMLXML model){
        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型
            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){
                //获得这个类的id，并且加入到我们的HashMap中
                String id = model.getPackagedElements().get(i).getId();
                model.getClassMap().put(id,model.getPackagedElements().get(i));
            }else if(model.getPackagedElements().get(i).getType().equals("uml:Interface")){
                //获得这个接口的名字，并且加入到我们的HashMap中
                String id = model.getPackagedElements().get(i).getId();
                model.getInterfaceMap().put(id,model.getPackagedElements().get(i));
            }
        }
    }

    /**
     * 记录每个Class中的属性
     * @param model
     */
    public static void statAttribute(UMLXML model){
        HashMap<String,PackagedElement> classMap = model.getClassMap();

        for(String key:classMap.keySet()){
            PackagedElement cls = classMap.get(key);

            if(cls.getOwnedAttributes()!=null){
                for(OwnedAttribute ownedAttribute : cls.getOwnedAttributes()){
                    cls.getAttrMap().put(ownedAttribute.getName(),ownedAttribute);
                    cls.getAttrSet().add(ownedAttribute.getName());
                }
            }
        }
    }

    /**
     * 计算一个UML类图里面的每个类“Class”的rfc大小
     * @param model
     */
    public static void computeRFC(UMLXML model){
        HashMap<String,PackagedElement> classMap = model.getClassMap();

        for(String key:classMap.keySet()){
            PackagedElement cls = classMap.get(key);

            if(cls.getOwnedOperations() != null){
                //对于每一个方法
                for(OwnedOperation ownedOperation : cls.getOwnedOperations()){
                    for(OwnedRule ownedRule : ownedOperation.getOwnedRules()){
                        String code = ownedRule.getSpecification().getValue();
                        //System.out.println(code);

                        //要对这个code进行正则表达式的匹配，匹配的规则是：.* \.  (.*)
                        //String text = "student.selectCourse() teacher.teachCourse()";
                        String patternString1 ="([A-Za-z0-9]*\\.[A-Za-z0-9]*)";
                        Pattern pattern = Pattern.compile(patternString1);
                        Matcher matcher = pattern.matcher(code);

                        while(matcher.find()){
                            //进行字符串的分割
                            String caller = matcher.group(1).split("\\.")[0];
                            String method = matcher.group(1).split("\\.")[1];
                            //System.out.println("caller："+caller+" method："+method);

                            //判断这个caller是属性还是类，我们只针对这两种情况，现在我没考虑类
                            if(cls.getAttrMap().containsKey(caller)){//遍历所有的属性集合,查看这个caller是否是属性
                                //包含这个属性
                                //那么得到这个属性的类别
                                String type = cls.getAttrMap().get(caller).getType();
                                //在这个类的rfcMap中放入我们的这个id和属性名
                                cls.getRfcMap().put(type+"____"+method,1);
//                                for(String attrkey : cls.getRfcMap().keySet()){
//                                    System.out.println("方法是："+attrkey);
//                                }
                                //System.out.println("Yes");
                            }
                        }
                    }
                }
            }

            //rfc = 自身的方法数(wmc) + 调用的其他的不是本类的方法数(rfcMap.size())
            cls.setRfc(cls.getWmc()+cls.getRfcMap().size());
        }




    }

    /**
     * 计算一个UML类图里面的每个类“Class”的lcom大小
     * @param model
     */
    public static void computeLCOM(UMLXML model){
        //1. 得到Class集合
        HashMap<String,PackagedElement> classMap = model.getClassMap();

        //2. 遍历每一个Class
        for(String key : classMap.keySet()){
            PackagedElement cls = classMap.get(key);

            //如果Class有方法，那么遍历里面的每一个方法，得到每个方法使用的属性集合
            if(cls.getOwnedOperations() != null){
                //对于每一个方法
                for(OwnedOperation ownedOperation : cls.getOwnedOperations()){
                    for(OwnedRule ownedRule : ownedOperation.getOwnedRules()){
                        //得到方法里面的代码
                        String code = ownedRule.getSpecification().getValue();
                        //System.out.println(code);

                        //使用cls的每一个attribute去进行正则匹配code，如果匹配成功，那么将这个attribute加入到这个方法的属性集合中
                        for(String attr : cls.getAttrMap().keySet()){
                            if(code.indexOf(attr) > 0){
                                //如果匹配成功，那么将这个属性加入到方法的属性集合中
                                ownedOperation.getAttrSet().add(attr);
                            }

                        }
                    }

                    if(ownedOperation.getAttrSet()!=null){
                        //打印输出看一下结果
                        //System.out.println("-----------------------------------------------------------------");
                        //System.out.println("对于类："+key);
                        //System.out.println("方法："+ownedOperation.getName()+"，它拥有的使用到的属性后一下：");
//                        for(String attr : ownedOperation.getAttrSet()){
//                            System.out.println(attr);
//                        }
                    }
                }

                //对于每一个方法的attrSet，来求P和Q

                int p = 0;
                int q = 0;
                for(OwnedOperation operationA : cls.getOwnedOperations()){
                    for(OwnedOperation operationB : cls.getOwnedOperations()){
                        if(operationA != operationB){   //如果不是同一个方法对象
                            HashSet<String> result = new HashSet<String>();
                            result.clear();
                            result.addAll(operationA.getAttrSet());
                            result.retainAll(operationB.getAttrSet());
                            if(!result.isEmpty()){  //如果不为空代表有交集
                                //System.out.println(result);
                                q++;
                                //System.out.println("q："+q);
                            }else{
                                p++;
                                //System.out.println("p："+p);
                            }
                        }
                    }
                }
                //打印一下结果
                //System.out.println("计算得到lcom是"+(p-q)/2);
                cls.setLcom((p-q)/2);
            }
        }
    }

}
