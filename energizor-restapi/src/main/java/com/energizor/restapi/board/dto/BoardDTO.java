package com.energizor.restapi.board.dto;

import jdk.jshell.Snippet;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {

    private int boardCode;
    private String title;
    private String content;
    private Long viewCount;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;
    private String userRank;
    private String userName;
    private String deptName;
    private String teamName;
    private Long userCode;
    private int boardTypeCode;

}
