package in.kce.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import in.kce.book.bean.AuthorBean;
import in.kce.book.util.DBUtil;

public class AuthorDAO {

    public AuthorBean getAuthorByName(String name) {

        AuthorBean author = null;

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM Author_tbl WHERE Author_name = ?")) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                author = new AuthorBean();
                author.setAuthorCode(rs.getInt("Author_code"));
                author.setAuthorName(rs.getString("Author_name"));
                author.setContactNo(rs.getLong("Contact_no"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return author;
    }

    public AuthorBean getAuthorByCode(int code) {

        AuthorBean author = null;

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM Author_tbl WHERE Author_code = ?")) {

            ps.setInt(1, code);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                author = new AuthorBean();
                author.setAuthorCode(rs.getInt("Author_code"));
                author.setAuthorName(rs.getString("Author_name"));
                author.setContactNo(rs.getLong("Contact_no"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return author;
    }
}
