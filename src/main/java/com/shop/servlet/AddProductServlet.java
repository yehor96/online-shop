package com.shop.servlet;

import com.shop.dao.ProductDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;

public class AddProductServlet extends HttpServlet {

    private ProductDao productDao;

    public AddProductServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        try (PrintWriter writer = response.getWriter()) {
            writer.println("<html><body>");
            writer.println("<h2>Add new product:</h2>");

            String form = """
                    <form action="/products" method="POST">
                        Name: <input type="text" name="name"/>
                        Price: <input type="text" name="price"/>
                        <input type="submit" value="add">
                    </form>
                    """;
            writer.println(form);
            writer.println("</body></html>");
        }
    }

    public void addMapping(ServletContextHandler context) {
        context.addServlet(new ServletHolder(this), "/products/add");
    }
}
