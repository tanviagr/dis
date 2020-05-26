package sgsits.cse.dis.user.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sgsits.cse.dis.user.dto.MarkAsReadDto;
import sgsits.cse.dis.user.dto.SendNotificationRequestDto;
import sgsits.cse.dis.user.model.Notification;
import sgsits.cse.dis.user.service.NotificationService;

import java.util.List;

/**
 * The type User notification controller.
 */
@Api(value = "User Notification Controller")
@RestController
@Controller
@RequestMapping(path = "/userNotificationController")
public class UserNotificationController {


    /**
     * The Notification service.
     */
    private final NotificationService notificationService;


    @Autowired
    private SimpMessagingTemplate msgtemp;
    /**
     * Instantiates a new User notification controller.
     *
     * @param notificationService the notification service
     */
    @Autowired
    public UserNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Gets all notifications.
     *
     * @param username the username
     * @return the all notifications
     */
    @GetMapping(value = "/getAllNotification/{username}")
    public List<Notification> getAllNotifications(@PathVariable("username") String username) {
        return notificationService.getAllNotification(username);
    }

    @PostMapping(value = "/markAsRead")
    public void markAsRead(@RequestBody MarkAsReadDto markAsReadDto) {
        notificationService.markAsRead(markAsReadDto.getNotificationId(), markAsReadDto.getUsername());
    }

    /**
     * Send notification to all.
     *
     * @param notification the notification
     */
    @PostMapping(value = "/sendNotificationToAll")
    public void sendNotificationToAll(@RequestBody Notification notification) {
        notificationService.sendNotificationToAll(notification);
    }

    /**
     * Send notification to participants.
     *
     * @param notificationRequest the notification request
     */
    @PostMapping(value = "/sendNotificationToParticipants")
    public void sendNotificationToParticipants(@RequestBody SendNotificationRequestDto notificationRequest) {
        notificationService.sendNotificationToParticipants(notificationRequest.getNotification(),
                notificationRequest.getUsernameList());
    }

    /**
     * Send notification to all except.
     *
     * @param notificationRequest the notification request
     */
    @PostMapping(value = "/sendNotificationToAllExcept")
    public void sendNotificationToAllExcept(@RequestBody SendNotificationRequestDto notificationRequest) {
        notificationService.sendNotificationToAllExcept(notificationRequest.getNotification(),
                notificationRequest.getUsernameList());
    }

    @MessageMapping("/chat")
    public void register(@Header("simpSessionId") String sessionId) {
        msgtemp.convertAndSendToUser(sessionId, "/topic/messages", "SessionId: "+sessionId);
    }
}