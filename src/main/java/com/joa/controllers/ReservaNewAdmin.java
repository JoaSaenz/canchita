package com.joa.controllers;

import com.joa.classes.CanchaTO;
import com.joa.classes.ReservaTO;
import com.joa.classes.SelectTO;
import com.joa.dao.CanchaDAO;
import com.joa.dao.ReservaDAO;
import com.joa.dao.SelectDAO;
import com.joa.utils.StringUtils;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReservaNewAdmin", urlPatterns = {"/ReservaNewAdmin"})
public class ReservaNewAdmin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {

            ReservaDAO reservaDAO = new ReservaDAO();
            CanchaDAO canchaDAO = new CanchaDAO();

            String cliente = request.getParameter("clienteNew");
            String dni = request.getParameter("dniNew");
            String telefono = request.getParameter("telefonoNew");

            int idCancha = Integer.parseInt(request.getParameter("canchaNew"));
            String fecha = request.getParameter("fechaNew");
            String horaInicio = request.getParameter("horaInicioNew");
            String horaFin = request.getParameter("horaFinNew");

            double costo = Double.parseDouble(request.getParameter("costoNew"));
            double descuento = Double.parseDouble(request.getParameter("descuentoNew"));
            double total = Double.parseDouble(request.getParameter("totalNew"));
            double pagado = Double.parseDouble(request.getParameter("pagadoNew"));
            double saldo = Double.parseDouble(request.getParameter("saldoNew"));

            int idEstado = Integer.parseInt(request.getParameter("estadoNew"));

            ReservaTO obj = new ReservaTO();
            obj.setCliente(cliente);
            obj.setDni(dni);
            obj.setTelefono(telefono);

            obj.setIdCancha(idCancha);
            obj.setFecha(fecha);
            obj.setHoraInicio(horaInicio);
            obj.setHoraFin(horaFin);

            obj.setCosto(costo);
            obj.setDescuento(descuento);
            obj.setTotal(total);
            obj.setPagado(pagado);
            obj.setSaldo(saldo);

            obj.setIdEstado(idEstado);

            int respuesta = reservaDAO.insertFormAdmin(obj);
            request.setAttribute("respuesta", respuesta);

            //RETORNAR LA LISTA
            request.setAttribute("fecha", fecha);

            List<CanchaTO> canchas = canchaDAO.list();
            request.setAttribute("canchas", canchas);

            String idsCanchas = "";

            for (CanchaTO cancha : canchas) {
                cancha.setReservas(reservaDAO.list2(cancha.getId(), fecha));
                idsCanchas += cancha.getId() + ",";
            }
            idsCanchas = StringUtils.deleteLastChar(idsCanchas);
            request.setAttribute("idsCanchas", idsCanchas);

            //ESTADOS
            String estadosHidden = request.getParameter("estadosNew");
            SelectDAO selectDAO = new SelectDAO();
            List<SelectTO> estados = selectDAO.list("reservasEstados");

            if (estadosHidden == null) {
                estadosHidden = "";
                for (SelectTO estado : estados) {
                    estadosHidden += estado.getId() + ",";
                }
                estadosHidden = StringUtils.deleteLastChar(estadosHidden);
            }

            String[] estadosArray = StringUtils.splitOnCharArray(estadosHidden, ",");
            for (SelectTO estado : estados) {
                for (String string : estadosArray) {
                    if (estado.getId() == Integer.parseInt(string)) {
                        estado.setMarcado(1);
                    }
                }
            }

            //MODAL
            List<SelectTO> estadosModal = selectDAO.list("reservasEstados");

            //SET TO REQUEST
            request.setAttribute("estados", estados);
            request.setAttribute("estadosModal", estadosModal);
            request.setAttribute("estadosHidden", estadosHidden);
            request.setAttribute("estadosNew", estadosHidden);
            request.setAttribute("estadosExcel", estadosHidden);
            
            //FLAG LOGIN OR NEW/EDIT
            request.setAttribute("login", 0);

            getServletConfig().getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);

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
