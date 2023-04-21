package cn.edu.csu.smproject.Service;

import cn.edu.csu.smproject.domain.Node;
import cn.edu.csu.smproject.domain.PackagedElement;
import cn.edu.csu.smproject.domain.UMLXML;

public class VGUtil {
    /**
     * 计算圈复杂度
     * @param model
     */
    public static void computeVG(UMLXML model){
        int cnt = 0;
        for(PackagedElement packagedElement : model.getPackagedElements()){
            for(Node node : packagedElement.getNodes()){
                if (node.getType().equals("uml:DecisionNode")){
                    cnt++;
                }
            }
            packagedElement.setVg(cnt);
        }
    }
}
