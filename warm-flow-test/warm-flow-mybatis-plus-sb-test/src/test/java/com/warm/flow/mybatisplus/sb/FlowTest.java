package com.warm.flow.mybatisplus.sb;


import com.warm.flow.core.dto.FlowParams;
import com.warm.flow.core.entity.Definition;
import com.warm.flow.core.entity.Instance;
import com.warm.flow.core.enums.SkipType;
import com.warm.flow.core.service.DefService;
import com.warm.flow.core.service.InsService;
import com.warm.flow.core.service.TaskService;
import com.warm.flow.core.utils.page.Page;
import com.warm.flow.orm.dao.WarmDaoImpl;
import com.warm.flow.orm.entity.FlowDefinition;
import com.warm.flow.orm.mapper.WarmMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class FlowTest {

    @Resource
    private DefService defService;

    @Resource
    private InsService insService;

    @Resource
    private TaskService taskService;

    public FlowParams getUser() {
        return FlowParams.build().flowCode("leaveFlow-serial1")
                .createBy("user:1")
                .skipType(SkipType.PASS.getKey())
                .additionalHandler(Arrays.asList("role:100", "role:101"))
                .permissionFlag(Arrays.asList("role:1", "role:2"));
    }

    @Test
    public void deployFlow() throws Exception {
        String path = "D:\\java\\warm-flow\\warm-flow-doc\\leaveFlow-serial1_1.0.xml";
        System.out.println("已部署流程的id：" + defService.importXml(new FileInputStream(path)).getId());
    }

    @Test
    public void deployFlow1() {
        WarmDaoImpl<Definition> dao = defService.getDao();
        WarmMapper<Definition> mapper = dao.getMapper();
        mapper.selectList(null).forEach(System.out::println);
    }

    @Test
    public void publish() {
        defService.publish(1243304918010433536L);
    }

    @Test
    public void startFlow() {
        System.out.println("已开启的流程实例id：" + insService.start("2", getUser()).getId());
    }

    @Test
    public void skipFlow() {
        // 通过实例id流转
        Instance instance = insService.skipByInsId(1239301524400771072L, getUser().skipType(SkipType.PASS.getKey())
                .permissionFlag(Arrays.asList("role:1", "role:2")));
        System.out.println("流转后流程实例：" + instance.toString());

//        // 通过任务id流转
//        Instance instance = taskService.skip(1219286332145274880L, getUser().skipType(SkipType.PASS.getKey())
//                .permissionFlag(Arrays.asList("role:1", "role:2")));
//        System.out.println("流转后流程实例：" + instance.toString());
    }

    @Test
    public void termination() {
        // 终止流程实例
        FlowParams flowParams = new FlowParams();
        flowParams.message("终止流程").createBy("user:1");
        taskService.termination(1239217703449923584L, flowParams);
    }

    @Test
    public void skipAnyNode() {
        // 跳转到指定节点 跳转到结束节点
        Instance instance = taskService.skip(1239216494752174080L, getUser().skipType(SkipType.PASS.getKey())
                .permissionFlag(Arrays.asList("role:1", "role:2")).nodeCode("9edc9b26-cab4-4fd4-9a30-c89f11626911"));
        System.out.println("流转后流程实例：" + instance.toString());
    }
    @Test
    public void assignee() {
        // 转办
        System.out.println("转办：" + taskService.transfer(1239301524417548289L, getUser()));
    }

    @Test
    public void page(){
        FlowDefinition flowDefinition = new FlowDefinition();
        Page<Definition> page = Page.pageOf(1,10);
        page = defService.orderByCreateTime().desc().page(flowDefinition, page);
        List<Definition> list = page.getList();
        for (Definition definition : list) {
            System.out.println(definition);
        }
    }

    /**
     * 加减签
     */
    @Test
    public void signature(){
        FlowParams flowParams = FlowParams.build().additionalHandler(Arrays.asList("role:102", "role:1"))
                        .permissionFlag(Arrays.asList("role:1", "role:2", "user:1"))
                        .skipType(SkipType.PASS.getKey()).createBy("user:1");
        taskService.signature(1234425333428654080L, flowParams);
    }
    /**
     * 委派
     */
    @Test
    public void depute(){
        FlowParams flowParams = FlowParams.build().additionalHandler(Arrays.asList("role:103", "role:3"))
                .permissionFlag(Arrays.asList("role:3", "role:102", "user:1"))
                .skipType(SkipType.PASS.getKey()).createBy("user:1");
        taskService.depute(1243308524025548800L, flowParams);
    }
    /**
     * 转办
     */
    @Test
    public void transfer(){
        FlowParams flowParams = FlowParams.build().additionalHandler(Arrays.asList("role:103", "role:3"))
                .permissionFlag(Arrays.asList("role:3", "role:102", "user:1"))
                .skipType(SkipType.PASS.getKey()).createBy("user:1");
        taskService.transfer(1243308524025548800L, flowParams, false, true);
    }
}
