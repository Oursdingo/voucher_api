package com.octopus.voucher.repository;

import com.octopus.voucher.entity.Event;
import com.octopus.voucher.enumeration.ActionEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    void shouldGetAllEvents() {
        Event event1 = Event.builder()
                .actionEnum(ActionEnum.CONNEXION)
                .entite("USER")
                .date(LocalDateTime.of(2026, 1, 1, 9, 0))
                .adresse("127.0.0.1")
                .createdAt(LocalDateTime.now())
                .build();

        Event event2 = Event.builder()
                .actionEnum(ActionEnum.DECONNEXION)
                .entite("USER")
                .date(LocalDateTime.of(2026, 1, 1, 10, 0))
                .adresse("127.0.0.2")
                .createdAt(LocalDateTime.now())
                .build();

        eventRepository.saveAll(List.of(event1, event2));

        List<Event> events = eventRepository.findAll();

        //assert
        assertEquals(2, events.size());
        assertTrue(events.stream().anyMatch(e -> ActionEnum.CONNEXION == e.getActionEnum()));
    }

    @Test
    void shouldSaveEvent() {
        Event event = Event.builder()
                .actionEnum(ActionEnum.CONNEXION)
                .entite("ACCOUNT")
                .date(LocalDateTime.of(2026, 1, 2, 11, 0))
                .adresse("10.0.0.1")
                .build();

        Event saved = eventRepository.save(event);

        //assert
        assertNotNull(saved.getId());
        assertEquals(ActionEnum.CONNEXION, saved.getActionEnum());
    }

    @Test
    void shouldDeleteEvent() {
        Event event = Event.builder()
                .actionEnum(ActionEnum.DECONNEXION)
                .entite("VOUCHER")
                .date(LocalDateTime.of(2026, 1, 3, 12, 0))
                .adresse("10.0.0.2")
                .build();

        Event saved = eventRepository.save(event);
        eventRepository.delete(saved);

        //assert
        assertFalse(eventRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldUpdateEvent() {
        Event event = Event.builder()
                .actionEnum(ActionEnum.CONNEXION)
                .entite("AUDIT")
                .date(LocalDateTime.of(2026, 1, 4, 13, 0))
                .adresse("10.0.0.3")
                .build();

        Event saved = eventRepository.save(event);
        saved.setAdresse("10.0.0.4");

        Event updated = eventRepository.save(saved);

        //assert
        assertEquals("10.0.0.4", updated.getAdresse());
    }
}
