package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedFavoriteServiceImpl implements FeedFavoriteService {
    private final FeedFavoriteMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    public int favorite(FeedFavoriteToggleReq p) {
        p.setUserId(authenticationFacade.getLoginUserId());
        int delRow = mapper.delFav(p);
        if (delRow == 0) {
            return mapper.insFav(p);
        }
        return 0;
    }

}
