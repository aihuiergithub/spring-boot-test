package me.wanghui.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/1
 * Time: 下午4:13
 */

@RestController
public class ZookeeperRegisterController {

    @RequestMapping(name = "/hello",method = RequestMethod.GET)
    public String hello(){
        return "this is api";
    }
}
