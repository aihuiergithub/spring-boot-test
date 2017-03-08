package me.wanghui.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/1
 * Time: 下午4:13
 */

@RestController
@RequestMapping(value = "user")
public class ZookeeperRegisterController {

    @RequestMapping(name = "userSayHello", path = "hello", method = RequestMethod.GET)
    public String hello_1(HttpServletRequest request, HttpSession session) {

        String name = (String) session.getAttribute("name");
        if (name == null) {
            name = "wangHui";
            session.setAttribute("name", name);
            System.out.println("session add name : " + name);
        }
        System.out.println("session name : " + name);
        return "this is api 1";
    }

    @RequestMapping(name = "userSayHello", path = "hello2", method = RequestMethod.GET)
    public String hello_2(HttpServletRequest request, HttpSession session) {

        String name = (String) session.getAttribute("name");
        if (name == null) {
            name = "wangHui";
            session.setAttribute("name", name);
            System.out.println("session add name : " + name);
        }
        System.out.println("session name : " + name);
        return "this is api 2";
    }
}
