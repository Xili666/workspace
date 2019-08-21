package xyz.xili.workspace.controller;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {

    public Map<String, Object> getResultMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("ok", true);
        return map;
    }
}
