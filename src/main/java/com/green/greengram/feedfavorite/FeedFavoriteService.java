package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedFavoriteService {
    private final FeedFavoriteMapper mapper;

    public int favorite(FeedFavoriteToggleReq p) {
        int delRow = mapper.delFav(p);
        if (delRow == 0) {
            return mapper.insFav(p);
        }
        return 0;
    }

}
