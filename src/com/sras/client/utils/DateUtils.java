package com.sras.client.utils;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class DateUtils
{
	public static String getFriendlyTime(DateTime date)
	{
		DateTime now = new DateTime();
		Period period = new Period(date, now);

		PeriodFormatter formatter = new PeriodFormatterBuilder().appendYears()
				.appendSuffix(" years, ").appendMonths().appendSuffix(
						" months, ").appendWeeks().appendSuffix(" weeks, ")
				.appendDays().appendSuffix(" days, ").appendHours()
				.appendSuffix(" hours, ").appendMinutes().appendSuffix(
						" minutes, ").appendSeconds().appendSuffix(" seconds")
				.printZeroNever().toFormatter();
		String elapsed = formatter.print(period);
		return elapsed + " ago";
	}

	public static String getFriendlyTime(Date dateTime)
	{
		StringBuffer sb = new StringBuffer();
		Date current = Calendar.getInstance().getTime();
		long diffInSeconds = (current.getTime() - dateTime.getTime()) / 1000;

		long sec = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
		long min = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60
				: diffInSeconds;
		long hrs = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24
				: diffInSeconds;
		long days = (diffInSeconds = (diffInSeconds / 24)) >= 30 ? diffInSeconds % 30
				: diffInSeconds;
		long months = (diffInSeconds = (diffInSeconds / 30)) >= 12 ? diffInSeconds % 12
				: diffInSeconds;
		long years = (diffInSeconds = (diffInSeconds / 12));

		if (years > 0)
		{
			if (years == 1)
			{
				sb.append("a year");
			}
			else
			{
				sb.append(years + " years");
			}
			if (years <= 6 && months > 0)
			{
				if (months == 1)
				{
					sb.append(" and a month");
				}
				else
				{
					sb.append(" and " + months + " months");
				}
			}
		}
		else if (months > 0)
		{
			if (months == 1)
			{
				sb.append("a month");
			}
			else
			{
				sb.append(months + " months");
			}
			if (months <= 6 && days > 0)
			{
				if (days == 1)
				{
					sb.append(" and a day");
				}
				else
				{
					sb.append(" and " + days + " days");
				}
			}
		}
		else if (days > 0)
		{
			if (days == 1)
			{
				sb.append("a day");
			}
			else
			{
				sb.append(days + " days");
			}
			if (days <= 3 && hrs > 0)
			{
				if (hrs == 1)
				{
					sb.append(" and an hour");
				}
				else
				{
					sb.append(" and " + hrs + " hours");
				}
			}
		}
		else if (hrs > 0)
		{
			if (hrs == 1)
			{
				sb.append("an hour");
			}
			else
			{
				sb.append(hrs + " hours");
			}
			if (min > 1)
			{
				sb.append(" and " + min + " minutes");
			}
		}
		else if (min > 0)
		{
			if (min == 1)
			{
				sb.append("a minute");
			}
			else
			{
				sb.append(min + " minutes");
			}
			if (sec > 1)
			{
				sb.append(" and " + sec + " seconds");
			}
		}
		else
		{
			if (sec <= 1)
			{
				sb.append("about a second");
			}
			else
			{
				sb.append("about " + sec + " seconds");
			}
		}
		sb.append(" ago");
		return sb.toString();
	}
}
