package com.energizor.restapi.board.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardCommentDTO {

    private int commentCode;
    private String commentContent;
    private LocalDateTime registerDate;
    private String deptName;
    private String teamName;
    private String userName;
    private String userRank;
    private int boardCode;
}
