package org.cas.iie.xp.util;

import com.sun.xacml.EvaluationCtx;

import com.sun.xacml.attr.AttributeValue;
import com.sun.xacml.attr.BagAttribute;
import com.sun.xacml.attr.BooleanAttribute;
import com.sun.xacml.attr.TimeAttribute;

import com.sun.xacml.cond.EvaluationResult;
import com.sun.xacml.cond.FunctionBase;

import java.util.List;

public class TimeInRangeFunction extends FunctionBase {

	public static final String NAME = "http://research.sun.com/projects/xacml/names/function#time-in-range";

	public static final long MILLIS_PER_MINUTE = 1000 * 60;

	public static final long MILLIS_PER_DAY = MILLIS_PER_MINUTE * 60 * 24;

	public TimeInRangeFunction() {

		super(NAME, 0, TimeAttribute.identifier, false, 3,
				BooleanAttribute.identifier, false);
	}

	public EvaluationResult evaluate(List inputs, EvaluationCtx context) {

		AttributeValue[] argValues = new AttributeValue[inputs.size()];

		EvaluationResult result = evalArgs(inputs, context, argValues);

		if (result != null)

			return result;

		TimeAttribute attr = (TimeAttribute) (argValues[0]);

		long middleTime = attr.getMilliseconds();

		long minTime = resolveTime(attr, (TimeAttribute) (argValues[1]));

		long maxTime = resolveTime(attr, (TimeAttribute) (argValues[2]));

		if (minTime == maxTime)

			return EvaluationResult.getInstance(middleTime == minTime);

		long shiftSpan;

		if (minTime < maxTime)

			shiftSpan = -minTime;

		else

			shiftSpan = MILLIS_PER_DAY - minTime;

		maxTime = maxTime + shiftSpan;

		middleTime = handleWrap(middleTime + shiftSpan);

		return EvaluationResult.getInstance((middleTime >= 0)
				&& (middleTime <= maxTime));
	}

	private long resolveTime(TimeAttribute middleTime, TimeAttribute otherTime) {

		long time = otherTime.getMilliseconds();

		int tz = otherTime.getTimeZone();

		if (tz == TimeAttribute.TZ_UNSPECIFIED) {

			int middleTz = middleTime.getTimeZone();

			tz = otherTime.getDefaultedTimeZone();

			if (middleTz == TimeAttribute.TZ_UNSPECIFIED)

				middleTz = middleTime.getDefaultedTimeZone();

			if (middleTz != tz) {

				time -= ((middleTz - tz) * MILLIS_PER_MINUTE);

				time = handleWrap(time);
			}
		}

		return time;
	}

	private long handleWrap(long time) {

		if (time < 0) {

			return time + MILLIS_PER_DAY;
		}

		if (time > MILLIS_PER_DAY) {

			return time - MILLIS_PER_DAY;
		}

		return time;
	}

}
