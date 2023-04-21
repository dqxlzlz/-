package cn.edu.csu.smproject.Service;

import cn.edu.csu.smproject.domain.PackagedElement;
import cn.edu.csu.smproject.domain.UMLXML;

import java.util.ArrayList;

public class UCPUtil {
    /**
     * 记录有多少的用例
     * @param model
     * @return
     */
    public static ArrayList<String> statUseCase(UMLXML model){
        ArrayList<String> usecases = new ArrayList<String>();

        for(int i=0;i<model.getPackagedElements().size();i++){
            PackagedElement packagedElement = model.getPackagedElements().get(i);
            if(packagedElement.getType().equals("uml:UseCase")){
                usecases.add(packagedElement.getName());
            }
        }

        return usecases;
    }


    public static ArrayList<String> statActor(UMLXML model){
        ArrayList<String> actors = new ArrayList<String>();

        for(int i=0;i<model.getPackagedElements().size();i++){
            PackagedElement packagedElement = model.getPackagedElements().get(i);
            if(packagedElement.getType().equals("uml:Actor")){
                actors.add(packagedElement.getName());
            }
        }

        return actors;
    }
}
