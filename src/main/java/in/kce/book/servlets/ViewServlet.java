package in.kce.book.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import in.kce.book.bean.BookBean;

@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {

   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        out.print("<html><body>");

        if (session == null) {
            out.print("<h3>No session.</h3>");
            out.print("</body></html>");
            return;
        }

        BookBean book = (BookBean) session.getAttribute("book");

        if (book == null) {
            out.print("<h3>No book found.</h3>");
            out.print("</body></html>");
            return;
        }

        out.print("<h2>Book Details</h2>");
        out.print("Book Title: " + book.getBookName() + "<br>");
        out.print("Author Name: " + book.getAuthor().getAuthorName() + "<br>");
        out.print("Author Contact: " + book.getAuthor().getContactNo() + "<br>");
        out.print("Book Price: " + book.getCost() + "<br>");
        out.print("Book ISBN: " + book.getIsbn());

        out.print("</body></html>");
    }
}
