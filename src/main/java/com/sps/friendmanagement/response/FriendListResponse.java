package com.sps.friendmanagement.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendListResponse {
    private boolean isSuccess;
    private List<String> friends;
    private Integer count;
}
