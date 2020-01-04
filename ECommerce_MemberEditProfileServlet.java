/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B_servlets;

import HelperClasses.Member;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hellg
 */
@WebServlet(name = "ECommerce_MemberEditProfileServlet", urlPatterns = {"/ECommerce_MemberEditProfileServlet"})
public class ECommerce_MemberEditProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8"); //to read the html text instead of text
        HttpSession session = request.getSession();
        Member updatedMember = new Member();

        PrintWriter out = response.getWriter();

        String name = (String) request.getParameter("name");
        String phone = (String) request.getParameter("phone");
        String city = (String) request.getParameter("country");
        String address = (String) request.getParameter("address");
        int securityQuestion = Integer.parseInt(request.getParameter("securityQuestion"));
        String securityAnswer = (String) request.getParameter("securityAnswer");
        int age = Integer.parseInt(request.getParameter("age"));
        int income = Integer.parseInt(request.getParameter("income"));
        String email = (String) request.getParameter("email");

        //Bonus Marks: Change Password
//        String password = (String) request.getParameter("password");

        //Using form information to change member session information
//        updatedMember.setName(name);
//        updatedMember.setPhone(phone);
//        updatedMember.setCity(city);
//        updatedMember.setAddress(address);
//        updatedMember.setCity(city);
//        updatedMember.setSecurityQuestion(securityQuestion);
//        updatedMember.setSecurityAnswer(securityAnswer);
//        updatedMember.setAge(age);
//        updatedMember.setIncome(income);
//        updatedMember.setEmail(email);
//
//        session.setAttribute("member", updatedMember);
//        session.setAttribute("memberName", updatedMember.getName());
        //Updating Database
        Client client1 = ClientBuilder.newClient();
        WebTarget target1 = client1.target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.memberentity") /* /src/java/com */
                .path("editProfile")
                .queryParam("name", name)
                .queryParam("phone", phone)
                .queryParam("city", city)
                .queryParam("address", address)
                .queryParam("securityQuestion", securityQuestion)
                .queryParam("securityAnswer", securityAnswer)
                .queryParam("age", age)
                .queryParam("income", income)
                .queryParam("email", email);

        Invocation.Builder invocationBuilder1 = target1.request(MediaType.APPLICATION_JSON);
        Response res1 = invocationBuilder1.put(Entity.entity(updatedMember, MediaType.APPLICATION_JSON));

        //Getting updated information
        Client client2 = ClientBuilder.newClient();
        WebTarget target2 = client2.target("http://localhost:8080/IS3102_WebService-Student/webresources/entity.memberentity") /* /src/java/com */
                .path("getMember")
                .queryParam("email", email);

        Invocation.Builder invocationBuilder2 = target2.request(MediaType.APPLICATION_JSON);
        Response res2 = invocationBuilder2.get();

        if (res1.getStatus() == 200 && res2.getStatus() == 200) {
            Member updatedMemberData = res2.readEntity(Member.class);

            session.setAttribute("member", updatedMemberData);
            session.setAttribute("memberName", updatedMemberData.getName());

            response.sendRedirect("/IS3102_Project-war/B/SG/memberProfile.jsp");
        } else {
            out.println("Update failed, please try again!");
        }
//        if (password != null) {
//            request.setAttribute("")
//        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
