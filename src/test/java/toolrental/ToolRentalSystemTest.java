package toolrental;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.SimpleDateFormat;


public class ToolRentalSystemTest {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    @Test
    void test1() {
        CheckoutException thrown = assertThrows(CheckoutException.class, () ->
                ToolRentalSystem.checkout("JAKR", 5, 101, dateFormat.parse("9/3/15"))
        );
        assertEquals("Discount percent must be between [0, 100].", thrown.getMessage());
    }

    @Test
    void test2() throws Exception {
        RentalAgreement agreement = ToolRentalSystem.checkout("LADW", 3, 10, dateFormat.parse("7/2/20"));

        assertEquals("LADW", agreement.getToolCode());
        assertEquals("Ladder", agreement.getToolType());
        assertEquals("Werner", agreement.getToolBrand());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(dateFormat.parse("7/2/20"), agreement.getCheckOutDate());
        assertEquals(dateFormat.parse("07/5/20"), agreement.getDueDate());
        assertEquals(1.99, agreement.getDailyCharge());
        assertEquals(1, agreement.getChargeDays());
        assertEquals(1.99, agreement.getPreDiscountCharge());
        assertEquals(0.2, agreement.getDiscountAmount());
        assertEquals(1.8, agreement.getFinalCharge());
    }

    @Test
    void test3() throws Exception {
        RentalAgreement agreement = ToolRentalSystem.checkout("CHNS", 5, 25, dateFormat.parse("7/2/15"));

        assertEquals("CHNS", agreement.getToolCode());
        assertEquals("Chainsaw", agreement.getToolType());
        assertEquals("Stihl", agreement.getToolBrand());
        assertEquals(5, agreement.getRentalDays());
        assertEquals(dateFormat.parse("7/2/15"), agreement.getCheckOutDate());
        assertEquals(dateFormat.parse("7/7/15"), agreement.getDueDate());
        assertEquals(1.49, agreement.getDailyCharge());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(4.47, agreement.getPreDiscountCharge());
        assertEquals(1.12, agreement.getDiscountAmount());
        assertEquals(3.35, agreement.getFinalCharge());
    }

    @Test
    void test4() throws Exception {
        RentalAgreement agreement = ToolRentalSystem.checkout("JAKR", 6, 0, dateFormat.parse("9/3/15"));

        assertEquals("JAKR", agreement.getToolCode());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals("Ridgid", agreement.getToolBrand());
        assertEquals(6, agreement.getRentalDays());
        assertEquals(dateFormat.parse("9/3/15"), agreement.getCheckOutDate());
        assertEquals(dateFormat.parse("9/9/15"), agreement.getDueDate());
        assertEquals(3.0, agreement.getDailyCharge());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(9.0, agreement.getPreDiscountCharge());
        assertEquals(0.00, agreement.getDiscountAmount());
        assertEquals(9.0, agreement.getFinalCharge());
    }

    @Test
    void test5() throws Exception {
        RentalAgreement agreement = ToolRentalSystem.checkout("JAKR", 9, 0, dateFormat.parse("7/2/15"));

        assertEquals("JAKR", agreement.getToolCode());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals("Ridgid", agreement.getToolBrand());
        assertEquals(9, agreement.getRentalDays());
        assertEquals(dateFormat.parse("07/02/15"), agreement.getCheckOutDate());
        assertEquals(dateFormat.parse("07/11/15"), agreement.getDueDate());
        assertEquals(3.0, agreement.getDailyCharge());
        assertEquals(6, agreement.getChargeDays());
        assertEquals(18.0, agreement.getPreDiscountCharge());
        assertEquals(0.00, agreement.getDiscountAmount());
        assertEquals(18.0, agreement.getFinalCharge());
    }

    @Test
    void test6() throws Exception {
        RentalAgreement agreement = ToolRentalSystem.checkout("JAKR", 4, 50, dateFormat.parse("7/2/20"));

        assertEquals("JAKR", agreement.getToolCode());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals("Ridgid", agreement.getToolBrand());
        assertEquals(4, agreement.getRentalDays());
        assertEquals(dateFormat.parse("07/02/20"), agreement.getCheckOutDate());
        assertEquals(dateFormat.parse("07/06/20"), agreement.getDueDate());
        assertEquals(3.0, agreement.getDailyCharge());
        assertEquals(2, agreement.getChargeDays());
        assertEquals(6.0, agreement.getPreDiscountCharge());
        assertEquals(3.00, agreement.getDiscountAmount());
        assertEquals(3.00, agreement.getFinalCharge());
    }

    // Some more extra tests

    @Test
    void testInvalidDiscountPercent() {
        CheckoutException thrown = assertThrows(CheckoutException.class, () ->
                ToolRentalSystem.checkout("JAKR", 5, 101, dateFormat.parse("9/3/15"))
        );
        assertEquals("Discount percent must be between [0, 100].", thrown.getMessage());

        thrown = assertThrows(CheckoutException.class, () ->
                ToolRentalSystem.checkout("JAKR", 5, -1, dateFormat.parse("9/3/15"))
        );
        assertEquals("Discount percent must be between [0, 100].", thrown.getMessage());
    }

    @Test
    void testInvalidRentalDays() {
        CheckoutException thrown = assertThrows(CheckoutException.class, () ->
                ToolRentalSystem.checkout("CHNS", 0, 10, dateFormat.parse("9/3/15"))
        );
        assertEquals("Rental days must at least 1 day or more.", thrown.getMessage());
    }

    @Test
    void testInvalidToolCode() {
        CheckoutException thrown = assertThrows(CheckoutException.class, () ->
                ToolRentalSystem.checkout("INVALID_CODE", 5, 10, dateFormat.parse("9/3/15"))
        );
        assertEquals("Invalid tool code.", thrown.getMessage());
    }
}
