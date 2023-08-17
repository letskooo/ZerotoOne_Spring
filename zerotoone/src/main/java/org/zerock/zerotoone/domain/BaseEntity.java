package org.zerock.zerotoone.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public class BaseEntity {

    // 생성일 자동 추가. update 불가
    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    // 마지막 수정 날짜 자동 갱신.
    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;

}
