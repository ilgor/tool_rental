package toolrental;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws CheckoutException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

        String toolCode = "LADW";
        int rentalDays = 5;
        double discountPercent = 10;
        Date checkoutDate = dateFormat.parse("7/2/20");

        RentalAgreement rentalAgreement = ToolRentalSystem.checkout(toolCode, rentalDays, discountPercent, checkoutDate);
        rentalAgreement.printAgreement();
    }
}
