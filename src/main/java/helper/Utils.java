package helper;

import model.entity.Product;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static BigDecimal parseDecimal(String input) throws ParseException {

            NumberFormat numberFormat = DecimalFormat.getInstance(Locale.getDefault());

            Number number = numberFormat.parse(input);

            return new BigDecimal(number.toString());
    }

    public static BigDecimal getCartValue(List<Product> productsInCart) {
        return productsInCart.stream()
                .map(p -> p.getPrice())
                .reduce(BigDecimal::add)
                .get();
    }
}
