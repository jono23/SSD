package app;

import java.util.Date;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public aspect CallLogAspect {

	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	//aspect to record in a log file each transaction of a call

	
	pointcut newCustomer(): call(app.Customer.new(..));
	pointcut newBooking(): call(app.Booking.new(..));
	pointcut cancelBooking(): execution(* app.TA.removeBooking (..));
	pointcut deactivateCustomer(): execution(* app.TA.deactivateCustomer(..));
	pointcut makeReview(): execution(* app.TA.addReview(..));
	pointcut endCall(): execution(* app.TA.forgetCustomer(..));
	
	
	before(): newCustomer(){
		Object[] args = thisJoinPoint.getArgs();
		//this will be passed to logger class
		LOGGER.info(new DateTime().toLocalDateTime().toString() + " \n" +
		"===== New customer added ===== \n " + args[0] + " \n " + args[1] + " \n" + args[2] + " \n" + args[3] + " \n" + args[4]);
	}
	
	before(): newBooking(){
		Object[] args = thisJoinPoint.getArgs();
		//this will be passed to logger class
		LOGGER.info(new DateTime().toLocalDateTime().toString()+ " \n" +		
		"===== Booking made ====="+ " \n" +
		"Accommodation : " +((Bookable)args[0]).getAccommodation()+ " \n" +
		"Room : " + args[0]+ " \n" +
		"Date : " + args[1]+ " \n" +
		"Customer : " + args[2]);
	}
	
	before(): cancelBooking(){
		Object[] args = thisJoinPoint.getArgs();
		//this will be passed to logger class
		
		//create date objects to compare that are midnight dates.
		LocalDate ld = new LocalDate(((Booking) args[0]).getDate());
		Date bookingMidnightDate = ld.toDate();
		Date nowMidnightDate = LocalDate.now().toDate();
		
		if(bookingMidnightDate.compareTo(nowMidnightDate) < 0){
			LOGGER.info(new DateTime().toLocalDateTime().toString() + " \n" +
			"===== FAILED attempted historic booking cancellation =====");	
		}else{
			LOGGER.info(new DateTime().toLocalDateTime().toString() + " \n" +
			"===== Booking canceled =====" + " \n" +
			"Accommodation : " + ((Booking)args[0]).getRoom().getAccommodation() + " \n" +	
			"Room : " + ((Booking)args[0]).getRoom() + " \n" +
			"Date : " + ((Booking)args[0]).getDate() +	" \n" +
			"Customer : " + ((Booking)args[0]).getCustomer());
		}

	}
	
	before(): deactivateCustomer(){
		Object[] args = thisJoinPoint.getArgs();
		//this will be passed to logger class
		LOGGER.info(new DateTime().toLocalDateTime().toString() + " \n" +		
		"===== Customer deactivated! ===== " + " \n" +
		"Customer : " + ((Customer)args[0]));		
	}
	
	before(): makeReview(){
		Object[] args = thisJoinPoint.getArgs();
		//this will be passed to logger class
		LOGGER.info(new DateTime().toLocalDateTime().toString()+ " \n" +		
		"===== Review made ===== "+ " \n" +
		"Accommodation : " + ((Booking)args[0]).getRoom().getAccommodation()+ " \n" +	
		"Room : " + ((Booking)args[0]).getRoom()+ " \n" +	
		"Date : " + ((Booking)args[0]).getDate()+ " \n" +	
		"Customer : " + ((Booking)args[0]).getCustomer()+ " \n" +		
		"Review : " + args[1]);
	}
	
	before(): endCall(){
		LOGGER.info(new DateTime().toLocalDateTime().toString() + " \n" +		
		"===== Call ended ===== " + " \n" +
		"======================");		
	}	
	
}
