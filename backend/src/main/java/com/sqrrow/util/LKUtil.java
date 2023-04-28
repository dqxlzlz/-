package com.sqrrow.util;

import com.sqrrow.entity.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LKUtil {
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
//                设置WMC
                model.getPackagedElements().get(i).setWmc(wmc);
//                收集类的方法
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

    //深度优先搜索计算CS
    public static void countCS(UMLXML model){
        //1. 对所有的uml:Class类型进行hash，需要存储两个map一个是<string,int>，一个是<int,string>。
        //这里的String表示的是我们的id，而不是类名！
        mpToInt = new HashMap<String,Integer>();
        mpToString = new HashMap<Integer, String>();
        mpToPE = new HashMap<Integer, PackagedElement>();
        cnt = 0;    //有多少个Class类型

        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型
            if(model.getPackagedElements().get(i).getType().equals("uml:Class")){
                String id = model.getPackagedElements().get(i).getId();

                //加入<string,int>Map中
                mpToInt.put(id,cnt);
                //加入<int,string>Map中
                mpToString.put(cnt,id);
                //加入<int,packagedElemenrt>Map中
                mpToPE.put(cnt,model.getPackagedElements().get(i));
                cnt++;
            }
        }

        //2. 构建我们的邻接表,对于每一个有generalization的Class，将当前结点加入到我们的general的邻接表中
        adj = new ArrayList[cnt];
        for(int i=0;i<cnt;i++){
            adj[i] = new ArrayList<Integer>();
        }

        for(int i=0;i<model.getPackagedElements().size();i++){
            //如果是我们的Class类型，并且拥有generalization这个对象，那么我们要将这个结点加入到邻接表中
            if(model.getPackagedElements().get(i).getType().equals("uml:Class")
                    && model.getPackagedElements().get(i).getGeneralization() != null){
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
                    PackagedElement packagedElement = model.getPackagedElements().get(i);
                    int root = mpToInt.get(packagedElement.getId());
                    packagedElement.setTotalNumberOfMethod( packagedElement.getWmc());
                    packagedElement.setTotalNumberOfAttr(packagedElement.getAttrSet().size());
                    packagedElement.setNoa(packagedElement.getWmc()-packagedElement.getNoo());
                    DFS2(root);
                }
            }
        }

        //5. 遍历完毕后，先打印测试一下结果
        for(int i=0;i<cnt;i++){
            //System.out.println("当前的数字是："+i+",它的深度是："+depths[i]);
            //设置元素的depth
            mpToPE.get(i).setTotalNumberOfMethod(mpToPE.get(i).getMethodSet().size());
        }
    }

    //只需要父亲结点就可以了
    public static  void DFS2(int root){
        //如果当前的root没有孩子那么直接返回
        if(adj[root].size() == 0){
            return;
        }else{//如果有子节点，那么进行深度优先搜索
            for(int i=0;i<adj[root].size();i++){
                PackagedElement packagedElement = mpToPE.get(adj[root].get(i));

                HashSet<String> tmp = new HashSet<String>();
                tmp.addAll(packagedElement.getMethodSet());
                tmp.retainAll(mpToPE.get(root).getMethodSet());
                packagedElement.setNoo(tmp.size());
                packagedElement.setNoa(packagedElement.getWmc()-packagedElement.getNoo());

                packagedElement.getMethodSet().addAll(mpToPE.get(root).getMethodSet());
                packagedElement.getAttrSet().addAll(mpToPE.get(root).getAttrSet());
                packagedElement.setTotalNumberOfAttr(packagedElement.getAttrSet().size());
                DFS2(adj[root].get(i));
            }
        }
    }

    //计算特征化指数
    public static void computeSi(UMLXML model){
        //1. 得到Class集合
        HashMap<String,PackagedElement> classMap = model.getClassMap();

        //2. 遍历每一个Class
        for(String key : classMap.keySet()) {
            PackagedElement cls = classMap.get(key);

            //根据特征化指数的定义进行计算
            double si = (cls.getNoa()*cls.getDepth())/(double)cls.getTotalNumberOfMethod();
            cls.setSi(si);
        }
    }
}
