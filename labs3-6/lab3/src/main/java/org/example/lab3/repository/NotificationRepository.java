package org.example.lab3.repository;

import org.example.lab3.model.entity.Notification;
import org.example.lab3.model.enums.NotificationChannel;
import org.example.lab3.model.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStatus(NotificationStatus status);
    List<Notification> findByChannel(NotificationChannel channel);
    List<Notification> findByRecipientId(Long recipientId);

    // Для самостоятельных заданий
    List<Notification> findByStatusAndChannel(NotificationStatus status, NotificationChannel channel);
    List<Notification> findByStatusOrderByCreatedAtAsc(NotificationStatus status);

    @Query("SELECT n FROM Notification n WHERE n.status = :status AND n.channel = :channel")
    List<Notification> findByStatusAndChannelCustom(@Param("status") NotificationStatus status,
                                                    @Param("channel") NotificationChannel channel);

    @Query(value = "SELECT * FROM notifications WHERE status = :status AND channel = :channel", nativeQuery = true)
    List<Notification> findNativeByStatusAndChannel(@Param("status") String status,
                                                    @Param("channel") String channel);

    @Query("SELECT n FROM Notification n WHERE n.recipient.id = :recipientId AND n.status = :status")
    List<Notification> findByRecipientIdAndStatus(@Param("recipientId") Long recipientId,
                                                  @Param("status") NotificationStatus status);
}