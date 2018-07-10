package com.example.maguilar.shptes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maguilar on 02/07/2018.
 */

public class ExpandibleData {

    public HashMap<String,List<String>> getData(){
        HashMap<String, List<String>> expandibleListDetail = new HashMap<>();
        List<String> woman = new ArrayList<String>();
        woman.add("Blusas");
        woman.add("Camisas");
        woman.add("Blazers");
        woman.add("Sudaderas");
        expandibleListDetail.put("Woman",woman);
        return  expandibleListDetail;
    }
}
