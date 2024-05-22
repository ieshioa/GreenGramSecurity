package com.green.greengram.common;

public class GlobalConst {
    public static final int FEED_PAGING_SIZE = 20;
    public static final int COMMENT_SIZE_FER_FEED = 4;

}

/*
프론트 -> 백  데이터를 전달하는 방법 (크게 두가지)
1. 쿼리스트링(파라미터)
      url 즉 주소값에 데이터를 담아서 보냄
      처리속도가 빠름
      데이터가 노출됨
2. 바디
       JSON or File
3. url
        PathVariable - 거의 pk값만
        쿼리스트링


- 데이터를 노출시키면 안되거나 데이터 양이 많을때는
    절대 파라미터로 처리하면 안됨
- url은 담을 수 있는 데이터에 한계가 있다. 바디는 없다.


어노테이션 세팅
1. 쿼리스트링으로 받고싶을 때
    - @RequestParam
    - @ModelAttribute
2. JSON으로 받고싶을 때
    - @RequestBody
3. File로 받고싶을 때
    - @RequestPart
        Multipart를 썼다면 json을 받을 떄도 RequestPart를 써야함
4. url
    -@PathVariable
 */
