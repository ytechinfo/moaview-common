package com.moaview.ep.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class DateUtils
{
  public static final int YEAR = 1;
  public static final int MONTH = 2;
  public static final int DATE = 3;
  public static final int MonTHFIRST = 4;
  public static final int MONTHEND = 5;
  public static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
  public static String DEFAULT_YYYYMMDD = "yyyy-MM-dd";

  public static String getYyyymmdd(Calendar cal) {
    Locale currentLocale = new Locale("KOREAN", "KOREA");
    String pattern = "yyyyMMdd";
    SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
    return formatter.format(cal.getTime());
  }

  public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {
    int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
    int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
    int dd = Integer.parseInt(yyyymmdd.substring(6));

    GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0, 0, 0);

    return calendar;
  }

  public static String getCurrentDateTime() {
    return getCurrentDateTime(DEFAULT_PATTERN);
  }

  public static String getCurrentDateTime(String format) {
    Date today = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(today);
  }

  public static String getCurrentDate() {
    return getCurrentDate(DEFAULT_YYYYMMDD);
  }

  public static String getCurrentDate(String format) {
    Date today = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(today);
  }

  public static String getCurrentTime() {
    Date today = new Date();
    Locale currentLocale = new Locale("KOREAN", "KOREA");
    String pattern = "HHmmss";
    SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
    return formatter.format(today);
  }

  public static String getCurrentYyyymmdd() {
    return getCurrentDateTime().substring(0, 8);
  }

  public static String getWeekToDay(String yyyymm, int week, String pattern) {
    Calendar cal = Calendar.getInstance(Locale.KOREA);

    int new_yy = Integer.parseInt(yyyymm.substring(0, 4));
    int new_mm = Integer.parseInt(yyyymm.substring(4, 6));
    int new_dd = 1;

    cal.set(new_yy, new_mm - 1, new_dd);

    if (cal.get(7) == 1) {
      week--;
    }

    cal.add(5, (week - 1) * 7 + (cal.getFirstDayOfWeek() - cal.get(7)));

    SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREA);

    return formatter.format(cal.getTime());
  }

  public static String getCalcDate(int iDay) {
    return getCalcDate(iDay, "yyyy-MM-dd");
  }

  public static String getCalcDate(int iDay, String pattern) {
    Calendar temp = Calendar.getInstance();

    temp.add(5, iDay);

    SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);

    return dateFormatter.format(temp.getTime());
  }

  public static String getCalcMonth(int iMonth) {
    return getCalcMonth(iMonth, "yyyy-MM");
  }

  public static String getCalcMonth(int iMonth, String pattern) {
    Calendar temp = Calendar.getInstance();
    temp.add(2, iMonth);

    SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);

    return dateFormatter.format(temp.getTime());
  }

  public static String getOpDate(int field, int amount, String date) {
    GregorianCalendar calDate = getGregorianCalendar(date);

    if (field == 1)
      calDate.add(1, amount);
    else if (field == 2)
      calDate.add(2, amount);
    else {
      calDate.add(5, amount);
    }

    return getYyyymmdd(calDate);
  }

  public static int getWeek(String yyyymmdd, int addDay) {
    Calendar cal = Calendar.getInstance(Locale.KOREA);
    int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
    int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
    int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));

    cal.set(new_yy, new_mm - 1, new_dd);
    cal.add(5, addDay);

    int week = cal.get(7);
    return week;
  }

  public static int getLastDayOfMon(int year, int month) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, month, 1);
    return cal.getActualMaximum(5);
  }

  public static int getLastDayOfMon(String yyyymm) {
    Calendar cal = Calendar.getInstance();
    int yyyy = Integer.parseInt(yyyymm.substring(0, 4));
    int mm = Integer.parseInt(yyyymm.substring(4)) - 1;

    cal.set(yyyy, mm, 1);
    return cal.getActualMaximum(5);
  }

  public static boolean isCorrect(String yyyymmdd) {
    boolean flag = false;
    if (yyyymmdd.length() < 8)
      return false;
    try {
      int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
      int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
      int dd = Integer.parseInt(yyyymmdd.substring(6));
      flag = isCorrect(yyyy, mm, dd);
    } catch (Exception ex) {
      return false;
    }
    return flag;
  }

  public static boolean isCorrect(int yyyy, int mm, int dd) {
    if ((yyyy < 0) || (mm < 0) || (dd < 0))
      return false;
    if ((mm > 12) || (dd > 31)) {
      return false;
    }
    String year = "yyyy";
    String month = "00" + mm;
    String year_str = year + month.substring(month.length() - 2);
    int endday = getLastDayOfMon(year_str);

    if (dd > endday) {
      return false;
    }
    return true;
  }

  public static String getThisDay(String type) {
    Date date = new Date();
    SimpleDateFormat sdf = null;
    try {
      if (type.toLowerCase().equals("yyyymmdd")) {
        sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
      }
      if (type.toLowerCase().equals("yyyymmddhh")) {
        sdf = new SimpleDateFormat("yyyyMMddHH");
        return sdf.format(date);
      }
      if (type.toLowerCase().equals("yyyymmddhhmm")) {
        sdf = new SimpleDateFormat("yyyyMMddHHmm");
        return sdf.format(date);
      }
      if (type.toLowerCase().equals("yyyymmddhhmmss")) {
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
      }
      if (type.toLowerCase().equals("yyyymmddhhmmssms")) {
        sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(date);
      }
      sdf = new SimpleDateFormat(type);
      return sdf.format(date);
    } catch (Exception localException) {
    }
    return "[ ERROR ]: parameter must be 'YYYYMMDD', 'YYYYMMDDHH', 'YYYYMMDDHHSS'or 'YYYYMMDDHHSSMS'";
  }

  public static String changeDateFormat(String yyyymmdd) {
    String rtnDate = null;

    String yyyy = yyyymmdd.substring(0, 4);
    String mm = yyyymmdd.substring(4, 6);
    String dd = yyyymmdd.substring(6, 8);
    rtnDate = yyyy + " 년 " + mm + " 월 " + dd + " 일";

    return rtnDate;
  }

  public static long getDifferDays(String startDate, String endDate) {
    GregorianCalendar StartDate = getGregorianCalendar(startDate);
    GregorianCalendar EndDate = getGregorianCalendar(endDate);
    long difer = (EndDate.getTime().getTime() - StartDate.getTime().getTime()) / 86400000L;
    return difer;
  }

  public static int getDayOfWeek() {
    Calendar rightNow = Calendar.getInstance();
    int day_of_week = rightNow.get(7);
    return day_of_week;
  }

  public static int getWeekOfYear() {
    Locale LOCALE_COUNTRY = Locale.KOREA;
    Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
    int week_of_year = rightNow.get(3);
    return week_of_year;
  }

  public static int getWeekOfMonth() {
    Locale LOCALE_COUNTRY = Locale.KOREA;
    Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
    int week_of_month = rightNow.get(4);
    return week_of_month;
  }

  public static Calendar getCalendarInstance(String p_date) {
    Locale LOCALE_COUNTRY = Locale.KOREA;
    Calendar retCal = Calendar.getInstance(LOCALE_COUNTRY);

    if ((p_date != null) && (p_date.length() == 8)) {
      int year = Integer.parseInt(p_date.substring(0, 4));
      int month = Integer.parseInt(p_date.substring(4, 6)) - 1;
      int date = Integer.parseInt(p_date.substring(6));

      retCal.set(year, month, date);
    }
    return retCal;
  }
}