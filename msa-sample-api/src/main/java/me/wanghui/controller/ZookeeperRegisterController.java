package me.wanghui.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(name = "userSayHello", value = "user")
public class ZookeeperRegisterController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello_1(HttpServletRequest request, HttpSession session) {

        String name = (String) session.getAttribute("name");
        if (name == null) {
            name = "wangHui";
            session.setAttribute("name", name);
            System.out.println("session add name : " + name);
        }
        System.out.println("session name : " + name);
        return "this is api user/hello";
    }

    @RequestMapping(path = "hello2", method = RequestMethod.GET)
    public String hello_2(HttpServletRequest request, HttpSession session) {

        String name = (String) session.getAttribute("name");
        if (name == null) {
            name = "wangHui";
            session.setAttribute("name", name);
            System.out.println("session add name : " + name);
        }
        System.out.println("session name : " + name);
        return "this is api user/hello2";
    }

    @RequestMapping(path = "hello2/{userName}", method = RequestMethod.GET)
    public String hello_3(HttpServletRequest request, HttpSession session, @PathVariable String userName) {

        return "this is api get request set userName : " + userName;
    }

    @RequestMapping(path = "hello_put/{userName}", method = RequestMethod.PUT)
    public String hello_put(HttpServletRequest request, HttpSession session, @PathVariable String userName) {
        return "this is api put request set userName : " + userName;
    }
}
