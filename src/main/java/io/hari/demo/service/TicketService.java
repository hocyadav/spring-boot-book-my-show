package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;
import io.hari.demo.dao.SeatLockDao;
import io.hari.demo.entity.SeatLock;
import io.hari.demo.entity.Ticket;
import io.hari.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

    @Override
    public Ticket save(Ticket ticket) {
        final Ticket fetchedTicket = super.save(ticket);
        synchronized (this) {
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
        }
        return fetchedTicket;
    }
}
