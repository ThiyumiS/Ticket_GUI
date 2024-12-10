package com.java.ticketing.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class TicketPool {
    private final Queue<Integer> tickets = new LinkedList<>();
    private final int maxCapacity;
    private int ticketsSold = 0;

    public TicketPool(@Value("${ticketpool.maxCapacity}") int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTickets(int count, String vendorName) {
        while (tickets.size() + ticketsSold >= maxCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        for (int i = 0; i < count; i++) {
            tickets.add(ticketsSold + 1);
        }
        notifyAll();
    }

    public synchronized boolean removeTicket(String customerName) {
        if (tickets.isEmpty() && ticketsSold >= maxCapacity) {
            return false;
        }

        Integer ticket = tickets.poll();
        if (ticket != null) {
            ticketsSold++;
            notifyAll();
            return true;
        }
        return false;
    }
}

