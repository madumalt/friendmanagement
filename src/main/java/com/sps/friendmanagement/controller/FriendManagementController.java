package com.sps.friendmanagement.controller;

import com.sps.friendmanagement.request.BlockSubscribeRequest;
import com.sps.friendmanagement.request.ConnectionRequest;
import com.sps.friendmanagement.request.ListFriendsRequest;
import com.sps.friendmanagement.request.NotifyRequest;
import com.sps.friendmanagement.response.FriendListResponse;
import com.sps.friendmanagement.response.AcknowledgeResponse;
import com.sps.friendmanagement.response.NotifyResponse;
import com.sps.friendmanagement.service.PersonConnectionService;
import com.sps.friendmanagement.service.PersonService;
import com.sps.friendmanagement.util.FriendManagementUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendManagementController {
    private final PersonService personService;
    private final PersonConnectionService personConnectionService;
    private final FriendManagementUtil friendManagementUtil;

    public FriendManagementController(PersonService personService, PersonConnectionService personConnectionService,
            FriendManagementUtil friendManagementUtil) {

        this.personService = personService;
        this.personConnectionService = personConnectionService;
        this.friendManagementUtil = friendManagementUtil;
    }

    @PostMapping("add")
    public ResponseEntity<AcknowledgeResponse> addConnection(@RequestBody ConnectionRequest connectionRequest) {

        personConnectionService.addFriends(friendManagementUtil.getPersonList(connectionRequest.getFriends()));
        return successAckResponse();
    }

    @PostMapping("list")
    public ResponseEntity<FriendListResponse> getFriendList(@RequestBody ListFriendsRequest listFriendsRequest) {

        List<String> friendList = personConnectionService.getFriendList(listFriendsRequest.getEmail());

        if (!CollectionUtils.isEmpty(friendList)) {
            return new ResponseEntity<>(FriendListResponse.builder()
                    .isSuccess(true)
                    .friends(friendList)
                    .count(friendList.size())
                    .build(),
                    HttpStatus.OK);
        }
        return emptyListResponse();
    }

    @PostMapping("common")
    public ResponseEntity<FriendListResponse> getCommonFriendList(
            @RequestBody ConnectionRequest commonConnectionsRequest) {

        List<String> commonFriends = personConnectionService.getCommonFriends(
                friendManagementUtil.getPersonList(commonConnectionsRequest.getFriends()));

        if (!CollectionUtils.isEmpty(commonFriends)) {
            return new ResponseEntity<>(FriendListResponse.builder()
                    .isSuccess(true)
                    .friends(commonFriends)
                    .count(commonFriends.size())
                    .build(),
                    HttpStatus.OK);
        }
        return emptyListResponse();
    }

    @PostMapping("subscribe")
    public ResponseEntity<AcknowledgeResponse> subscribe(@RequestBody BlockSubscribeRequest subscriptionRequest) {

        personConnectionService.subscribeToPersonConnection(subscriptionRequest.getRequestor(),
                subscriptionRequest.getTarget());
        return successAckResponse();
    }

    @PostMapping("block")
    public ResponseEntity<AcknowledgeResponse> block(@RequestBody BlockSubscribeRequest blockRequest) {

        personConnectionService.blockFriend(blockRequest.getRequestor(), blockRequest.getTarget());
        return successAckResponse();
    }

    @PostMapping("notify")
    public ResponseEntity<NotifyResponse> getShouldNotifiedEmails(@RequestBody NotifyRequest notifyRequest) {

        List<String> shouldNotifiedEmails = personConnectionService.getAllEmailsToBeNotified(
                notifyRequest.getSender(), notifyRequest.getText());

        return new ResponseEntity<>(NotifyResponse.builder()
                .isSuccess(true)
                .recipients(shouldNotifiedEmails).build(),
                HttpStatus.OK);
    }

    private ResponseEntity<AcknowledgeResponse> successAckResponse() {

        return new ResponseEntity<>(AcknowledgeResponse.builder()
                .isSuccess(true)
                .build(),
                HttpStatus.OK);
    }

    private ResponseEntity<FriendListResponse> emptyListResponse() {

        return new ResponseEntity<>(FriendListResponse.builder()
                .isSuccess(false)
                .friends(Collections.emptyList())
                .count(0)
                .build(),
                HttpStatus.OK);
    }
}
