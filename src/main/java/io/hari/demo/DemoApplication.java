package io.hari.demo;

import com.google.common.collect.ImmutableList;
import io.hari.demo.config.AppConfig;
import io.hari.demo.dao.ScreenDao;
import io.hari.demo.dao.SeatDao;
import io.hari.demo.dao.SeatLockDao;
import io.hari.demo.dao.ShowDao;
import io.hari.demo.entity.*;
import io.hari.demo.service.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
@EnableCaching//1. before that dependency in pom
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    AppConfig appConfig;
    @Autowired
    SeatsService seatsService;
    @Autowired
    ShowService showService;
    @Autowired
    ShowDao showDao;
    @Autowired
    MovieService movieService;
    @Autowired
    SeatDao seatDao;
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;
    @Autowired
    SeatLockDao seatLockDao;
    @Autowired
    ScreenDao screenDao;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("config = " + appConfig);

        final Map<String, String> map = new HashMap<>();
        map.put("rating", "4.5");
        map.put("top_10", "yes");
        final Movie movie1 = Movie.builder().movieName("Movie1 - Avengers")
                .movieAttribute(MovieAttribute.builder().attributes(map).build())
                .ratings(Float.valueOf("4.5")).build();
        movieService.save(movie1);
        final Seat seat1 = Seat.builder().seatType("gold").build();
        final Seat seat2 = Seat.builder().seatType("silver").build();
        final Seat seat3 = Seat.builder().seatType("gold").build();
        final Seat seat4 = Seat.builder().seatType("silver").build();
        seatsService.saveAll(Arrays.asList(seat1, seat2, seat3, seat4));

        final LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 00));
        final LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 00));
        //period will give only days, month and yr
        final Period between = Period.between(startTime.toLocalDate(), endTime.toLocalDate());
        System.out.println("between.getDays() = " + between.getDays());
        //duration will give min, sec : https://mkyong.com/java8/java-8-difference-between-two-localdate-or-localdatetime/
        final Duration between1 = Duration.between(startTime, endTime);
        System.out.println("between1.toMinutes() = " + between1.toMinutes());

        final Show show1 = Show.builder().showName("show1 - afternoon show").movies(Arrays.asList(movie1))
                .startTime(startTime)
                .endTime(endTime)
                .movieLength(Duration.between(startTime, endTime).toMinutes())
                .weekDay(WeekDay.builder().days(Arrays.asList(Days.sun, Days.mon)).build())
                .seats(Arrays.asList(seat1, seat2, seat3, seat4)).build();
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

        final Ticket ticket1 = ticketService.fetchTicket(ticket.getId());
        System.out.println("--------  "+ticket1);
        final Ticket ticket2 = ticketService.fetchTicket(ticket.getId());
        System.out.println("--------  "+ticket2);

        //attach ticket to user1
        hariom.setTickets(Arrays.asList(ticket));//after creating ticket set to user1 and update user1 db
        userService.save(hariom);

        //find all available seats
        final List<Seat> showSeats2 = seatDao.findAllAvailableShowSeats(1L);
        System.err.println("All available Seats2 = " + showSeats2);

        //create show 2 with show 1 seats , since both are running on same screen
        Movie movie2 = Movie.builder().movieName("Movie2 - Hulk").ratings(4.6f).build();
        movieService.save(movie2);

        final LocalDateTime startTime1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 00));
        final LocalDateTime endTime1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 30));
        final Show show2 = Show.builder().showName("show2 - evening show")
                .movies(Arrays.asList(movie2))
                .startTime(startTime1)
                .endTime(endTime1)
                .movieLength(Duration.between(startTime1, endTime1).toMinutes())
                .weekDay(WeekDay.builder().days(Arrays.asList(Days.mon, Days.tue)).build())
                .seats(Arrays.asList(seat1, seat2, seat3, seat4))
                .build();
        final Show fetchedShow = showService.save(show2);
        System.out.println("show2 = " + fetchedShow);

        //find day wise shows, here we need to go deep inside other obj list, i.e. WeekDay list is days,
        //1. create a Transient property same as that weekday
        //2. create a getter,
        //3. now we can call that getter in stream as we are calling field
        final List<Show> shows = showService.findAll();
        final Map<Boolean, List<Show>> collect = shows.stream().filter(Objects::nonNull)//always add object null filter
                .collect(Collectors.partitioningBy(i -> i.getDays().contains(Days.sun)));
        System.out.println("sunday shows = " + collect.get(true));//sunday shows

        final Map<Boolean, List<Show>> monShows = shows.stream().filter(Objects::nonNull)
                .collect(Collectors.partitioningBy(i -> i.getDays().contains(Days.mon)));
        final Map<Boolean, List<Show>> tueShows = shows.stream().filter(Objects::nonNull)
                .collect(Collectors.partitioningBy(i -> i.getDays().contains(Days.tue)));
        System.out.println("monShows = " + monShows.get(true));
        System.out.println("tueShows = " + tueShows.get(true));

        //movie i want to search for monday or tuesday
        final Map<Boolean, List<Show>> monAndTueShows = shows.stream().filter(Objects::nonNull)
                .collect(Collectors.partitioningBy(i -> {
                    final List<Days> days = i.getDays();
                    if (days.contains(Days.mon)) return true;
                    if (days.contains(Days.tue)) return false;
                    return false;
                }));
        System.out.println("monAndTueShows = " + monAndTueShows.get(true));

        final Map<Boolean, List<Show>> collect1 = shows.stream().filter(Objects::nonNull)
                .collect(Collectors.partitioningBy(
                        i -> i.getDays().contains(Days.sun),
                        Collectors.toList()
                ));
        System.out.println("collect1 = " + collect1.get(true));
        // movies on monday, : adding new argument in partitioning
        final Map<Boolean, Long> collect2 = shows.stream().filter(Objects::nonNull).collect(Collectors.partitioningBy(
                i -> i.getDays().contains(Days.mon),
                Collectors.counting()
        ));
        System.out.println("collect2 = " + collect2.get(true));

        final Map<Boolean, Map<String, List<Show>>> collect3 = shows.stream().filter(Objects::nonNull)
				.collect(Collectors.partitioningBy(i -> i.getDays().contains(Days.mon),
						Collectors.groupingBy(i -> i.getShowName())));
        System.out.println("collect3 = " + collect3.get(true));

        //key :sun, value : movies name in string
        final Map<Boolean, List<String>> collect4 = shows.stream().filter(Objects::nonNull)
				.collect(Collectors.partitioningBy(i -> i.getDays().contains(Days.mon),//filter
                        Collectors.mapping(i -> i.getShowName(), Collectors.toList())//we can place price here, or we can create new obj
                ));
        System.out.println("collect4 = " + collect4);

        final Map<Boolean, List<List<Seat>>> collect5 = shows.stream().filter(Objects::nonNull)
				.collect(Collectors.partitioningBy(i -> i.getDays().contains(Days.sun),
                Collectors.mapping(i -> i.getSeats(), Collectors.toList())//sunday movie seat
        ));
        System.out.println("collect5 = " + collect5.get(true));

		//client output from show entity, key : mon, value new entity
		final Map<Boolean, List<ShowDto>> collect6 = shows.stream().filter(Objects::nonNull)
				.collect(Collectors.partitioningBy(//using 2 argument partitioning method
						show -> show.getDays().contains(Days.mon),//1st argument
						Collectors.mapping(show -> {
							final ShowDto showDto = ShowDto.builder().movieName(show.getShowName())
									.availableSeats(show.getSeats().size()).movieLength(show.getMovieLength()).build();
							return showDto;
						}, Collectors.toList())
				));

        System.out.println("collect6 = " + collect6.get(true));

        ShowDto.nonNullTest("hari");
