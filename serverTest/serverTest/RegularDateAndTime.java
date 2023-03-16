package serverTest;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegularDateAndTime implements IDateAndTime {

	@Override
	public int getYear() {
		return LocalDate.now().getYear();
	}

	@Override
	public int getMonth() {
		return LocalDate.now().getMonthValue();
	}

	@Override
	public int getDay() {
		return LocalDate.now().getDayOfMonth();
	}

	@Override
	public int getHour() {
		return LocalTime.now().getHour();
	}

	@Override
	public int getMinutes() {
		return LocalTime.now().getMinute();
	}

	@Override
	public int getSeconds() {
		return LocalTime.now().getSecond();
	}

}
