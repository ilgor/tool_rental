package toolrental;

public enum ToolType {
    CHNS("Chainsaw", 1.49, 0.0, 1.49),
    LADW("Ladder", 1.99, 1.99, 0.0),
    JAKD("Jackhammer", 2.99, 0.0,  0.0),
    JAKR("Jackhammer", 2.99, 0.0, 0.0);

    private final String name;
    private final double dailyWeekdayCharge;
    private final double dailyWeekendCharge;
    private final double dailyHolidayCharge;

    ToolType(String name, double dailyWeekdayCharge, double dailyWeekendCharge, double dailyHolidayCharge) {
        this.name = name;
        this.dailyWeekdayCharge = dailyWeekdayCharge;
        this.dailyWeekendCharge = dailyWeekendCharge;
        this.dailyHolidayCharge = dailyHolidayCharge;
    }

    public String getName() {
        return name;
    }

    public double getDailyCharge(boolean isWeekend, boolean isHoliday) {
        if (isHoliday) {
            return dailyHolidayCharge;
        }
        if (isWeekend) {
            return dailyWeekendCharge;
        }

        return dailyWeekdayCharge;
    }
}
