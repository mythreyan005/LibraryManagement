package in.kce.book.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import in.kce.book.bean.*;
import in.kce.book.dao.AuthorDAO;
import in.kce.book.service.Administrator;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        if ("AddBook".equalsIgnoreCase(operation)) {

            BookBean book = buildBook(request);

            if (book == null) {
                response.sendRedirect("Invalid.html");
                return;
            }

            Administrator admin = new Administrator();
            String result = admin.add(book);

            if ("SUCCESS".equals(result)) {
                HttpSession session = request.getSession();
                session.setAttribute("book", book);
                response.sendRedirect("ViewServlet");
            } else {
                response.sendRedirect("Failure.html");
            }

        } 
        else if ("ViewBook".equalsIgnoreCase(operation)) {

            String isbn = request.getParameter("isbn");

            Administrator admin = new Administrator();
            BookBean book = admin.viewBook(isbn);

            if (book == null) {
                response.sendRedirect("Failure.html");
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("book", book);
            response.sendRedirect("ViewServlet");
        }
    }

    private BookBean buildBook(HttpServletRequest request) {

        try {
            String isbn = request.getParameter("isbn");
            String bookName = request.getParameter("bookname");
            String bookType = request.getParameter("booktype");
            String authorName = request.getParameter("author");
            String costStr = request.getParameter("bookcost");

            float cost = Float.parseFloat(costStr);

            AuthorDAO adao = new AuthorDAO();
            AuthorBean author = adao.getAuthorByName(authorName);

            if (author == null) return null;

            BookBean book = new BookBean();
            book.setIsbn(isbn);
            book.setBookName(bookName);
            book.setBookType(bookType.charAt(0));
            book.setCost(cost);
            book.setAuthor(author);

            return book;

        } catch (Exception e) {
            return null;
        }
    }
}
