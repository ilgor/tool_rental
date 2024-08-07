package toolrental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ToolRentalSystem {
    private static final Map<String, Tool> tools = new HashMap<>();

    static {
        tools.put("CHNS", new Tool("CHNS", ToolType.CHNS, "Stihl"));
        tools.put("LADW", new Tool("LADW", ToolType.LADW, "Werner"));
        tools.put("JAKD", new Tool("JAKD", ToolType.JAKD, "DeWalt"));
        tools.put("JAKR", new Tool("JAKR", ToolType.JAKR, "Ridgid"));
    }

    public static RentalAgreement checkout(String toolCode, int rentalDays, double discountPercent, Date checkOutDate) throws CheckoutException {
        if (rentalDays < 1) {
            throw new CheckoutException("Rental days must at least 1 day or more.");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new CheckoutException("Discount percent must be between [0, 100].");
        }

        Tool tool = tools.get(toolCode);

        if (tool == null) {
            throw new CheckoutException("Invalid tool code.");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkOutDate);
        calendar.add(Calendar.DAY_OF_MONTH, rentalDays);
        Date dueDate = calendar.getTime();

        double dailyCharge = roundUp(tool.getDailyCharge(isWeekend(checkOutDate), isHoliday(checkOutDate)));
        int chargeDays = calculateChargeDays(checkOutDate, rentalDays);

        double preDiscountCharge = roundUp(dailyCharge * chargeDays);
        double discountAmount = roundUp((preDiscountCharge * discountPercent) / 100);
        double finalCharge = roundUp(preDiscountCharge - discountAmount);

        return new RentalAgreement(toolCode, tool.getType().getName(), tool.getBrand(), rentalDays, checkOutDate, dueDate, dailyCharge, chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    private static double roundUp(double val) {
        BigDecimal bd = new BigDecimal(val);
        bd = bd.setScale(2, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    private static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    private static boolean isHoliday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        return (month == Calendar.JULY && day == 4) || (month == Calendar.SEPTEMBER && getFirstMonday(year) == day);
    }

    private static int getFirstMonday(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.SEPTEMBER, 1);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    private static int calculateChargeDays(Date checkOutDate, int rentalDays) {
        int chargeDays = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkOutDate);
        for (int i = 0; i < rentalDays; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date currentDate = calendar.getTime();
            if (!isWeekend(currentDate) && !isHoliday(currentDate)) {
                chargeDays++;
            }
        }
        return chargeDays;
    }
}
