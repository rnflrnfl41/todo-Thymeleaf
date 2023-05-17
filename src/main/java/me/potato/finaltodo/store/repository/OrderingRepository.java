package me.potato.finaltodo.store.repository;

import me.potato.finaltodo.store.entity.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderingRepository extends JpaRepository<Ordering, Long> {
    Optional<Ordering> findByUserNo(Long userNo);

    void deleteByUserNo(Long userNo);
}


/*
Hibernate: create table ordering (id bigint not null auto_increment, todos json, user_no bigint, primary key (id)) engine=InnoDB
Hibernate: create table todo (id bigint not null auto_increment, completed_time varchar(255), created_time varchar(255), item varchar(255), status varchar(255), user_no bigint, primary key (id)) engine=InnoDB
Hibernate: alter table ordering drop index UK_942yqh0dkpkscjvletxyxo19a
Hibernate: alter table ordering add constraint UK_942yqh0dkpkscjvletxyxo19a unique (user_no)
*/

/*
Hibernate: insert into todo (completed_time, created_time, item, status, user_no) values (?, ?, ?, ?, ?)
Hibernate: select o1_0.id,o1_0.todos,o1_0.user_no from ordering o1_0 where o1_0.user_no=?
Optional.empty
Hibernate: insert into ordering (todos, user_no) values (?, ?)

Hibernate: insert into todo (completed_time, created_time, item, status, user_no) values (?, ?, ?, ?, ?)
Hibernate: select o1_0.id,o1_0.todos,o1_0.user_no from ordering o1_0 where o1_0.user_no=?
Optional[Ordering(id=1, userNo=1, todos=[Todo(id=1, item=req, status=registered, userNo=1, createdTime=2023-05-17 14:57, completedTime=still progressing..)])]
Hibernate: select o1_0.id,o1_0.todos,o1_0.user_no from ordering o1_0 where o1_0.id=?
Hibernate: update ordering set todos=?, user_no=? where id=?
*/