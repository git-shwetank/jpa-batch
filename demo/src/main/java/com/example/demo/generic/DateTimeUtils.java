package com.example.demo.generic;

import org.apache.commons.lang.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.example.demo.generic.StaticProperties.ASIA_SINGAPORE;
import static java.lang.String.format;
import static java.time.temporal.TemporalAdjusters.firstInMonth;
import static java.util.Arrays.asList;
import static org.joda.time.DateTimeConstants.*;
import static org.joda.time.DateTimeZone.UTC;
import static org.springframework.util.Assert.isTrue;

public class DateTimeUtils {
    public static final String DATETIME_REPORT_PATTERN = "yyyyMMdd_HHmmss";
    public static final String DATETIME_PA_PATTERN = "yyyyMMdd HH:mm:ss";
    public static final String BASIC_DATE_PATTERN = "yyyyMMdd";
    //TODO: Add additional date format for holidays page's date picker.
    public static final String CUSTOM_DATE_FORMAT = "d MMM yyyy";

    public static final String DATETIME_PATTERN_YYYYMMDD_HHMMSS = "yyyyMMdd HHmmss";

    public static final String DATE_FORMAT_BBGDL_PROCESSING = "EEE MMM dd HH:mm:ss ZZZ yyyy";

    public static final DateTimeFormatter ISO_BASIC_DATE_FORMATTER = DateTimeFormat.forPattern(BASIC_DATE_PATTERN);
    public static final DateTimeFormatter ISO_CUSTOM_DATE_FORMATTER = DateTimeFormat.forPattern(CUSTOM_DATE_FORMAT);
    public static final DateTimeFormatter ISO_DATE_FORMATTER = ISODateTimeFormat.date();
    public static final DateTimeFormatter ISO_HOUR_MINUTE_FORMATTER = ISODateTimeFormat.hourMinute();
    public static final DateTimeFormatter DATETIME_HUMAN_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATETIME_YYYYMMDD_HHMMSS_FORMATTER = DateTimeFormat.forPattern(DATETIME_PATTERN_YYYYMMDD_HHMMSS);


    public static DateTimeFormatter dateTimePathFormatter = DateTimeFormat.forPattern("yyyyMMdd_HHmm").withZone(DateTimeZone.UTC);

    public static String printCustomDate(ReadablePartial value) {
        return ISO_CUSTOM_DATE_FORMATTER.print(value);
    }

    public static String printBasicDate(ReadablePartial value) {
        return ISO_BASIC_DATE_FORMATTER.print(value);
    }

