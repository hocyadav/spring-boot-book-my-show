package io.hari.demo;

import com.google.common.collect.ImmutableList;
import io.hari.demo.config.AppConfig;
import io.hari.demo.dao.SeatDao;
import io.hari.demo.dao.SeatLockDao;
import io.hari.demo.entity.*;
import io.hari.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired AppConfig config;
	@Autowired SeatsService seatsService;
	@Autowired ShowService showService;
	@Autowired MovieService movieService;
	@Autowired SeatDao seatDao;
	@Autowired UserService userService;
	@Autowired TicketService  ticketService;
	@Autowired SeatLockDao seatLockDao;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("config = " + config);

		final Movie movie1 = Movie.builder().movieName("Movie1 - Avengers").build();
		movieService.save(movie1);
		final Seat seat1 = Seat.builder().seatType("gold").build();
		final Seat seat2 = Seat.builder().seatType("silver").build();
		final Seat seat3 = Seat.builder().seatType("gold").build();
		final Seat seat4 = Seat.builder().seatType("silver").build();
		seatsService.saveAll(Arrays.asList(seat1, seat2, seat3, seat4));

		final Show show1 = Show.builder().showName("show1")
				.movies(Arrays.asList(movie1)).seats(Arrays.asList(seat1, seat2, seat3, seat4)).build();
		final Show save = showService.save(show1);
		System.out.println("save = " + save);

		final List<Seat> seats = seatDao.findAllAvailableShowSeats(show1.getId());
		System.out.println("seats = " + seats);

		// user1 operations
		//create user1
		final User user1 = userService.save(User.builder().name("hariom").build());
		final User user2 = userService.save(User.builder().name("chandan").build());
		System.out.println("user1 = " + user1);

		// add new show to user1 entity , make sure show is already in db
		final User hariom = userService.findById(1L);
		//find all available seats
		final List<Seat> showSeats = seatDao.findAllAvailableShowSeats(1L);
		System.out.println("All available Seats = " + showSeats);
		//check lock value
		showSeats.forEach(seat -> {
			final SeatLock seatLock = seatLockDao.findByShowIdAndSeatId(1L, seat.getId());
			System.err.println("seatLock = " + seatLock);
		});
		//select seats
		final ImmutableList<Seat> immutableList =
				ImmutableList.of(showSeats.get(0), showSeats.get(1), showSeats.get(2));
		System.err.println("selecting seats = " + immutableList);

		//user1 1 select seats - change lock status to temp locked
		userService.selectSeats(1L, immutableList);

		//find all available seats
		final List<Seat> showSeats3 = seatDao.findAllAvailableShowSeats(1L);
		System.err.println("before payment fail of user 1 - All available Seats3 for user 2= " + showSeats3);

		//if we test this then dont book below ticket obj and save - just comment and test
//		userService.paymentFail_Or_UnexpectedError(user1, show1, immutableList);

		//find all available seats
		final List<Seat> showSeats4 = seatDao.findAllAvailableShowSeats(1L);
		System.err.println("after payment fail All available Seats3 for user 2= " + showSeats4);

		//book ticket
		final Ticket ticket = Ticket.builder()
				.user(hariom)
				.paymentMod("cc")
				.show(show1)
				.seatIds(immutableList.stream().map(i -> i.getId().toString()).collect(Collectors.joining(",")))
				.build();
		ticketService.save(ticket);

		//attach ticket to user1
		hariom.setTickets(Arrays.asList(ticket));//after creating ticket set to user1 and update user1 db
		userService.save(hariom);

		//find all available seats
		final List<Seat> showSeats2 = seatDao.findAllAvailableShowSeats(1L);
		System.err.println("All available Seats2 = " + showSeats2);

		//create show 2 with show 1 seats , since both are running on same screen
		Movie movie2 = Movie.builder().movieName("Movie2 - Hulk").build();
		movieService.save(movie2);

		final Show show2 = Show.builder().showName("show2")
				.movies(Arrays.asList(movie2))
				.seats(Arrays.asList(seat1, seat2, seat3, seat4))
				.build();
		final Show fetchedShow = showService.save(show2);
		System.out.println("show2 = " + fetchedShow);
	}
}


