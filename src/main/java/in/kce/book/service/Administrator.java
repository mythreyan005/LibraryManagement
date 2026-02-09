package in.kce.book.service;

import in.kce.book.bean.BookBean;
import in.kce.book.dao.BookDAO;

public class Administrator {

    BookDAO bookdao = new BookDAO();

    public String add(BookBean bookBean) {

        if (bookBean == null ||
            bookBean.getBookName() == null || bookBean.getBookName().trim().isEmpty() ||
            bookBean.getIsbn() == null || bookBean.getIsbn().trim().isEmpty() ||
            bookBean.getAuthor() == null ||
            bookBean.getAuthor().getAuthorName() == null ||
            bookBean.getAuthor().getAuthorName().trim().isEmpty() ||
            bookBean.getCost() <= 0) {

            return "INVALID";
        }

        
        char type = Character.toUpperCase(bookBean.getBookType());
        if (type != 'G' && type != 'T') {
            return "INVALID";
        }
        bookBean.setBookType(type);

        BookBean saved = bookdao.createBook(bookBean);

        if (saved != null)
            return "SUCCESS";

        return "FAILURE";
    }

    public BookBean viewBook(String isbn) {
        return bookdao.fetchBook(isbn);
    }
}