    public static LocalDate previousWeekDay(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return date.minusDays(3);
            case SUNDAY:
                return date.minusDays(2);
            default:
                return date.minusDays(1);
        }
    }

    public static DateTime previousWeekDay(DateTime dateTime) {
        LocalDate date = previousWeekDay(dateTime.toLocalDate());
        return date.toDateTime(dateTime.toLocalTime());
    }

    public static LocalDate nextWeekDay(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek();

        if (dayOfWeek == FRIDAY) {
            return date.plusDays(3);
        } else if (dayOfWeek == SATURDAY) {
            return date.plusDays(2);
        } else {
            return date.plusDays(1);
        }
    }

    public static LocalDate weekDayWithOffset(LocalDate date, int dayOffset) {
        isTrue(DateTimeUtils.isAWeekDay(date), format("Date %s should not be weekend.", date.toString()));

        LocalDate requiredWorkDay = date;
        while (dayOffset != 0) {
            if (dayOffset > 0) {
                requiredWorkDay = nextWeekDay(requiredWorkDay);
                --dayOffset;
                continue;
            }
            requiredWorkDay = previousWeekDay(requiredWorkDay);
            ++dayOffset;
        }
        return requiredWorkDay;
    }

    public static LocalDate dayWithOffset(LocalDate date, int dayOffset) {
        LocalDate requiredWorkDay = date;
        while (dayOffset != 0) {
            if (dayOffset > 0) {
                requiredWorkDay = nextWeekDay(requiredWorkDay);
                --dayOffset;
                continue;
            }
            requiredWorkDay = previousWeekDay(requiredWorkDay);
            ++dayOffset;
        }
        return requiredWorkDay;
    }

    public static Boolean isDateTimeAfterCutoffTime(DateTime dateTime, LocalTime cutOffTime, String cutOffTimeZone) {
        DateTimeZone tz = DateTimeZone.forID(cutOffTimeZone);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss");
        String dateTimeStr = dateTime.toString("yyyyMMdd") + " " + cutOffTime.toString("HH:mm:ss");

        DateTime cutoffDate = formatter.withZone(tz).parseDateTime(dateTimeStr);

        return dateTime.withZone(UTC).isAfter(cutoffDate.withZone(UTC));
    }

    /**
     * To identify time cutoff referencing to UTC.
     * Note: this is used when index has useNativeDates flag is not SET
     *
     * @param dateTime
     * @param cutOffTime
     * @param timeZone
     * @return
     */
    public static Boolean isTimeAfterCutoffTime(DateTime dateTime, LocalTime cutOffTime, String timeZone) {
        DateTimeZone tz = DateTimeZone.forID(timeZone);
        DateTime cutOff = cutOffTime.toDateTimeToday(tz);
        return isTimeAfterCutoffTime(dateTime, cutOff);
    }

    public static Boolean isTimeAfterCutoffTime(DateTime dateTime, DateTime cutOffTime) {
        return dateTime.withZone(UTC).toLocalTime().isAfter(cutOffTime.withZone(UTC).toLocalTime());
    }


    /**
     * To identify time cutoff referencing given timezone.
     * Note: this is used when index has useNativeDates flag as SET
     *
     * @param dateTime
     * @param cutOffTime
     * @param timeZone
     * @return
     */
    public static Boolean isTimeAfterCutoffTimeWithZone(DateTime dateTime, LocalTime cutOffTime, String timeZone) {
        DateTimeZone tz = DateTimeZone.forID(timeZone);
        DateTime cutOff = cutOffTime.toDateTimeToday(tz);
        return isTimeAfterCutoffTimeWithZone(dateTime, cutOff, tz);
    }

    public static Boolean isTimeAfterCutoffTimeWithZone(DateTime dateTime, DateTime cutOffTime, DateTimeZone timeZone) {
        return dateTime.withZone(timeZone).toLocalTime().isAfter(cutOffTime.withZone(timeZone).toLocalTime());
    }

    public static boolean isAWeekDay(LocalDate dayOfWeek) {
        return dayOfWeek.getDayOfWeek() != SATURDAY && dayOfWeek.getDayOfWeek() != SUNDAY;
    }

    public static void forEachDateBetweenInclusive(LocalDate startDate, LocalDate endDate, Consumer<LocalDate> lambda) {
        Days days = Days.daysBetween(startDate, endDate);
        Stream.iterate(startDate, date -> date.plusDays(1))
                .sequential()
                .limit(days.getDays() + 1)
                .forEach(lambda::accept);
    }

    public static DateTime mergeDateTimeWithLocalDate(DateTime dateTime, LocalDate date) {
        return new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(),
                dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), dateTime.getSecondOfMinute(), dateTime.getMillisOfSecond(),
                dateTime.getZone());
    }

    public static LocalTime convertTimeToUTC(String time) {
        LocalTime localtime = LocalTime.parse(time);
        return localtime.toDateTimeToday(DateTimeZone.forID(ASIA_SINGAPORE)).toDateTime(UTC).toLocalTime();
    }

    public static DateTime convertTimeToUTCDateTime(String time) {
        LocalTime localtime = LocalTime.now();
        if (time != null || !StringUtils.isEmpty(time)) {
            localtime = LocalTime.parse(time);
        }

        return localtime.toDateTimeToday(DateTimeZone.forID(ASIA_SINGAPORE)).toDateTime(UTC);
    }

    public static DateTime convertDateTimeToUTCDateTime(DateTime dateTime) {
        return dateTime.toDateTime(UTC);
    }

    public static DateTime convertUTCDateTimeToLocal(DateTime utcDatetime, String timeZone) {
        return utcDatetime.toDateTime(DateTimeZone.forID(timeZone));
    }

    public static DateTime convertUTCTimetoDateTime(LocalTime schTime, String schTimezone, String accTimezone) {
        LocalTime currentTime = LocalTime.now();
        int dayOffset = 0;
        if (currentTime.minusMinutes(10).isAfter(schTime)) {
            dayOffset = 1;
        }
        DateTime dtlocal = schTime.toDateTimeToday(UTC).toDateTime(DateTimeZone.forID(schTimezone)).plusDays(dayOffset);
        return dtlocal.toDateTime(UTC).toDateTime(DateTimeZone.forID(accTimezone));
    }

    public static LocalTime convertTimeFromZoneToZone(LocalTime time, String fromTimeZone, String toTimeZone) {
        return time.toDateTimeToday(DateTimeZone.forID(fromTimeZone))
                .toDateTime(DateTimeZone.forID(toTimeZone))
                .toLocalTime();
    }

    public static LocalTime convertTimeToUTC(String time, String timeZone) {
        LocalTime localtime = LocalTime.parse(time);
        return convertTimeToUTC(localtime, timeZone);
    }

    public static LocalTime convertTimeToUTC(LocalTime time, String timeZone) {
        return time.toDateTimeToday(DateTimeZone.forID(timeZone)).toDateTime(UTC).toLocalTime();
    }

    public static LocalTime convertUTCTimeToLocal(LocalTime localTime) {
        return localTime.toDateTimeToday(UTC).toDateTime(DateTimeZone.forID(ASIA_SINGAPORE)).toLocalTime();
    }

    public static LocalTime convertUTCTimeToLocal(LocalTime localTime, String timeZone) {
        return localTime.toDateTimeToday(UTC).toDateTime(DateTimeZone.forID(timeZone)).toLocalTime();
    }


    /**
     * Added by Anandan
     *
     * @param localTime
     * @param timeZone
     * @return
     */
    public static DateTime convertToUTCTime(LocalTime localTime, String timeZone) {
        return localTime.toDateTimeToday(DateTimeZone.forID(timeZone)).toDateTime(UTC);
    }

    public static DateTime convertUTCTimeToDateTimeWithZone(DateTime utcDateTime, String timeZone) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(timeZone);
        return utcDateTime.withZone(dateTimeZone);
    }

    public static DateTime convertUTCTimeToDateTimeLocal(DateTime utcDateTime) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(ASIA_SINGAPORE);
        return utcDateTime.withZone(dateTimeZone);
    }

    public static DateTime getActualCalculateDate(DateTime date, int offset) {
        if (offset > 0) {
            for (int i = 0; i < offset; i++) {
                LocalDate localDate = DateTimeUtils.nextWeekDay(date.toLocalDate());
                date = mergeDateTimeWithLocalDate(date, localDate);
            }
        } else {
            int loop = -offset;
            for (int i = 0; i < loop; i++) {
                LocalDate localDate = DateTimeUtils.previousWeekDay(date.toLocalDate());
                date = mergeDateTimeWithLocalDate(date, localDate);
            }
        }
        return date;
    }

    public static int getExpiryMonthNumberFromCode(String reutersCode) {

        if (reutersCode != null && reutersCode.length() > 2) {

            String monthCode = String.valueOf(reutersCode.charAt(reutersCode.length() - 2));
            YamlMapFactoryBean yamlMonthCodeMapFactoryBean = new YamlMapFactoryBean();
            yamlMonthCodeMapFactoryBean.setResources(new ClassPathResource("futures-month-codes.yml"));
            Map<String, String> monthCodeMap = (Map<String, String>) yamlMonthCodeMapFactoryBean.getObject().get("futures");

            Iterator i = monthCodeMap.entrySet().iterator();

            while (i.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) i.next();
                if (monthCode.equals(entry.getValue())) {
                    return Integer.parseInt(entry.getKey());
                }
            }
        }

        return 0;
    }

    /****
     * Get the contract expiry year from the contract code
     * @param reutersContractCode
     * @param calculationDate
     * @return
     * @throws IllegalArgumentException
     */
    public static int getExpiryYearFromCode(String reutersContractCode, LocalDate calculationDate) throws IllegalArgumentException {

        isTrue(reutersContractCode != null && reutersContractCode.length() > 2, format("In-correct Future Contract Code: %s", reutersContractCode));

        String yearCodePart = String.valueOf(reutersContractCode.charAt(reutersContractCode.length() - 1));

        int calculationYear = calculationDate.getYear();
        int expiryYear = calculationDate.getYear();

        String year = String.valueOf(calculationYear);
        String calculationYearPart = year.substring(year.length() - 1);
        if (!yearCodePart.equals(calculationYearPart)) {
            //TODO find the nearest expiry year-
            /*
            suppose yearCodePart is 8 and Calculation Year 2019
            then expiry Year should be 2018

            8-9 = -1 should be added to calculation year

            suppose yearCodePart is 8 and Calculation Year 2017
            then expiry Year should be 2018

            8-7 = 1 should be added to calculation year

            suppose yearCodePart is 0 and Calculation Year 2019
            then expiry Year should be 2020

            0 - 9 = -9  aka if unsigned difference is <-4 or =<5 (10-9=1) should be added to calculation year

            suppose yearCodePart is 0 and Calculation Year 2011
            then expiry Year should be 2010

            0 - 1 = -1 should be added to calculation year

            suppose yearCodePart is 0 and Calculation Year 2015
            then expiry Year should be 2020

            0 - 5 = -5 since unsigned difference is <-4 or =<5 (10-5=5) should be added to calculation year

            suppose yearCodePart is 9 and Calculation Year 2014
            then expiry Year should be 2019

            9 - 4 = 5 since unsigned difference is <-4 or =<5 (10-5=5) should be added to calculation year

            suppose yearCodePart is 2 and Calculation Year 2017
            then expiry Year should be 2012

            2 - 7 = -5 should be added to calculation year

            suppose yearCodePart is 2 and Calculation Year 2018
            then expiry Year should be 2022

            2 - 8 = -6 unsigned difference is <-4 or =<5 (10-6=4)should be added to calculation year

            suppose yearCodePart is 8 and Calculation Year 2012
            then expiry Year should be 2008

            8 - 2 = 6 should be added to calculation year
            */
            int difference = Integer.parseInt(yearCodePart) - Integer.parseInt(calculationYearPart);

            if (difference > -5 && difference <= 5) {
                // -4,-3,-2,-1 or 1,2,3,4,5
                expiryYear += difference;
            } else {
                if (difference < 0) {
                    expiryYear += (difference + 10);
                } else {
                    expiryYear += (difference - 10);
                }
            }
        }
        //else no change to expiry year
        return expiryYear;
    }

    public static LocalDate prepareDateForContractCode(String reutersCode, int givenYear) {
        int month = getExpiryMonthNumberFromCode(reutersCode);
        LocalDate returnDate = LocalDate.now().withDayOfMonth(1).withMonthOfYear(month).withYear(givenYear);
        return returnDate;
    }

    private static String getMonthCharacterFromCode(String code) {
        return String.valueOf(code.charAt((code.length() - 2)));
    }


    public static LocalDate findClosestWednesdayDate(LocalDate date) {
        int dayIndex = date.getDayOfWeek();

        if (asList(SUNDAY, MONDAY, TUESDAY).contains(dayIndex)) {
            return findClosestWednesdayDate(date.plusDays(1));
        } else if (asList(THURSDAY, FRIDAY, SATURDAY).contains(dayIndex)) {
            return findClosestWednesdayDate(date.minusDays(1));
        } else {
            return date;
        }

    }

    public static int compareFutureContract(String futureContractCode1, String futureContractCode2, LocalDate calculationDate) {

        LocalDate contractDate1 = prepareDateForContractCode(futureContractCode1, getExpiryYearFromCode(futureContractCode1, calculationDate));
        LocalDate contractDate2 = prepareDateForContractCode(futureContractCode2, getExpiryYearFromCode(futureContractCode2, calculationDate));

        return contractDate1.compareTo(contractDate2);

    }

    public static final java.time.format.DateTimeFormatter ISO_DATETIME_FORMATTER = java.time.format.DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT_ISO);

    /***
     *
     * @param dateTime
     * @return
     */
    public static String getTimeInISO(DateTime dateTime) {
        if (dateTime == null)
            return new String();

        DateTime dateTimeUTC = dateTime.withZone(DateTimeZone.UTC);

        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTimeUTC.getYear(),
                dateTimeUTC.getMonthOfYear(),
                dateTimeUTC.getDayOfMonth(),
                dateTimeUTC.getHourOfDay(),
                dateTimeUTC.getMinuteOfHour(),
                dateTimeUTC.getSecondOfMinute(),
                dateTimeUTC.getMillisOfSecond(),
                ZoneId.of(dateTimeUTC.getZone().getID()));

        return zonedDateTime.format(ISO_DATETIME_FORMATTER);
    }

    /***
     *
     * @param time
     * @return
     */
    public static LocalTime convertAnyFormatTimeToUTC(String time) {
        LocalTime localTime = convertAnyFormatTime(time);
        return localTime.toDateTimeToday(DateTimeZone.forID(ASIA_SINGAPORE)).toDateTime(UTC).toLocalTime();
    }


    public static LocalTime convertAnyFormatTimeWithTZToUTC(String time, String timezone) {
        LocalTime localTime = convertAnyFormatTime(time);
        return localTime.toDateTimeToday(DateTimeZone.forID(timezone)).toDateTime(UTC).toLocalTime();
    }

    public static LocalTime convertAnyFormatTime(String time) {
        LocalTime localTime;
        if (time.length() <= 4) {
            String value = StringUtils.leftPad(time, 4, "0");
            DateTimeFormatter fmt = DateTimeFormat.forPattern("HHmm");
            localTime = fmt.parseLocalTime(value);
        } else {
            localTime = LocalTime.parse(time);
        }

        return localTime;
    }

    /***
     *
     * @param firstDateOfMonth
     * @param recurrence
     * @param dayOfWeek
     * @return
     */
    public static LocalDate getNextRecurrenceOfMonth(LocalDate firstDateOfMonth, int recurrence, int dayOfWeek) {
        return LocalDate.parse(getNextRecurrenceOfMonth(java.time.LocalDate.parse(firstDateOfMonth.toString()), recurrence, dayOfWeek).toString());
    }

    /***
     *
     * @param firstDateOfMonth
     * @param recurrence
     * @param dayOfWeek
     * @return
     */
    public static java.time.LocalDate getNextRecurrenceOfMonth(java.time.LocalDate firstDateOfMonth, int recurrence, int dayOfWeek) {
        /**
         * Get the Given first date of the week in month
         *  DayofWeek=Thursday,
         *  firstOccurrenceDayOfWeek=Which first Thursday in the month?
         */
        java.time.LocalDate firstOccurrenceDayOfWeek = firstDateOfMonth.with(firstInMonth(DayOfWeek.of(dayOfWeek)));

        /***
         * Identify Week of the year
         */
        TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfYear();
        int weekNumber = firstOccurrenceDayOfWeek.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        /**
         * First day of the month week number + next occurrence week
         */
        weekNumber = weekNumber + recurrence;
        java.time.LocalDate reOccurrenceDate = firstOccurrenceDayOfWeek.with(weekOfYear, weekNumber);
        return reOccurrenceDate;
    }

    /***
     *
     * @param localDate
     * @return
     */
    public static LocalDate convertLocalDateFromBasicDatePattern(String localDate) {
        return LocalDate.parse(localDate, DateTimeUtils.ISO_BASIC_DATE_FORMATTER);

    }
}
