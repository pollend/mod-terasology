package org.terasology.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.terasology.web.dto.UserDto;
import org.terasology.web.resource.UserResource;

import javax.validation.Valid;

@RestController
public class AuthController {
    private Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<UserResource> registerUser(@ModelAttribute("user") @Valid UserDto user, BindingResult result) {

return null;
//        return new ResponseEntity<UserResource>(new UserResource(user),HttpStatus.ACCEPTED);
    }

//    public ResponseEntity<?> login(){
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken());
//    }
}
