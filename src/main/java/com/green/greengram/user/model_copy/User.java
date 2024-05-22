package com.green.greengram.user.model_copy;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class User {

     private long userId;
     private String uid;
     private String upw;
     private String nm;
     private String pic;
     private String createdAt;
     private String updatedAt;


}
