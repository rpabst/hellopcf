package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    @Value("${port:NOT SET}")
    public String PORT;
    @Value("${memory.limit:NOT SET}")
    public String MEMORY_LIMIT;
    @Value("${cf.isntance.index:NOT SET}")
    public String CF_INSTANCE_INDEX;
    @Value("${cf.instance.addr:NOT SET}")
    public String CF_INSTANCE_ADDR;


    public EnvController()
    {

    }

    public EnvController(String PORT, String MEMORY_LIMIT, String CF_INSTANCE_INDEX, String CF_INSTANCE_ADDR) {
        this.PORT = PORT;
        this.MEMORY_LIMIT = MEMORY_LIMIT;
        this.CF_INSTANCE_INDEX = CF_INSTANCE_INDEX;
        this.CF_INSTANCE_ADDR = CF_INSTANCE_ADDR;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> envVariables = new HashMap();
        envVariables.put("CF_INSTANCE_ADDR", CF_INSTANCE_ADDR);
        envVariables.put("CF_INSTANCE_INDEX", CF_INSTANCE_INDEX);
        envVariables.put("MEMORY_LIMIT", MEMORY_LIMIT);
        envVariables.put("PORT", PORT);

        return envVariables;
    }
}