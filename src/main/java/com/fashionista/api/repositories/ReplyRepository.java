package com.fashionista.api.repositories;

import com.fashionista.api.entities.Reply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends CrudRepository<Reply, String> {
}
