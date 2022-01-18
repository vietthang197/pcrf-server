package vn.neo.controller;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author thanglv on 1/15/2022
 * @project pcrf-server
 */
@RestController
@RequestMapping("/api/pcrf")
@EnableScheduling
public class HomeController {

    private final AtomicInteger countSession = new AtomicInteger();

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_XML_VALUE})
    public String login(@RequestBody String data) {
        System.out.println("login");
        String res = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<cp_reply>\n" +
                "    <result>0</result>\n" +
                "    <session>"+ genSession() +"</session>\n" +
                "    <command>login</command>\n" +
                "    <description>Successful</description>\n" +
                "</cp_reply>";
        return res;
    }

    @PostMapping(value = "/keepAlive", produces = {MediaType.APPLICATION_XML_VALUE})
    public String keepAlive(@RequestBody String data) {
        System.out.println("keepalive");
        String res = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<cp_reply>\n" +
                "    <result>0</result>\n" +
                "    <session>nxixstewixrcdmdphnsnhpoympsfwsdxdbcyxxz</session>\n" +
                "    <command>keepalive</command>\n" +
                "</cp_reply>";
        return res;
    }

    @PostMapping(value = "/logout", produces = {MediaType.APPLICATION_XML_VALUE})
    public String logout(@RequestBody String data) {
        System.out.println("logout");
        String res = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<cp_reply>\n" +
                "    <result>0</result>\n" +
                "    <command>logout</command>\n" +
                "    <description>Successful</description>\n" +
                "</cp_reply>";
        countSession.decrementAndGet();
        return res;
    }

    @PostMapping(value = "service", produces = {MediaType.APPLICATION_XML_VALUE})
    public String service(@RequestBody String data) throws InterruptedException {
        Thread.sleep(1300);
        return "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<cp_reply>\n" +
                "\t<command>addservice</command>\n" +
                "\t<result>2100</result>\n" +
                "\t<description>FAILED</description>\n" +
                "\t<session>qizmwgiwnofzwsfonzpfprdnsiefwuepfbfxbym</session>\n" +
                "</cp_reply>";
    }

    public String genSession() {
        countSession.incrementAndGet();
        return UUID.randomUUID().toString();
    }

    @Scheduled(fixedRate = 3000)
    public void test() {
        System.out.println("session active: " + countSession.get());
    }

}
