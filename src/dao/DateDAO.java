package dao;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import com.toedter.calendar.IDateEvaluator;

public class DateDAO {
    public static String DateConvert(String date) {
    	// first take format, then parse then format again
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dateTime.format(outputFormatter);
       return formattedDate;
    }
    
    // disable specific date(based which arrangements i picked) -> disabled dates are already booked within arrangement
    public static class DisabledDateEvaluator implements IDateEvaluator {
        private final Calendar disabledStartDate;
        private final Calendar disabledEndDate;

        public DisabledDateEvaluator(Calendar disabledStartDate, Calendar disabledEndDate) {
            this.disabledStartDate = disabledStartDate;
            this.disabledEndDate = disabledEndDate;
        }

        @Override
        public boolean isSpecial(Date date) {
            return false;
        }

        @Override
        public Color getSpecialForegroundColor() {
            return null;
        }

        @Override
        public Color getSpecialBackroundColor() {
            return null;
        }

        @Override
        public String getSpecialTooltip() {
            return null;
        }

        @Override
        public boolean isInvalid(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            return calendar.compareTo(disabledStartDate) >= 0 && calendar.compareTo(disabledEndDate) <= 0;
        }

        @Override
        public Color getInvalidForegroundColor() {
            return null;
        }

        @Override
        public Color getInvalidBackroundColor() {
            return Color.RED; // Customize the color for disabled dates
        }

        @Override
        public String getInvalidTooltip() {
            return "This date is disabled";
        }
    }
}
