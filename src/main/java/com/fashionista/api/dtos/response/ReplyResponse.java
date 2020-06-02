package com.fashionista.api.dtos.response;

import com.fashionista.api.entities.Reply;
import com.fashionista.api.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponse {
    private String id;
    private String reply;
    private OwnerResponse owner;
    private Date date;

    static ReplyResponse transformToDto(Reply reply) {
        User user = reply.getUser();
        OwnerResponse owner = new OwnerResponse(user.getId(), user.getFullName());

        return new ReplyResponse(
                reply.getId(),
                reply.getReply(),
                owner,
                reply.getUpdatedAt()
        );
    }
}
