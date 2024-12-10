package com.java.ticketing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.java.ticketing.model.TicketPool;

@Service
public class TicketService {
    private final TicketPool pool;
    private final ThreadPoolTaskExecutor executor;

    @Autowired
    public TicketService(TicketPool pool) {
        this.pool = pool;
        this.executor = new ThreadPoolTaskExecutor();
        this.executor.setCorePoolSize(10);
        this.executor.initialize();
    }

    public void startVendorSimulation(int rate, String vendorName) {
        executor.execute(() -> {
            while (true) {
                pool.addTickets(rate, vendorName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    public void startCustomerSimulation(String customerName) {
        executor.execute(() -> {
            while (pool.removeTicket(customerName)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    public void stopSimulation() {
        executor.shutdown();
    }
}
