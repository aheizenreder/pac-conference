/**
 * 
 */
package com.prodyna.pac.conference.talk;

import java.util.Calendar;
import java.util.Date;

import com.prodyna.pac.conference.talk.model.Talk;

/**
 * This class collects utility methods for talk.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class TalkUtil {

	/**
	 * this method calculates based on talks start date and duration the end
	 * date of talk.
	 * 
	 * @param talk
	 *            talk to calculate end date.
	 * @return end date of talk calculated from start date and talk duration.
	 */
	public static Date calculateTalkEndDate(Talk talk) {
		Calendar startDateCal = Calendar.getInstance();
		startDateCal.setTime(talk.getStartDate());
		startDateCal.add(Calendar.MINUTE, talk.getDuration());
		return startDateCal.getTime();
	}

}
