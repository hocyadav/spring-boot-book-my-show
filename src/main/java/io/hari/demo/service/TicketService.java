package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;
import io.hari.demo.dao.SeatLockDao;
import io.hari.demo.entity.SeatLock;
import io.hari.demo.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Service
public class TicketService extends BaseService<Ticket> {

    @Autowired
    SeatLockDao seatLockDao;

    public TicketService(BaseDao<Ticket> dao) {
        super(dao);
    }

    /**
     * Creating ticket
     * Approach :
     * 1. get ticket obj : contains show, seats id in string, userid
     * 2. get lock obj using show id & seat id
     * 3. check if status is locked then stop coz someone has booked this show's seat
     * 4. if status is available then change to locked and create ticket object
     * 5. do above in synchronized block
     * @param ticket
     * @return
     */
    @Override
    public Ticket save(Ticket ticket) {
        synchronized (this) {
        final Ticket fetchedTicket = super.save(ticket);
            final Long showId = ticket.getShow().getId();
            final String[] split = ticket.getSeatIds().split(",");
            Arrays.stream(split).forEach(System.err::println);

            Arrays.stream(split).forEach(s -> {
                final Long seatId = Long.valueOf(s);
                final SeatLock beforeLock = seatLockDao.findByShowIdAndSeatId(showId, seatId);
                System.err.println("beforeLock = " + beforeLock);

                beforeLock.setLockStatus("locked");
                final SeatLock afterLock = seatLockDao.save(beforeLock);
                System.err.println("afterLock = " + afterLock);
            });
        return fetchedTicket;
        }
    }

    @Cacheable("tickets")//2
    public Ticket fetchTicket(Long ticketId) {//testing caching
        final Ticket ticket = daoCall(ticketId);
        return ticket;
    }

    private Ticket daoCall(Long ticketId) {
        System.out.println("db call");//3 for testing
        final Optional<Ticket> ticketOptional = dao.findById(ticketId);
        return ticketOptional.get();
    }
}
