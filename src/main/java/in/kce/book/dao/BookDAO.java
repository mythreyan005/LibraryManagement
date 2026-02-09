package in.kce.book.dao;

import java.sql.*;
import in.kce.book.bean.*;
import in.kce.book.util.DBUtil;

public class BookDAO {

	public BookBean createBook(BookBean bookBean) {

	    String sql = "INSERT INTO Book_tbl (ISBN, BOOK_TITLE, BOOK_TYPE, AUTHOR_CODE, BOOK_COST) VALUES (?,?,?,?,?)";

	    try (Connection con = DBUtil.getDBConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, bookBean.getIsbn());
	        ps.setString(2, bookBean.getBookName());
	        ps.setString(3, String.valueOf(bookBean.getBookType()));
	        ps.setInt(4, bookBean.getAuthor().getAuthorCode());
	        ps.setFloat(5, bookBean.getCost());

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            return bookBean;  
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}


    public BookBean fetchBook(String isbn) {

        BookBean book = null;

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM Book_tbl WHERE ISBN=?")) {

            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                book = new BookBean();
                book.setIsbn(rs.getString("ISBN"));
                book.setBookName(rs.getString("BOOK_TITLE"));   
                book.setBookType(rs.getString("BOOK_TYPE").charAt(0));
                book.setCost(rs.getFloat("BOOK_COST")); 

                AuthorBean author = new AuthorDAO()
                        .getAuthorByCode(rs.getInt("AUTHOR_CODE"));
                book.setAuthor(author);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }
}
