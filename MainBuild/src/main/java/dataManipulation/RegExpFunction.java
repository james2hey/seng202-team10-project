package dataManipulation;

import org.sqlite.Function;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * RegExpFunction defines a function for the REGEXP operator in the SQLite database. This function is used when a query
 * needs to filter data fields using a regular expression.
 */
public class RegExpFunction extends Function {

    @Override
    protected void xFunc() throws SQLException {
        try {
            String expression = value_text(0);
            String value = value_text(1);
            if (value == null) {
                value = "";
            }
            Pattern pattern = Pattern.compile(expression);
            result(pattern.matcher(value).find() ? 1 : 0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
