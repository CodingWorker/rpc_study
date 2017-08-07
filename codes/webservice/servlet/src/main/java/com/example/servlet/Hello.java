package com.example.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by daiya on 2017/8/7.
 */
@WebServlet
public class Hello extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String redirectUrl="http://www.baidu.com";
        response.sendRedirect(redirectUrl);
    }

    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response){

    }

}
