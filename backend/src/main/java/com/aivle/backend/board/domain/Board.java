//package com.aivle.backend.board.domain;
//
//import com.aivle.backend.user.entity.User;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDate;
//
//@Entity
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Board {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long boardId; // 게시판 아이디
//
//    @Column(nullable = false)
//    private String title; // 제목
//
//    @Column(nullable = false)
//    private String content; // 내용
//
//    private int views; // 조회 수
//
//    @CreationTimestamp
//    private LocalDate createAt;
//
//    @UpdateTimestamp
//    private LocalDate updateAt;
//
//    // user join
//    @ManyToOne
//    @JoinColumn(name = "id", nullable = false)
//    private User user;
//<<<<<<< Updated upstream
//=======
//
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Reply> replies = new ArrayList<>();
//
//    // 좋아요/싫어요 카운트
//    @Builder.Default
//    @Column(nullable = false)
//    private int likes = 0;
//
//    @Builder.Default
//    @Column(nullable = false)
//    private int dislikes = 0;
//
//    // BoardLike 연관관계
//    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @JsonIgnore
//    @Builder.Default
//    private List<BoardLike> likesList = new ArrayList<>();
//>>>>>>> Stashed changes
//}

//package com.aivle.backend.board.domain;
//
//import com.aivle.backend.user.entity.User;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Board {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long boardId; // 게시판 아이디
//
//    @Column(nullable = false)
//    private String title; // 제목
//
//    @Column(nullable = false)
//    private String content; // 내용
//
//    private int views; // 조회 수
//
//    @CreationTimestamp
//    private LocalDate createAt;
//
//    @UpdateTimestamp
//    private LocalDate updatedAt;
//
//    // user join
//    @ManyToOne
//    @JoinColumn(name = "id", nullable = false)
//    private User user;
//
//<<<<<<< HEAD
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Builder.Default
//    private List<Reply> replies = new ArrayList<>();
//=======
//    // ✅ 댓글 리스트 (순환 참조 방지)
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Reply> replies = new ArrayList<>();
//
//    // ✅ 좋아요/싫어요 카운트
//    @Builder.Default
//    @Column(nullable = false)
//    private int likes = 0;
//
//    @Builder.Default
//    @Column(nullable = false)
//    private int dislikes = 0;
//
//    // ✅ 좋아요 리스트 (BoardLike 연관관계)
//    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @JsonIgnore
//    @Builder.Default
//    private List<BoardLike> likesList = new ArrayList<>();
//>>>>>>> fix/user-update1-frontend
//}

package com.aivle.backend.board.domain;

import com.aivle.backend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId; // 게시판 아이디

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String content; // 내용

    private int views; // 조회 수

    @CreationTimestamp
    private LocalDate createAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    // user join
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    // ✅ 댓글 리스트 (순환 참조 방지)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Reply> replies = new ArrayList<>();

    // ✅ 좋아요/싫어요 카운트
    @Builder.Default
    @Column(nullable = false)
    private int likes = 0;

    @Builder.Default
    @Column(nullable = false)
    private int dislikes = 0;

    // ✅ 좋아요 리스트 (BoardLike 연관관계)
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<BoardLike> likesList = new ArrayList<>();
}