//        ShowDto.nonNullTest(null);


        //todo :DONE create timeout in lock obj - using localdattime seconds as timeout
        final SeatLock seatLock = SeatLock.builder()
                .lockedTime(LocalDateTime.now())
                .timeoutInSec(Long.valueOf(5))
                .build();
        seatLockDao.save(seatLock);
        System.out.println("seatLock = " + seatLock);
        final boolean check = seatLock.lockExpiredCheck();
        System.out.println("check = " + check);

        Thread.sleep(5000);//check after 5 sec
        System.out.println("lockExpiredCheck = " + seatLock.lockExpiredCheck());

        //todo : DONE create timeout in lock obj - using Localdattime and Duration as timeout
        final SeatLock seatLock1 = SeatLock.builder()
                .lockedTime(LocalDateTime.now())
                .timeOutDuration(Duration.ofSeconds(10))
                .build();
        seatLockDao.save(seatLock1);
        System.out.println("seatLock1 = " + seatLock1);
        System.out.println("usingDuration = " + seatLock1.lockExpiredUsingDuration());
        Thread.sleep(10000);
        System.out.println("usingDuration = " + seatLock1.lockExpiredUsingDuration());

        System.out.println("compareTo = " + Duration.ofSeconds(10).compareTo(Duration.ofSeconds(10)));//0
        System.out.println("compareTo = " + Duration.ofSeconds(10).compareTo(Duration.ofSeconds(100)));//-1
        System.out.println("compareTo = " + Duration.ofSeconds(100).compareTo(Duration.ofSeconds(10)));//1

        //todo :DONE read timeouts from config
        final Duration timeout = appConfig.getTimeout();
        System.out.println("timeout = " + timeout);
        System.out.println("appConfig = " + timeout.getSeconds());
        final SeatLock seatLock2 = SeatLock.builder()
                .lockedTime(LocalDateTime.now())
                .timeOutDuration(timeout).build();
        seatLockDao.save(seatLock2);
        System.out.println("seatLock2 = " + seatLock2);
        System.out.println("seatLock2.lockExpiredUsingDuration() = " + seatLock2.lockExpiredUsingDuration());
        Thread.sleep(5000);//test with 10 sec
        System.out.println("seatLock2.lockExpiredUsingDuration() = " + seatLock2.lockExpiredUsingDuration());

        //todo : DONE : create a seat and lock and test for rollback if any one fail
        final Seat seat = Seat.builder().seatType("platinum").build();
//        seatsService.saveSeatAndLock(show1.getId(), seat);

        //todo : NOT DONE : link new seat id to show, required bi directional mapping
//        show1.getSeats().add(seat);//error coz of uni direction not bidirectional
//        showDao.save(show1);

        System.err.println(show1.getId() +" "+seat.getId());
//        showDao.addNewShowSeat(show1.getId(), seat.getId());//not working

    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
class ShowDto {
    String movieName;
    Long movieLength;
    Integer availableSeats;

    public static void nonNullTest(@NonNull String string) {//null check of string, throw exception
        System.out.println("string = " + string);
    }
}


