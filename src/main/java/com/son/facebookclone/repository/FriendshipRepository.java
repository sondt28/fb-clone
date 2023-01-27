package com.son.facebookclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.son.facebookclone.model.Friendship;
import com.son.facebookclone.model.FriendshipPK;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipPK>{

	
}
