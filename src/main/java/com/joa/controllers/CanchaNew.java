/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.controllers;

import com.joa.classes.CanchaTO;
import com.joa.dao.CanchaDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author developer
 */
@WebServlet(name = "CanchaNew", urlPatterns = {"/CanchaNew"})
public class CanchaNew extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            /* TODO output your page here. You may use following sample code. */
            CanchaDAO canchaDAO = new CanchaDAO();
            
            int idCancha = request.getParameter("idCancha") == null ? 0 :  Integer.parseInt(request.getParameter("idCancha"));
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            
            
            CanchaTO canchaTO = new CanchaTO();
            canchaTO.setId(idCancha);
            canchaTO.setNombre(nombre);
            canchaTO.setDescripcion(descripcion);
            
            int respuesta = canchaDAO.insertUpdate(canchaTO);
            request.setAttribute("respuesta", respuesta);
            
            //RETORNAR LA LISTA
            List<CanchaTO> canchas = canchaDAO.list();
            request.setAttribute("canchas", canchas);
            
            
            getServletConfig().getServletContext().getRequestDispatcher("/canchasList.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("ERROR @ReservaNewAdmin: " + e);
        }
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
