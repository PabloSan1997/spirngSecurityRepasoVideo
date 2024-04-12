package com.practicasecurity.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/index")
    public String index(){
        return "Hello world";
    }

    @GetMapping("/index2")
    public String index2(){
        return "Hello world Not Secured";
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSession(){
        String sessionID = "";
        User userObject = null;

        List<Object> sessions = sessionRegistry.getAllPrincipals();
        for (Object session : sessions) {
            if(session instanceof User){
                userObject = (User) session;
            }
            List<SessionInformation> information = sessionRegistry.getAllSessions(session, false);
            for (SessionInformation sessionInformation : information) {
                sessionID = sessionInformation.getSessionId();
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("response","hello word");
        map.put("sessionid", sessionID);
        map.put("sessionUser", userObject);
        return ResponseEntity.ok().body(map);
    }
}
