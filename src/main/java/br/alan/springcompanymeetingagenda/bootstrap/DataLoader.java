package br.alan.springcompanymeetingagenda.bootstrap;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import br.alan.springcompanymeetingagenda.domain.Meeting;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.domain.ResourceType;
import br.alan.springcompanymeetingagenda.repositories.MeetingRepository;
import br.alan.springcompanymeetingagenda.repositories.ResourceRepository;
import br.alan.springcompanymeetingagenda.repositories.ResourceTypeRepository;
import lombok.RequiredArgsConstructor;

/**
 * DataLoader
 */
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    // == fields ==
    private final ResourceTypeRepository resourceTypeRepository;
    private final ResourceRepository resourceRepository;
    private final MeetingRepository meetingRepository;

    // == public methods ==
    @Override
    public void run(String... args) throws Exception {
        this.loadResourceTypes();
        this.loadResources();
        this.loadMeetings();
    }

    private void loadResourceTypes() {
        if (this.resourceTypeRepository.count() == 0) {
            this.resourceTypeRepository.save(ResourceType.builder().name("Projector").build());
            this.resourceTypeRepository.save(ResourceType.builder().name("Room").build());
        }
    }

    private void loadResources() {
        if (this.resourceRepository.count() == 0) {
            try {
                ResourceType room = this.resourceTypeRepository.findAllByName("Room").get(0);

                this.resourceRepository
                        .save(Resource.builder().name("Room 1").resourceType(room).build());
                this.resourceRepository
                        .save(Resource.builder().name("Room 2").resourceType(room).build());

                ResourceType projector =
                        this.resourceTypeRepository.findAllByName("Projector").get(0);
                this.resourceRepository.save(
                        Resource.builder().name("Projector 1").resourceType(projector).build());
                this.resourceRepository.save(
                        Resource.builder().name("Projector 2").resourceType(projector).build());
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }

    private void loadMeetings() {
        if (this.meetingRepository.count() == 0) {
            try {
                Resource room1 = this.resourceRepository.findAllByName("Room 1").get(0);
                Resource projector1 = this.resourceRepository.findAllByName("Projector 1").get(0);

                this.meetingRepository.save(Meeting.builder().name("Meeting 1")
                        .description("Meeting 1 Description")
                        .start(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 8, 0, 0)))
                        .end(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 9, 0, 0))).build());
                this.meetingRepository.save(Meeting.builder().name("Meeting 1")
                        .description("Meeting 1 Description")
                        .resources(new HashSet<>(Arrays.asList(room1, projector1)))
                        .start(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 14, 0, 0)))
                        .end(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 15, 0, 0))).build());
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }
}
