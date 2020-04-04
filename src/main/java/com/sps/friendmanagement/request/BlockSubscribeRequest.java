package com.sps.friendmanagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockSubscribeRequest {
    private String requestor;
    private String target;
}
