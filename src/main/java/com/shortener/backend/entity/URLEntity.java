package com.shortener.backend.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author M.dehghan
 * @since 2020-09-02
 */
@Entity
@Table(name = "URL")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
public class URLEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "LONG_URL")
    private String longURL;

    @Column(name = "SHORT_URL")
    private String shortUrl;

    @Column(name = "LINK_COUNTER")
    private Integer counter;
}
